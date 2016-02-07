package it.unibo.oop.myworkoutbuddy.controller.db;

public class Person {

    private final String lastName;
    private final String firstName;
    private final boolean married;
    private final int age;

    public Person(final String firstName, final String lastName, final boolean married, final int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.married = married;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [lastName=" + lastName + ", firstName=" + firstName + ", married=" + married + ", age=" + age
                + "]";
    }

}
