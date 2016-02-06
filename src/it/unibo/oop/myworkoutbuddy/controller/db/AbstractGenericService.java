package it.unibo.oop.myworkoutbuddy.controller.db;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoException;

import it.unibo.oop.myworkoutbuddy.controller.GenericService;

public abstract class AbstractGenericService<T> implements GenericService<T> {

    @Override
    public boolean create(Map<String, Object> fields) {
        Document document = null;

        for (final Entry<String, Object> e : fields.entrySet()) {
            final String key = e.getKey();
            final Object value = (e.getValue() instanceof Collection)
                    ? ((Collection<?>) e.getValue()).toArray()
                    : e.getValue();
            if (document == null) {
                document = new Document(key, value);
            } else {
                document.append(key, value);
            }
        }

        try {
            MongoDriver.getMongoClient()
                    .getDatabase("myworkoutbuddy")
                    .getCollection(getCollectionName()).insertOne(document);
        } catch (final MongoException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteById(ObjectId id) {
        // TODO Auto-generated method stub
        return false;
    }

    protected abstract String getCollectionName();

}
