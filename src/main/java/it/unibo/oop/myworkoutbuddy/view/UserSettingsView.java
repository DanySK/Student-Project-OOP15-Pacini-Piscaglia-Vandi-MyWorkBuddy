package it.unibo.oop.myworkoutbuddy.view;

/**
 *
 * Provides functions to get Users modified parameters.
 *
 */
public interface UserSettingsView {

    /**
     * 
     * @return new user name
     */
    String getNewName();

    /**
     * 
     * @return new user surname
     */
    String getNewSurname();

    /**
     * 
     * @return new user age
     */
    int getNewAge();

    /**
     * Get the new email for the user.
     * 
     * @return new Email
     */
    String getNewEmail();

    /**
     * Get the new password for the user.
     * 
     * @return new Password
     */
    String getNewPassword();

    /**
     * Get the password confirm.
     * 
     * @return password confirm.
     */
    String getPasswordConfirm();

}
