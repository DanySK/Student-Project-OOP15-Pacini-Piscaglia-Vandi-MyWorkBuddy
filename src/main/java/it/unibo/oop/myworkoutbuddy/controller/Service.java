package it.unibo.oop.myworkoutbuddy.controller;

import it.unibo.oop.myworkoutbuddy.controller.db.DBService;
import it.unibo.oop.myworkoutbuddy.controller.db.mongodb.MongoService;

/**
 * An enum that describes all the services that the controller needs.
 */
public enum Service {

    /**
     * The service for interact with the body zones collection.
     */
    BODY_ZONES,
    /**
     * The service for interact with the exercises collection.
     */
    EXERCISES,
    /**
     * The service for interact with the gym tools collection.
     */
    GYM_TOOLS,
    /**
     * The service for interact with the user's measures collection.
     */
    MEASURES,
    /**
     * The service for interact with the results collection.
     */
    RESULTS,
    /**
     * The service for interact with the routines collection.
     */
    ROUTINES,
    /**
     * The service for interact with the users collection.
     */
    USERS;

    private final DBService service;

    Service() {
        service = new MongoService(toString());
    }

    /**
     * Returns the {@link DBService} associated to this enum value.
     * 
     * @return the {@link DBService} associated to this enum value
     */
    public DBService getDBService() {
        return service;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
