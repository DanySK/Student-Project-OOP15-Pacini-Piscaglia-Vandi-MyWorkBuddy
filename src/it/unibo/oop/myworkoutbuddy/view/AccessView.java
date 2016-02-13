package it.unibo.oop.myworkoutbuddy.view;

/**
 * 
 * AccessWiew interface that provides functions to fetch input to login.
 * 
 */
public interface AccessView {

    /**
     * 
     * @return Username.
     * 
     */
    String getUsername();

    /**
     * 
     * @return UserPassword.
     * 
     */
    String getPassword();

    /**
     * 
     * @return the chosen style for the GUI.
     * 
     */
    String getStyle();

}
