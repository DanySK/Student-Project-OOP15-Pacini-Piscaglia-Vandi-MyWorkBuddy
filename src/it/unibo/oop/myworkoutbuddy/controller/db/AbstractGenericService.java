package it.unibo.oop.myworkoutbuddy.controller.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;

import it.unibo.oop.myworkoutbuddy.controller.GenericService;
import it.unibo.oop.myworkoutbuddy.controller.db.util.MongoDBUtils;

public abstract class AbstractGenericService<T> implements GenericService<T> {

    private final Class<? extends T> clazz;

    protected AbstractGenericService(final Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean create(Map<String, Object> fields) {
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
    public List<T> getByParams(Map<String, Object> params) {
        return MongoDBUtils.getDocumentsByParams(
                getCollection(),
                params,
                clazz);
    }

    @Override
    public long deleteByParams(Map<String, Object> params) {
        return getCollection()
                .deleteMany(Objects.requireNonNull(params).size() > 0
                        ? MongoDBUtils.toBson(params)
                        : new Document())
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
