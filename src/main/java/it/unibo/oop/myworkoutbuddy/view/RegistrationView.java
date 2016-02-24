package it.unibo.oop.myworkoutbuddy.view;

/**
 *
 * View used to register a new user fetching its personal info, ID and password.
 *
 */
public interface RegistrationView {

    /**
     * 
     * @return user Name
     */
    String getName();

    /**
     * 
     * @return user Surname
     */
    String getSurname();

    /**
     * 
     * @return user age
     */
    int getAge();

    /**
     * Get the new email of the user.
     * 
     * @return email
     */
    String getEmail();

    /**
     * Get the password of the new user.
     * 
     * @return password
     */
    String getPassword();

    /**
     * 
     * @return password confirmation.
     */
    String getPasswordConfirm();

    /**
     * 
     * Get the ID of the new user.
     * 
     * @return username
     */
    String getUsername();

    /**
     * 
     * Get the height for the new user.
     * 
     * @return height
     */
    int getHeight();

    /**
     * Get the weight of the new user.
     * 
     * @return weight
     */
    double getWeight();
}
