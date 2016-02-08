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

import it.unibo.oop.myworkoutbuddy.controller.Builder;

/**
 * Utility class for CRUD operations.
 */
public final class CRUDOperations {

    /**
     * @param collection
     *            The collection to use for get the data.
     * @param params
     *            The filters to apply
     * @param clazz
     *            The class of the object to retrieve.
     * @return A list of elements which satisfy the given filters.
     * @param <T>
     *            the type of the element to retrieve.
     */
    public static <T> List<T> getDocumentsByParams(
            final MongoCollection<Document> collection,
            final Map<String, Object> params,
            final Class<? extends T> clazz) {
        final List<T> list = new ArrayList<>();
        collection
                .find(toBson(params, true))
                .forEach(addToList(list, clazz));
        return list;
    }

    /**
     * Converts a {@link java.util.Map} to a {@link org.bson.conversions.Bson} object.
     * This operation trasforms each string in a {@link java.util.regex.Pattern}.
     * 
     * @param params
     *            The map to convert to a BSON query
     * @param stringToRegex
     *            Whether a string should be compiled as a regular expression
     * @return The BSON query to perform.
     */
    public static Bson toBson(final Map<String, Object> params, final boolean stringToRegex) {
        return new BasicDBObject(params.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, e -> {
                    final Object v = e.getValue();
                    return (stringToRegex && v instanceof String)
                            ? Pattern.compile(v.toString())
                            : v;
                })));
    }

    private static <T> Block<? super Document> addToList(final List<T> list, final Class<? extends T> clazz) {
        return new Block<Document>() {
            public void apply(final Document document) {
                final Builder<T> builder = new Builder<>(clazz);
                document.forEach((f, v) -> {
                    if (!f.contains("_id")) {
                        builder.set(f, v);
                    }
                });
                try {
                    list.add(builder.build());
                } catch (final Exception e) {
                    throw new UnsupportedOperationException();
                }
            }
        };
    }

    private CRUDOperations() {
        throw new IllegalAccessError();
    }

}
