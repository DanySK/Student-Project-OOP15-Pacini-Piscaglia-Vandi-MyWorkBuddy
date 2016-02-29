package it.unibo.oop.myworkoutbuddy.controller.db;

import static org.junit.Assert.assertEquals;

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
        test1.put("weight", 90);
        test1.put("height", 150);
        final Map<String, Object> test2 = new HashMap<>();
        test2.put("username", "lucarossi");
        test2.put("password", "test2015");
        test2.put("email", "luca.rossi@unibo.it");
        test2.put("age", 20);
        test2.put("name", "Luca");
        test2.put("surname", "Rossi");
        test2.put("weight", 90);
        test2.put("height", 150);
        tests.addAll(Arrays.asList(test1, test2));
        testService.create(tests);
        assertEquals(testService.getAll().size(), tests.size());
    }

    @After
    public void testDelete() {
        testService.deleteAll();
    }

    @Test
    public void testDates() throws Exception {
        final Date date = new Date();
        final DBService datesTesting = new MongoService("date_tetsting");
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
