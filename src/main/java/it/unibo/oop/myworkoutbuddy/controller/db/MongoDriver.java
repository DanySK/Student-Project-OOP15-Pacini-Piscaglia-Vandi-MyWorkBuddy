package it.unibo.oop.myworkoutbuddy.controller.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.bson.Document;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;

/**
 * Class to interact with the server.
 */
public final class MongoDriver {

    private static final String CONFIG_FILE = "/resources/db/config.yaml";

    private static MongoClient client;

    private static String url;

    private static String dbName;

    private static MongoClientURI uri;

    static {
        final InputStream file = MongoDriver.class.getResourceAsStream(CONFIG_FILE);
        try {
            @SuppressWarnings("unchecked")
            final Map<String, Object> config = (Map<String, Object>) new YamlReader(
                    new BufferedReader(new InputStreamReader(file))).read();
            url = config.get("url").toString();
            dbName = config.get("db_name").toString();
            uri = new MongoClientURI(url);
        } catch (final YamlException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The name of the database in use by this driver.
     */
    public static String getDatabaseName() {
        return dbName;
    }

    /**
     * Returns the specified collection.
     * 
     * @param databaseName
     *            the name of the database
     * @param collectionName
     *            the name of the collection to return
     * @return the specified collection.
     */
    public static MongoCollection<Document> getCollection(final String databaseName, final String collectionName) {
        return getMongoClient()
                .getDatabase(databaseName)
                .getCollection(collectionName);
    }

    /**
     * Returns the specified collection.
     * 
     * @param collectionName
     *            the name of the collection to return
     * @return the specified collection.
     */
    public static MongoCollection<Document> getCollection(final String collectionName) {
        return getCollection(getDatabaseName(), collectionName);
    }

    /**
     * @return The connection with a specific URI.
     * @throws MongoException
     *             if some connection error occurs.
     */
    private static MongoClient getMongoClient() {
        if (client == null) {
            client = new MongoClient(uri);
        }
        return client;
    }

    private MongoDriver() {
        throw new IllegalAccessError("No instances of " + getClass().getName());
    }

}
