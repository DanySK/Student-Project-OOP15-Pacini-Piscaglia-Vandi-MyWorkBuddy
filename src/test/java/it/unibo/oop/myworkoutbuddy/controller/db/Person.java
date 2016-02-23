package it.unibo.oop.myworkoutbuddy.controller.db;

import com.google.common.base.Preconditions;

public class Person {

    private final String lastName;
    private final String firstName;
    private final boolean married;
    private final int age;

    public static final class Builder {

        private String firstName;
        private String lastName;
        private boolean married;
        private int age = -1;

        private boolean built;

        public Builder() {
            built = false;
        }

        public Builder firstName(final String firstName) {
            Preconditions.checkState(!built);
            Preconditions.checkArgument(firstName != null && firstName != "");
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final String lastName) {
            Preconditions.checkState(!built);
            Preconditions.checkArgument(lastName != null && lastName != "");
            this.lastName = lastName;
            return this;
        }

        public Builder age(final int age) {
            Preconditions.checkState(!built);
            Preconditions.checkArgument(age > 0);
            this.age = age;
            return this;
        }

        public Builder married(final boolean married) {
            Preconditions.checkState(!built);
            this.married = married;
            return this;
        }

        public Person build() {
            Preconditions.checkState(!built);
            Preconditions.checkArgument(firstName != null && firstName != "");
            Preconditions.checkArgument(lastName != null && lastName != "");
            Preconditions.checkArgument(age > 0);
            return new Person(firstName, lastName, married, age);
        }

    }

    private Person(final String firstName, final String lastName, final boolean married, final int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.married = married;
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isMarried() {
        return married;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person [lastName=" + lastName + ", firstName=" + firstName + ", married=" + married + ", age=" + age
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + (married ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Person other = (Person) obj;
        if (age != other.age) return false;
        if (firstName == null) {
            if (other.firstName != null) return false;
        } else if (!firstName.equals(other.firstName)) return false;
        if (lastName == null) {
            if (other.lastName != null) return false;
        } else if (!lastName.equals(other.lastName)) return false;
        if (married != other.married) return false;
        return true;
    }

}
