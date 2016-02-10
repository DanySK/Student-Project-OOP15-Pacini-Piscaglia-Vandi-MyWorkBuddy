package it.unibo.oop.myworkoutbuddy.controller.db.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;

import it.unibo.oop.myworkoutbuddy.util.Builder;

/**
 * Utility class for CRUD operations.
 */
public final class CRUDOperations {

    /**
     * @param collection
     *            The collection to use.
     * @param params
     *            The query filters
     * @param clazz
     *            The class of the object to retrieve.
     * @return A list of elements which satisfy the given filters.
     * @param <T>
     *            the type of the element to retrieve.
     */
    public static List<Map<String, Object>> getDocumentsByParams(
            final MongoCollection<Document> collection,
            final Map<String, Object> params) {
        final List<Map<String, Object>> l = new ArrayList<>();
        collection
                .find(toBson(params, true));
        return l;
    }

    /**
     * Deletes all the documents that satisfy the specified parameters.
     * 
     * @param collection
     *            The collection to use.
     * @param params
     *            The delete filters
     * @return The number of deleted elements.
     */
    public static long deleteDocumentsByParams(
            final MongoCollection<Document> collection,
            final Map<String, Object> params) {
        return collection
                .deleteMany(toBson(params, false))
                .getDeletedCount();
    }

    /**
     * Converts a {@link Map} to a {@link Bson} object.
     * 
     * @param params
     *            The map to convert to a BSON query
     * @param stringToRegex
     *            if {@code true} each string will be compiled as a {@link Pattern}.
     * @return The BSON query to perform.
     */
    private static Bson toBson(final Map<String, Object> params, final boolean stringToRegex) {
        return new BasicDBObject(params.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, e -> {
                    final Object v = e.getValue();
                    return (stringToRegex && v instanceof String)
                            ? Pattern.compile(String.valueOf(v))
                            : v;
                })));
    }

    private static <T> Block<? super Document> addToList(final List<T> l, final Class<? extends T> clazz) {
        return new Block<Document>() {
            @Override
            public void apply(final Document document) {
                final Builder<T> builder = new Builder<>(clazz);
                document.forEach((f, v) -> {
                    if (!f.contains("_id")) {
                        builder.set(f, v);
                    }
                });
                l.add(builder.build());
            }
        };
    }

    private CRUDOperations() {
        throw new IllegalAccessError("No instances for " + getClass().getName());
    }

}
