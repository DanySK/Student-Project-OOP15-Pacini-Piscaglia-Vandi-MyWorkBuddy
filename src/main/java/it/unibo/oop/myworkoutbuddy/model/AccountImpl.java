package it.unibo.oop.myworkoutbuddy.model;
/**
 Class Account to system access

     userName : name used to access the application
     password : password to access the application
     avatar : alter ego for user.
*/
public class AccountImpl implements Account {

    private final String userName;
    private final String password;

    /**
     * 
     * @param userName String
     * @param password String
     */
    public AccountImpl(final String userName, final String password) {

        this.userName = userName;
        this.password = password;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "AccountImpl [userName = " + this.getUserName() + ", password = " + this.getPassword() + "]";
    }
}
