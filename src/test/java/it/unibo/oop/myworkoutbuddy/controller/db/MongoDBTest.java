package it.unibo.oop.myworkoutbuddy.controller.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.oop.myworkoutbuddy.controller.Service;

public class MongoDBTest {

    private final Service service = new MongoService("people");

    @Before
    public void testCreate() {
    }

    @After
    public void testDelete() {
    }

    @Test
    public void testGetByParams() throws Exception {
    }

    @Test
    public void testGetAll() throws Exception {
    }

}
