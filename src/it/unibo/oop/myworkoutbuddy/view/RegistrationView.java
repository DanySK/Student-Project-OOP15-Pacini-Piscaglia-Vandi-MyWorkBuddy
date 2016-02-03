package it.unibo.oop.myworkoutbuddy.view;

/**
 *
 * View used to register a new user fetching its personal info
 * like ID and password.
 *
 */
public interface RegistrationView {
    
    /**
     * 
     * @return user name
     */
    String getName();

    /**
     * 
     * @return user surname
     */
    String getSurname();

    /**
     * 
     * @return user age
     */
    int getAge();
    
    /**
     * Get the new email for the user.
     * 
     * @return email
     */
    String getEmail();

    
    /**
     * Get the password for the new user.
     * 
     * @return password
     */
    String getPassword();
}
