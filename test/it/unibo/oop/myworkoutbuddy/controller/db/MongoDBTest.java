package it.unibo.oop.myworkoutbuddy.controller.db;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.oop.myworkoutbuddy.util.Builder;

public class MongoDBTest {

    private class PersonService extends AbstractGenericService<Person> {

        public PersonService(final Class<Person> clazz) {
            super(clazz);
        }

        @Override
        protected String getCollectionName() {
            return "people";
        }

    }

    private final PersonService service = new PersonService(Person.class);

    @Before
    public void testCreate() {
        service.create(new Builder<>(Person.class)
                .set("firstName", "Mattia")
                .set("lastName", "Vandi")
                .set("age", 20)
                .set("married", false)
                .toMap());
        service.create(new Builder<>(Person.class)
                .set("firstName", "Lorenzo")
                .set("lastName", "Pacini")
                .set("age", 20)
                .set("married", false)
                .toMap());
        service.create(new Builder<>(Person.class)
                .set("firstName", "Nicola")
                .set("lastName", "Piscaglia")
                .set("age", 20)
                .set("married", false)
                .toMap());

        System.out.println("Inserted sample data.");
    }

    @After
    public void testDelete() {
        System.out.println("Deleted " + service.deleteAll() + " document(s).");
    }

    @Test
    public void testGetByParams() throws Exception {
        final Map<String, Object> params = new HashMap<>();
        params.put("lastName", "ia");
        final List<Person> people = service.getByParams(params);
        System.out.println(people);
        assertTrue(people.containsAll(Arrays.asList(
                new Person("Nicola", "Piscaglia", false, 20))));
    }

    @Test
    public void testGetAll() throws Exception {
        final List<Person> people = service.getAll();
        System.out.println(people);
        assertTrue(people.containsAll(Arrays.asList(
                new Person("Mattia", "Vandi", false, 20),
                new Person("Nicola", "Piscaglia", false, 20),
                new Person("Lorenzo", "Pacini", false, 20))));
    }

}
