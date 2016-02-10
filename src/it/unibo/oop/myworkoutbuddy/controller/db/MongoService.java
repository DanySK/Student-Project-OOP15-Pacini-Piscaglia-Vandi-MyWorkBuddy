package it.unibo.oop.myworkoutbuddy.controller.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import it.unibo.oop.myworkoutbuddy.controller.Service;
import it.unibo.oop.myworkoutbuddy.controller.db.util.CRUDOperations;
import it.unibo.oop.myworkoutbuddy.util.Preconditions;

/**
 * A generic service to make Create Read Update Delete operations on MongoDB.
 */
public class MongoService implements Service {

    private final MongoCollection<Document> collection;

    /**
     * Creates a new instance of an abstract service.
     * 
     * @param collectionName
     *            the name of the collection to use
     */
    protected MongoService(final String collectionName) {
        collection = MongoDriver.getCollection(collectionName);
    }

    @Override
    public boolean create(final Map<String, Object> fields) {
        return CRUDOperations.createNewDocument(collection, fields);
    }

    @Override
    public boolean create(final List<Map<String, Object>> fields) {
        Preconditions.checkArgument(!fields.contains(null));
        return CRUDOperations.createNewDocuments(collection, fields);
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return getByParams(new HashMap<>());
    }

    @Override
    public List<Map<String, Object>> getByParams(final Map<String, Object> params) {
        return CRUDOperations.getDocumentsByParams(collection, params);
    }

    @Override
    public long updateByParams(final Map<String, Object> queryParams, final Map<String, Object> updateParams) {
        return CRUDOperations.updateDocumentsByParams(collection, queryParams, updateParams);
    }

    @Override
    public long deleteAll() {
        return deleteByParams(new HashMap<>());
    }

    @Override
    public long deleteByParams(final Map<String, Object> params) {
        return CRUDOperations.deleteDocumentsByParams(collection, params);
    }

}
