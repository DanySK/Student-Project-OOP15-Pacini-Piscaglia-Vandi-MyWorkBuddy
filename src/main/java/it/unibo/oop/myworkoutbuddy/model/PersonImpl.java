package it.unibo.oop.myworkoutbuddy.model;
/**
 User's general data.

     firstName : name 
     lastName : surname
     age : 
     email : 
     -------------------------------------------------------------
*/
public class PersonImpl implements Person {

    private String firstName;
    private String lastName;
    private Integer age;
    private String email;

    /**
     * 
     * @param firstName String
     * @param lastName String
     * @param email String
     * @param age int
     */
    public PersonImpl(final String firstName, final String lastName, final Integer age, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public Integer getAge() {
        return this.age;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "PersonImpl [firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", age=" + this.getAge() + ", email=" + this.getEmail()
                + "]";
    }
}
