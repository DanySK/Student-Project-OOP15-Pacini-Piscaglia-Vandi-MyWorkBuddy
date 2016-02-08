package it.unibo.oop.myworkoutbuddy.controller;

import java.util.List;
import java.util.Map;

/**
 * A generic service to interact with the database.
 * 
 * @param <T>
 *            the type of element to retrieve.
 */
public interface GenericService<T> {

    /**
     * Creates a document to insert in the database.
     * 
     * @param fields
     *            The element fields
     * @return True if the user is successfully created, false if some errors occurs.
     */
    boolean create(Map<String, Object> fields);

    /**
     * Retrieves all the elements stored.
     * 
     * @return all the elements stored.
     */
    List<T> getAll();

    /**
     * @param params
     *            The filters to apply.
     * @return an {@link Collections#emptyList} if no element was found. A non-empty {@link List} otherwise.
     */
    List<T> getByParams(Map<String, Object> params);

    /**
     * Deletes all the elements in database.
     * 
     * @return The number of deleted elements.
     */
    long deleteAll();

    /**
     * Deletes all the elements which match the given parameters.
     * 
     * @param params
     *            The filters to apply.
     * @return The number of deleted elements.
     */
    long deleteByParams(Map<String, Object> params);

}
