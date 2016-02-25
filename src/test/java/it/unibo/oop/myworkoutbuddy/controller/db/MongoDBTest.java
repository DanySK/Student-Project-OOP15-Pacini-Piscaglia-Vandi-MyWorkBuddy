package it.unibo.oop.myworkoutbuddy.controller.db;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import it.unibo.oop.myworkoutbuddy.controller.Service;
import it.unibo.oop.myworkoutbuddy.util.DateFormats;

public class MongoDBTest {

    @Test
    public void testDates() throws Exception {
        final Date date = new Date();
        final Service datesTesting = new MongoService("date_tetsting");
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
