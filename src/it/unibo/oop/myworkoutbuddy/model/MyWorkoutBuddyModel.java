package it.unibo.oop.myworkoutbuddy.model;

import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.model.User.Account;

/**
 * 
 * 
 */
public interface MyWorkoutBuddyModel {
    /**
     * login of User : set current login User.
     * @param userName String
     * @param password String
     */
    void loginUser(final String userName, final String password);

    /**
     * 
     * @return current login User, in Optional form
     */
    Optional<User> getLoginUser();

    /**
     * Add a new Account.
     * @param name String
     * @param password String
     */
    void addAccount(final String name, final String password);

    /**
     * Add a new User.
     * @param account Account
     * @param userData UserData
     * @param email String
     */
    void addUser(final Account account, final UserData userData, final String email);

    /**
     * Add a new GymTool.
     * @param toolName String
     * @param fileImage String
     * @param vMin int
     * @param vMax int 
     * @param numMax int
     */
    void addGymTool(final String toolName, final String fileImage, final int vMin, final int vMax, final int numMax);

    /**
     * test method for example data.
     */
    void testData1();
}
