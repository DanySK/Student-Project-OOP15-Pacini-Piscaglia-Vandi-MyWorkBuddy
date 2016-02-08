package it.unibo.oop.myworkoutbuddy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.model.User.Account;

/**
 * 
 *
 */
public class MyWorkoutBuddyModel {

    private static final int AGE = 20;

    private List<User> listUser;
    private List<Account> listAccount;
    private List<GymTool> listGymTool;

    private Account loginUser;
    /**
     * 
     */
    public MyWorkoutBuddyModel() {
        this.listUser = new ArrayList<>();
        this.listAccount = new ArrayList<>();
        this.listGymTool = new ArrayList<>();

        /* Test : Account Data*/
        this.addAccount("account1", "password1");
        this.addAccount("account2", "password2");
        this.addAccount("account3", "password3");

        /* Test : User Data*/
        this.addUser(this.getAccount(0), new UserDataImpl("Pacini", "Lorenzo", AGE, "lorenzo.pacini@studio.unibo.it"), "avatar0.png");
        this.addUser(this.getAccount(1), new UserDataImpl("Piscaglia", "Nicola", AGE, "nicola.piscaglia@studio.unibo.it"), "avatar1.png");
        this.addUser(this.getAccount(2), new UserDataImpl("Vandi", "Mattia", AGE, "mattia.vandi@studio.unibo.it"), "avatar2.png");

        /* Test : GymTool Data*/
        this.addGymTool("Tapis Roulant", "image1.png", 10, 1, 10);
        this.addGymTool("Gym Tool2", "image2.png", 10, 1, 10);
        this.addGymTool("Gym Tool3", "image3.png", 10, 1, 10);

    }
    /**
     * 
     * @param indice int
     * @return the Account at indice pos of listAccount
     */
    private Account getAccount(final int indice) {
        return this.listAccount.get(indice);
    }

    /**
     * 
     * @param toolName String 
     * @param fileImage String 
     * @param vMin int
     * @param vMax int
     * @param numMax int
     */
    public void addGymTool(final String toolName, final String fileImage, final int vMin, final int vMax, final int numMax) {
        this.listGymTool.add(new GymToolImpl(toolName, fileImage, vMin, vMax, numMax));
    }
    /**
     * 
     * @param account Account
     * @param userData UserData
     * @param email String
     */
    public void addUser(final Account account, final UserData userData, final String email) {
        this.listUser.add(new UserImpl(account, userData, email));
    }
    /**
     * 
     * @param name String
     * @param password String
     */
    public void addAccount(final String name, final String password) {
        this.listAccount.add(new UserImpl().new AccountImpl(name, password));
    }
    /**
     * 
     * @param userName String
     * @param password String
     */
    public void loginUser(final String userName, final String password) {
        this.listAccount.forEach(t-> {
            if (t.getUserName().equals(userName) && t.getPassword().equals(password)) {
                this.loginUser = t;
            }
        });
    }
    /**
     * 
     * @return Optional version of loginUser
     */
    public Optional<Account> getLoginUser() {
        return Optional.of(this.loginUser);
    }
}
