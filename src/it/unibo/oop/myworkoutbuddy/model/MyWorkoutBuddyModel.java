package it.unibo.oop.myworkoutbuddy.model;

import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.model.User.Account;

/**
 * 
 *
 */
public interface MyWorkoutBuddyModel {
    /**
     * 
     * @param userName String
     * @param password String
     */
    void loginUser(final String userName, final String password);
    /**
     * 
     * @param name String
     * @param password String
     */
    void addAccount(final String name, final String password);
    /**
     * 
     * @param account Account
     * @param userData UserData
     * @param email String
     */
    void addUser(final Account account, final UserData userData, final String email);
    /**
     * 
     * @return User of Login, in Optional form
     */
    Optional<User> getLoginUser();
    /**
     * 
     * @param toolName String
     * @param fileImage String
     * @param vMin int
     * @param vMax int 
     * @param numMax int
     */
    void addGymTool(final String toolName, final String fileImage, final int vMin, final int vMax, final int numMax);
}
