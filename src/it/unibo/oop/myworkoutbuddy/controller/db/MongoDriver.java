package it.unibo.oop.myworkoutbuddy.controller.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

/**
 * Class to interact with MongoClient on {@link on https://www.mongolab.com/}.
 */
public final class MongoDriver {

    private static MongoClient client;

    private static final String URL = "mongodb://mattiavandi:myworkoutbuddy2015@ds059165.mongolab.com:59165/myworkoutbuddy";

    private static final MongoClientURI CLIENT_URI = new MongoClientURI(URL);

    /**
     * @return The connection with a specific URI.
     * @throws MongoException
     *             if some connection error occurs.
     */
    public static MongoClient getMongoClient() {
        if (client == null) {
            client = new MongoClient(CLIENT_URI);
        }
        return client;
    }

    private MongoDriver() {
        throw new IllegalAccessError("No instances of " + getClass().getName());
    }

}
