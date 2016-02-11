package it.unibo.oop.myworkoutbuddy.controller.db;

import static it.unibo.oop.myworkoutbuddy.controller.db.util.CRUDOperations.createNewDocument;
import static it.unibo.oop.myworkoutbuddy.controller.db.util.CRUDOperations.createNewDocuments;
import static it.unibo.oop.myworkoutbuddy.controller.db.util.CRUDOperations.deleteDocumentsByParams;
import static it.unibo.oop.myworkoutbuddy.controller.db.util.CRUDOperations.getDocumentsByParams;
import static it.unibo.oop.myworkoutbuddy.controller.db.util.CRUDOperations.updateDocumentsByParams;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import it.unibo.oop.myworkoutbuddy.controller.Service;
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
    public MongoService(final String collectionName) {
        collection = MongoDriver.getCollection(collectionName);
    }

    @Override
    public boolean create(final Map<String, Object> fields) {
        Preconditions.checkArgument(!fields.values().contains(null));
        return createNewDocument(collection, fields);
    }

    @Override
    public boolean create(final Collection<? extends Map<String, Object>> elements) {
        Preconditions.checkArgument(!elements.contains(null));
        return createNewDocuments(collection, elements);
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return getByParams(new HashMap<>());
    }

    @Override
    public List<Map<String, Object>> getByParams(final Map<String, Object> params) {
        return getDocumentsByParams(collection, params);
    }

    @Override
    public long updateByParams(final Map<String, Object> queryParams, final Map<String, Object> updateParams) {
        return updateDocumentsByParams(collection, queryParams, updateParams);
    }

    @Override
    public long deleteAll() {
        return deleteByParams(new HashMap<>());
    }

    @Override
    public long deleteByParams(final Map<String, Object> params) {
        return deleteDocumentsByParams(collection, params);
    }

}
