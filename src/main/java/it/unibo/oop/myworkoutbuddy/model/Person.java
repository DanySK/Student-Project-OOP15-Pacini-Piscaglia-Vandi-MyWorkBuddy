package it.unibo.oop.myworkoutbuddy.model;
/**
 * 
 * User's general data.
 */
public interface Person {
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
    Integer getAge();

    /**
     * 
     * @return the email of the User
     */
    String getEmail();
}
