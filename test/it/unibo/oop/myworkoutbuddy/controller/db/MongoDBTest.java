package it.unibo.oop.myworkoutbuddy.controller.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.Block;
import com.mongodb.client.model.Filters;

public class MongoDBTest {

    private class Person {

        private ObjectId id;

        private final String firstName;
        private final String lastName;
        private final int age;

        private final boolean married;

        public Person(String firstName, String lastName, int age, boolean married) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.married = married;
        }

        public ObjectId getId() {
            return id;
        }

        public void setId(ObjectId id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Person [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", married=" + married
                    + "]";
        }

    }

    private class PersonBuilder {

        final Map<String, Optional<?>> map;

        public PersonBuilder() {
            map = new HashMap<>();
            map.put("firstName", Optional.empty());
            map.put("lastName", Optional.empty());
            map.put("age", Optional.empty());
            map.put("married", Optional.empty());
        }

        public PersonBuilder set(String key, Object value) {
            if (map.get(key) == null) {
                throw new IllegalArgumentException();
            }
            map.merge(key, Optional.of(value), (o, n) -> n);

            return this;
        }

        public Map<String, Object> toMap() {
            checkFields();
            return map.entrySet().stream()
                    .collect(Collectors.toMap(
                            Entry::getKey,
                            e -> e.getValue().get()));
        }

        public Person build() {
            checkFields();
            return new Person(
                    (String) map.get("firstName").get(),
                    (String) map.get("lastName").get(),
                    (int) map.get("age").get(),
                    (boolean) map.get("married").get());
        }

        private void checkFields() {
            if (map.values().stream().anyMatch(o -> !o.isPresent())) {
                throw new IllegalArgumentException();
            }
        }

    }

    private class PersonService extends AbstractGenericService<Person> {

        @Override
        public List<Person> getAll() {
            final List<Person> people = new ArrayList<>();

            MongoDriver.getMongoClient()
                    .getDatabase("myworkoutbuddy")
                    .getCollection(getCollectionName())
                    .find()
                    .forEach(toList(people));

            return people;
        }

        @Override
        public Optional<Person> getById(ObjectId id) {
            final List<Person> person = new ArrayList<>();
            MongoDriver.getMongoClient()
                    .getDatabase("myworkoutbuddy")
                    .getCollection(getCollectionName())
                    .find(Filters.eq("_id", id))
                    .forEach(toList(person));
            return person.isEmpty() ? Optional.empty() : Optional.of(person.get(0));
        }

        @Override
        public List<Person> getByParams(Map<String, Object> params) {
            final List<Person> people = new ArrayList<>();

            MongoDriver.getMongoClient()
                    .getDatabase("myworkoutbuddy")
                    .getCollection(getCollectionName())
                    .find(Filters.and(toBsonFilters(params)))
                    .forEach(toList(people));
            return people;
        }

        @Override
        protected String getCollectionName() {
            return "persons";
        }

        private Block<? super Document> toList(List<Person> list) {
            return new Block<Document>() {
                public void apply(Document document) {
                    final PersonBuilder builder = new PersonBuilder();
                    document.forEach((k, v) -> {
                        try {
                            builder.set(k, v);
                        } catch (final IllegalArgumentException e) {
                        }
                    });
                    final Person person = builder.build();
                    person.setId(document.getObjectId("_id"));
                    list.add(person);
                }
            };
        }

        private Iterable<Bson> toBsonFilters(Map<String, Object> params) {
            List<Bson> filters = new ArrayList<>();

            params.forEach((k, v) -> filters.add(Filters.eq(k, v)));

            return filters;
        }

    }

    private final PersonService service = new PersonService();

    @Test
    public void testCreate() {

        final PersonBuilder me = new PersonBuilder()
                .set("age", 20)
                .set("firstName", "Mattia")
                .set("lastName", "Vandi")
                .set("married", false);

        service.create(me.toMap());
    }

    @Test
    public void testGetAll() {
        System.out.println("getAll");
        System.out.println(service.getAll());
    }

    @Test
    public void testGetById() {
        System.out.println("getById");
        System.out.println(service.getById(service.getAll().get(0).getId()));
    }

    @Test
    public void testGetByParams() {
        final Map<String, Object> params = new HashMap<>();
        params.put("age", 20);
        System.out.println("getByParams");
        System.out.println(service.getByParams(params));
    }

}
