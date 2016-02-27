package it.unibo.oop.myworkoutbuddy.controller.db.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.common.base.Preconditions;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;

/**
 * Utility class for CRUD operations.
 */
public final class CRUDOperations {

    /**
     * Creates a new document to insert in the database.
     * 
     * @param collection
     *            the collection to use
     * @param fields
     *            the fields of the element to insert
     * @return True if the element is successfully created, false if some errors occurs.
     */
    public static boolean createNewDocument(
            final MongoCollection<Document> collection,
            final Map<String, Object> fields) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(fields);
        Preconditions.checkArgument(!fields.values().contains(null));
        try {
            collection.insertOne(new Document(fields));
            return true;
        } catch (final MongoException e) {
            // Insert fail.
            return false;
        }
    }

    /**
     * Creates as many new documents as the elements and inserts them in the database.
     * 
     * @param collection
     *            the collection to use
     * @param elements
     *            the elements to insert
     * @return true if the elements is successfully created, false if some errors occurs
     */
    public static long createNewDocuments(
            final MongoCollection<Document> collection,
            final Collection<? extends Map<String, Object>> elements) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(elements);
        Preconditions.checkArgument(!elements.contains(null));
        try {
            collection.insertMany(elements.stream()
                    .map(Document::new)
                    .collect(Collectors.toList()));
            return elements.size();
        } catch (final MongoException e) {
            // Insert fail.
            return -1;
        }
    }

    /**
     * @param collection
     *            the collection to use
     * @param queryParams
     *            the query filters
     * @return a list of elements which satisfy the given filters
     */
    public static Optional<Map<String, Object>> getOneDocumentByParams(
            final MongoCollection<Document> collection,
            final Map<? extends String, ?> queryParams) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(queryParams);
        return Optional.ofNullable(collection
                .find(toBson(queryParams, false))
                .first());
    }

    /**
     * @param collection
     *            the collection to use
     * @param queryParams
     *            the query filters
     * @return a list of elements which satisfy the given filters
     */
    public static List<Map<String, Object>> getDocumentsByParams(
            final MongoCollection<Document> collection,
            final Map<? extends String, ?> queryParams) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(queryParams);
        final List<Map<String, Object>> l = new ArrayList<>();
        collection
                .find(toBson(queryParams, true))
                .forEach(addToList(l));
        l.removeIf(m -> m.equals(null));
        return l;
    }

    /**
     * @param collection
     *            the collection to use
     * @param queryParams
     *            the query filters
     * @param updateParams
     *            the fields to update with the new value.
     * @return the number of modified documents
     */
    public static long updateDocumentsByParams(
            final MongoCollection<Document> collection,
            final Map<? extends String, ?> queryParams,
            final Map<String, Object> updateParams) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(queryParams);
        Objects.requireNonNull(updateParams);
        return collection
                .updateMany(
                        toBson(queryParams, false),
                        new Document("$set", new Document(updateParams)))
                .getModifiedCount();
    }

    /**
     * Deletes all the documents that satisfy the specified parameters.
     * 
     * @param collection
     *            the collection to use
     * @param deleteParams
     *            the delete filter
     * @return the number of deleted documents
     */
    public static long deleteDocumentsByParams(
            final MongoCollection<Document> collection,
            final Map<? extends String, ?> deleteParams) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(deleteParams);
        return collection
                .deleteMany(toBson(deleteParams, false))
                .getDeletedCount();
    }

    /**
     * Converts a {@link Map} to a {@link Bson} object.
     * 
     * @param params
     *            the map to convert to a BSON query
     * @param stringToRegex
     *            if {@code true} each string will be compiled as a {@link Pattern}
     * @return fhe BSON query to perform
     */
    private static Bson toBson(final Map<? extends String, ?> params, final boolean stringToRegex) {
        return new BasicDBObject(params.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, e -> {
                    final Object v = e.getValue();
                    return (stringToRegex && v instanceof String)
                            ? Pattern.compile(String.valueOf(v))
                            : v;
                })));
    }

    private static <T> Block<? super Document> addToList(final List<Map<String, Object>> l) {
        return new Block<Document>() {
            @Override
            public void apply(final Document document) {
                document.remove("_id"); // We don't want the MongoDB ObjectId field as a JSON document field.
                l.add(document);
            }
        };
    }

    private CRUDOperations() {
        throw new IllegalAccessError("No instances for " + getClass().getName());
    }

}
