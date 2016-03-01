package it.unibo.oop.myworkoutbuddy.controller.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.oop.myworkoutbuddy.controller.db.mongodb.MongoService;
import it.unibo.oop.myworkoutbuddy.util.DateFormats;

public class MongoDBTest {

    private final DBService testService = new MongoService("tests");

    @Before
    public void testCreate() {
        final List<Map<String, Object>> tests = new ArrayList<>();
        final Map<String, Object> test1 = new HashMap<>();
        test1.put("username", "marcorossi");
        test1.put("password", "test2015");
        test1.put("email", "marco.rossi@unibo.it");
        test1.put("age", 20);
        test1.put("name", "Marco");
        test1.put("surname", "Rossi");
        final Map<String, Object> test2 = new HashMap<>();
        test2.put("username", "lucarossi");
        test2.put("password", "test2015");
        test2.put("email", "luca.rossi@unibo.it");
        test2.put("age", 20);
        test2.put("name", "Luca");
        test2.put("surname", "Rossi");
        final Map<String, Object> test3 = new HashMap<>();
        test3.put("username", "ginopaoli");
        test3.put("password", "test2015");
        test3.put("email", "gino.paoli@unibo.it");
        test3.put("age", 20);
        test3.put("name", "Gino");
        test3.put("surname", "Paoli");
        tests.addAll(Arrays.asList(test1, test2, test3));
        testService.create(tests);
        assertEquals(testService.getAll().size(), tests.size());
    }

    @After
    public void testDelete() {
        testService.deleteAll();
    }

    @Test
    public void testGetMethods() {
        final Map<String, Object> params = new HashMap<>();
        params.put("username", "ginopaoli");
        assertTrue(testService.getOneByParams(params).isPresent());

        final Map<String, Object> params2 = new HashMap<>();
        params2.put("surname", "Rossi");
        assertEquals(testService.getByParams(params2).size(), 2);

        final Map<String, Object> params3 = new HashMap<>();
        params3.put("age", 20);
        assertEquals(testService.getByParams(params3).size(), 3);
    }

    @Test
    public void testDates() throws Exception {
        final Date date = new Date();
        final DBService datesTesting = new MongoService("date_tests");
        final Map<String, Object> map = new HashMap<>();
        map.put("date", DateFormats.toUTCString(date));
        System.out.println(map);
        datesTesting.create(map);
        datesTesting.getAll().stream()
                .map(m -> (String) m.get("date"))
                .map(DateFormats::parseUTC)
                .forEach(System.out::println);
        datesTesting.deleteAll();
    }

}
