package it.unibo.oop.myworkoutbuddy.controller;

import java.util.List;
import java.util.Map;

/**
 * A service to interact with the database.
 */
public interface Service {

    /**
     * Creates a new element to insert in the database.
     * 
     * @param fields
     *            the element fields to insert
     * @return True if the user is successfully created, false if some errors occurs.
     */
    boolean create(Map<String, Object> fields);

    /**
     * Creates as many elements as the fields in the given list and inserts them in the database.
     * 
     * @param elements
     *            the elements to insert
     * @return true if the element is successfully created, false if some errors occurs
     */
    boolean create(List<Map<String, Object>> elements);

    /**
     * Retrieves all the elements stored.
     * 
     * @return all the elements stored.
     */
    List<Map<String, Object>> getAll();

    /**
     * @param params
     *            The filters to apply.
     * @return an {@link Collections#emptyList} if no element was found. A non-empty {@link List} otherwise.
     */
    List<Map<String, Object>> getByParams(Map<String, Object> params);

    /**
     * @param queryParams
     *            the query filters
     * @param updateParams
     *            the fields to update with the new value.
     * @return the number of modified documents
     */
    long updateByParams(final Map<String, Object> queryParams, final Map<String, Object> updateParams);

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
