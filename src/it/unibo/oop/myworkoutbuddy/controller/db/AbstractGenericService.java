package it.unibo.oop.myworkoutbuddy.controller.db;

import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import it.unibo.oop.myworkoutbuddy.controller.GenericService;
import it.unibo.oop.myworkoutbuddy.controller.db.util.MongoDBUtils;

public abstract class AbstractGenericService<T> implements GenericService<T> {

    protected abstract MongoCollection<Document> getMongoCollection();

    private final Class<T> clazz;

    protected AbstractGenericService(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean create(Map<String, Object> fields) {
        try {
            getMongoCollection()
                    .insertOne(new Document(fields));
        } catch (final MongoException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<T> getAll() {
        return MongoDBUtils.getAllDocuments(getMongoCollection(), clazz);
    }

    @Override
    public List<T> getByParams(Map<String, Object> params) {
        return MongoDBUtils.getDocumentsByParams(getMongoCollection(), params, clazz);
    }

    @Override
    public long deleteByParams(Map<String, Object> params) {
        return getMongoCollection()
                .deleteMany(Filters.and(MongoDBUtils.toBsonFilters(params)))
                .getDeletedCount();
    }

}
