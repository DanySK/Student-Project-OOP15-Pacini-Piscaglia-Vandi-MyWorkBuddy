package it.unibo.oop.myworkoutbuddy.model;
/**
 * 
 * User's general data.
 * -------------------------------------------------------------
 */
public interface UserData {
    /**
     * 
     * @return the Name of User
     */
    String getFirstName();
    /**
     * 
     * @return the Surname of User
     */
    String getLastName();
    /**
     * 
     * @return the birthDate of User
     */
    int getAge();
    /**
     * 
     * @return the email of the User
     */
    String getEmail();
}
