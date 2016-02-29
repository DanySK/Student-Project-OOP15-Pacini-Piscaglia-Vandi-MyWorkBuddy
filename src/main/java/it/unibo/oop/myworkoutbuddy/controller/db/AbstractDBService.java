package it.unibo.oop.myworkoutbuddy.controller.db;

/**
 * An abstract representation of a service to interact with some form of database.
 * All the database APIs to interact with the database should inherit from this class.
 *
 */
public abstract class AbstractDBService implements DBService {

    private final String tableName;

    /**
     * @param tableName
     *            the name of the table
     */
    protected AbstractDBService(final String tableName) {
        this.tableName = tableName;
    }

    /**
     * Returns the name of the table used by this {@link DBService}.
     * 
     * @return the name of the table used
     */
    protected String getTableName() {
        return tableName;
    }

}
