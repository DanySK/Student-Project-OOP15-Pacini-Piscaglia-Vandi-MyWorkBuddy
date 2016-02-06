package it.unibo.oop.myworkoutbuddy.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;

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
     * @param id
     *            The identifier
     * @return an {@link Optional#empty} if an user with the given ID was't found. An {@link Optional#of} if the user
     *         was found.
     */
    Optional<T> getById(ObjectId id);

    /**
     * 
     * @param params
     *            The Fields to look for.
     * @return an {@link Collections#emptyList} if no user was found. An unmodifiable list if something was found.
     */
    List<T> getByParams(Map<String, Object> params);

    /**
     * 
     * @param id
     *            The identifier
     * @return True deleted, false otherwise.
     */
    boolean deleteById(ObjectId id);

}
