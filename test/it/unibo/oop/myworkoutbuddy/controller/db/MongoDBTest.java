package it.unibo.oop.myworkoutbuddy.controller.db;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.oop.myworkoutbuddy.controller.Service;
import it.unibo.oop.myworkoutbuddy.util.json.JSONObject;

public class MongoDBTest {

    private final Service service = new MongoService("people");

    @Before
    public void testCreate() {
        final List<Map<String, Object>> toInsert = Arrays.asList(
                new JSONObject(new Person.PersonBuilder()
                        .firstName("Mattia")
                        .lastName("Vandi")
                        .age(20)
                        .married(false)
                        .build()),
                new JSONObject(new Person.PersonBuilder()
                        .firstName("Nicola")
                        .lastName("Piscaglia")
                        .age(21)
                        .married(false)
                        .build()),
                new JSONObject(new Person.PersonBuilder()
                        .firstName("Lorenzo")
                        .lastName("Pacini")
                        .age(20)
                        .married(false)
                        .build()));

        toInsert.forEach(System.out::println);

        service.create(toInsert);

        System.out.println("Inserted sample data.");
    }

    @After
    public void testDelete() {
        System.out.println("Deleted " + service.deleteAll() + " document(s).");
    }

    @Test
    public void testGetByParams() throws Exception {
        System.out.println(service.getAll());
    }

    @Test
    public void testGetAll() throws Exception {
        final JSONObject params = new JSONObject();
        params.put("age", 21);
        System.out.println(service.getByParams(params));
    }

}
