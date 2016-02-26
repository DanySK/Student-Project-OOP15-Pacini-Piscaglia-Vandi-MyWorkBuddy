package it.unibo.oop.myworkoutbuddy.model;

/**
 * Class to access system.
 */
public interface Account {
    /**
     * 
     * @return userName
     */
    String getUserName();

    /**
     * 
     * @return userPassword
     */
    String getPassword();

    /**
     * 
     * @return avatar of Account
     */
    String getAvatar();
}
