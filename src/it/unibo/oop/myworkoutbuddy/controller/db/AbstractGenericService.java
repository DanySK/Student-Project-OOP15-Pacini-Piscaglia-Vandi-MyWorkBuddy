package it.unibo.oop.myworkoutbuddy.controller.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;

import it.unibo.oop.myworkoutbuddy.controller.GenericService;
import it.unibo.oop.myworkoutbuddy.controller.db.util.CRUDOperations;
import it.unibo.oop.myworkoutbuddy.util.Preconditions;

/**
 * @param <T>
 */
public abstract class AbstractGenericService<T> implements GenericService<T> {

    private final Class<? extends T> clazz;

    /**
     * Creates a new instance of an abstract service.
     * 
     * @param clazz
     *            The class to retrieve.
     */
    protected AbstractGenericService(final Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean create(final Map<String, Object> fields) {
        Preconditions.checkArgument(!fields.values().contains(null));
        try {
            getCollection()
                    .insertOne(new Document(fields));
        } catch (final MongoException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<T> getAll() {
        return getByParams(new HashMap<>());
    }

    @Override
    public List<T> getByParams(final Map<String, Object> params) {
        return CRUDOperations.getDocumentsByParams(
                getCollection(),
                params,
                clazz);
    }

    @Override
    public long deleteByParams(final Map<String, Object> params) {
        return getCollection()
                .deleteMany(CRUDOperations.toBson(params, false))
                .getDeletedCount();
    }

    @Override
    public long deleteAll() {
        return deleteByParams(new HashMap<>());
    }

    /**
     * @return The name of the collection to use.
     */
    protected abstract String getCollectionName();

    private MongoCollection<Document> getCollection() {
        return MongoDriver.getCollection(getCollectionName());
    }

}
