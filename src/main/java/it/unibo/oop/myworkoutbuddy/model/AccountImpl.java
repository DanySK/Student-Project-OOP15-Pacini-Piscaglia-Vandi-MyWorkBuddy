package it.unibo.oop.myworkoutbuddy.model;
/**
 Class Account to system access

     userName : name used to access the application
     password : password to access the application
     avatar : alter ego for user.
     -------------------------------------------------------------
*/
public class AccountImpl implements Account {

    private final String userName;
    private final String password;
    private final String avatar;

    /**
     * 
     * @param userName String
     * @param password String
     * @param avatar String
     */
    public AccountImpl(final String userName, final String password, final String avatar) {

        this.userName = userName;
        this.password = password;
        this.avatar = avatar;
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
    public String getAvatar() {
        return this.avatar;
    }

    @Override
    public String toString() {
        return "AccountImpl [userName = " + this.getUserName() + ", password = " + this.getPassword() + "Avatar = " + this.getAvatar() + "]";
    }
}
