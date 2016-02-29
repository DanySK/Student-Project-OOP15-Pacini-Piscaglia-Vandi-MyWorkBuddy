package it.unibo.oop.myworkoutbuddy.controller.db;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A service to perform CRUD (Create Read Update Delete) operations on elements in a database.
 */
public interface DBService {

    /**
     * Inserts a new element in the database.
     * 
     * @param element
     *            the element fields to insert
     * @return True if the user is successfully created, false if some errors occurs.
     */
    boolean create(Map<String, Object> element);

    /**
     * Inserts new elements in the database.
     * 
     * @param elements
     *            the elements to insert
     * @return the number of inserted elements
     */
    long create(Collection<? extends Map<String, Object>> elements);

    /**
     * Retrieves a document that satisfies the query parameters.
     * 
     * @param query
     *            the query to perform on the elements
     * @return all the elements stored in the database
     */
    Optional<Map<String, Object>> getOneByParams(Map<String, Object> query);

    /**
     * Retrieves all the elements stored.
     * 
     * @return all the elements stored
     */
    List<Map<String, Object>> getAll();

    /**
     * Retrieves an {@link Collections#emptyList} if no element was found, a non-empty {@link List} otherwise.
     * 
     * @param queryParams
     *            The filters to apply.
     * @return an {@link Collections#emptyList} if no element was found, a non-empty {@link List} otherwise
     */
    List<Map<String, Object>> getByParams(Map<String, Object> queryParams);

    /**
     * Updates all the elements that match the specified parameters with the new ones.
     * 
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
     * Deletes all the elements which match the specified parameters.
     * 
     * @param params
     *            The filters to apply.
     * @return The number of deleted elements.
     */
    long deleteByParams(Map<String, Object> params);

}
