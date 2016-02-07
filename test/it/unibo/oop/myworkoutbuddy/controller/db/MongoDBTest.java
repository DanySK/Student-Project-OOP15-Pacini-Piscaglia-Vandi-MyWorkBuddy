package it.unibo.oop.myworkoutbuddy.controller.db;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoCollection;

import it.unibo.oop.myworkoutbuddy.util.ReflectionUtils;

public class MongoDBTest {

    private class PersonService extends AbstractGenericService<Person> {

        public PersonService(Class<Person> clazz) {
            super(clazz);
        }

        @Override
        protected MongoCollection<Document> getMongoCollection() {
            return MongoDriver.getMongoClient()
                    .getDatabase("myworkoutbuddy")
                    .getCollection("testing");
        }

    }

    private final PersonService service = new PersonService(Person.class);

    @Test
    public void testCreate() {
        System.out.println(ReflectionUtils.getConstructorShape(Person.class));
        // Test creatiion of new documents here...
    }

    @Test
    public void testGetByParams() throws Exception {
        final Map<String, Object> params = new HashMap<>();
        params.put("lastName", "Vandi");
        System.out.println(service.getByParams(params));
    }

    @Test
    public void testDeleteByParams() throws Exception {
        final Map<String, Object> params = new HashMap<>();
        // System.out.println(service.deleteByParams(params));
    }

}
