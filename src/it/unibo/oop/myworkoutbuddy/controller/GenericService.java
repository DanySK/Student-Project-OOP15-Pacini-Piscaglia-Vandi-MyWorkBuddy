package it.unibo.oop.myworkoutbuddy.controller;

import java.util.List;
import java.util.Map;

public interface GenericService<T> {

    /**
     * Creates a document to insert in the database.
     * 
     * @param fields
     *            The user fields (username, firstName, password, etc...)
     * @return True if the user is successfully created, false if some errors occurs.
     */
    boolean create(Map<String, Object> fields);

    /**
     * Retrieves all the users stored in the database.
     * 
     * @return all the users stored in the database
     */
    List<T> getAll();

    /**
     * 
     * @param params
     *            The fields to look for.
     * @return an {@link Collections#emptyList} if no user was found. An unmodifiable list if something was found.
     */
    List<T> getByParams(Map<String, Object> params);

    /**
     * 
     * @param params
     *            The fields to look for.
     * @return True if at least one element was deleted, false otherwise.
     */
    long deleteByParams(Map<String, Object> params);

}
