package it.unibo.oop.myworkoutbuddy.controller.db.mongodb;

import static it.unibo.oop.myworkoutbuddy.controller.db.mongodb.util.MongoCRUDOperations.createNewDocument;
import static it.unibo.oop.myworkoutbuddy.controller.db.mongodb.util.MongoCRUDOperations.createNewDocuments;
import static it.unibo.oop.myworkoutbuddy.controller.db.mongodb.util.MongoCRUDOperations.deleteDocumentsByParams;
import static it.unibo.oop.myworkoutbuddy.controller.db.mongodb.util.MongoCRUDOperations.getDocumentsByParams;
import static it.unibo.oop.myworkoutbuddy.controller.db.mongodb.util.MongoCRUDOperations.getOneDocumentByParams;
import static it.unibo.oop.myworkoutbuddy.controller.db.mongodb.util.MongoCRUDOperations.updateDocumentsByParams;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.Document;

import com.google.common.base.Preconditions;
import com.mongodb.client.MongoCollection;

import it.unibo.oop.myworkoutbuddy.controller.db.AbstractDBService;

/**
 * A generic service to make Create Read Update Delete operations on MongoDB.
 */
public class MongoService extends AbstractDBService {

    private final MongoCollection<Document> collection;

    /**
     * Creates a new instance of a service.
     * 
     * @param collectionName
     *            the name of the collection to use
     */
    public MongoService(final String collectionName) {
        super(collectionName);
        collection = MongoDriver.getCollection(getTableName());
    }

    @Override
    public boolean create(final Map<String, Object> fields) {
        Preconditions.checkArgument(!fields.values().contains(null));
        return createNewDocument(collection, fields);
    }

    @Override
    public long create(final Collection<? extends Map<String, Object>> elements) {
        Preconditions.checkArgument(!elements.contains(null));
        return createNewDocuments(collection, elements);
    }

    @Override
    public Optional<Map<String, Object>> getOneByParams(final Map<String, Object> queryParams) {
        return getOneDocumentByParams(collection, queryParams);
    }

    @Override
    public List<Map<String, Object>> getAll() {
        return getByParams(new HashMap<>());
    }

    @Override
    public List<Map<String, Object>> getByParams(final Map<String, Object> queryParams) {
        return getDocumentsByParams(collection, queryParams);
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
