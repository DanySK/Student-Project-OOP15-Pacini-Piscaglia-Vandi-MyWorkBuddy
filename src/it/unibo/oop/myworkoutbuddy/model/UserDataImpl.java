package it.unibo.oop.myworkoutbuddy.model;

/**
 * 
 * User's general data.
 * -------------------------------------------------------------
 */
 
public class UserDataImpl implements UserData {

    private String firstName;
    private String lastName;
    private int age;
    private String email;

    /**
     * 
     * @param firstName String
     * @param lastName String
     * @param email String
     * @param age int
     */
    public UserDataImpl(final String firstName, final String lastName,
            final int age, final String email) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAge(age);
        this.setEmail(email);
    }

    private void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    private void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    private void setAge(final int age) {
        this.age = age;
    }
    private void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String getFirstName() {
        // TODO Auto-generated method stub
        return this.firstName;
    }

    @Override
    public String getLastName() {
        // TODO Auto-generated method stub
        return this.lastName;
    }

    @Override
    public int getAge() {
        // TODO Auto-generated method stub
        return this.age;
    }

    @Override
    public String getEmail() {
        // TODO Auto-generated method stub
        return this.email;
    }
}
