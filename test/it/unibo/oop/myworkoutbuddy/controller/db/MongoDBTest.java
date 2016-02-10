package it.unibo.oop.myworkoutbuddy.controller.db;

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

    }

    @Test
    public void testGetAll() throws Exception {

    }

}
