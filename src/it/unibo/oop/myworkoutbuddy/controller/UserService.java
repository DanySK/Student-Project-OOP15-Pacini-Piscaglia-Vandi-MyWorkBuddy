package it.unibo.oop.myworkoutbuddy.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;

import it.unibo.oop.myworkoutbuddy.model.User;

public interface UserService extends GenericService<User> {

    /**
     * Creates a new user.
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
    List<User> getAll();

    /**
     * 
     * @param id
     *            The user identifier
     * @return an {@link Optional#empty} if an user with the given ID was't found. An {@link Optional#of} if the user
     *         was found.
     */
    Optional<User> getById(ObjectId id);

    /**
     * 
     * @param params
     *            The user fields to look (username, firstName, password, etc...)
     * @return an {@link Collections#emptyList} if no user was found.
     */
    List<User> getByParams(Map<String, Object> params);

    /**
     * 
     * @param id
     * @return
     */
    boolean deleteById(ObjectId id);

}
