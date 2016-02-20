package it.unibo.oop.myworkoutbuddy.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyData;
import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;
import it.unibo.oop.myworkoutbuddy.model.Body.BodyZone;
/**
 Application data.

     listUser : list of all memorized users
     listAccount : list of all memorized accounts
     listGymTool : list of all memorized GymTool

     mapGymTool : map <name of GymTool, GymTool>

     loginUser : current User
     currentAccount : current Account
*/
public class MyWorkoutBuddyModelImpl implements MyWorkoutBuddyModel {

    private List<User> listUser;
    private List<Account> listAccount;
    private List<GymTool> listGymTool;

    private Map<String, GymTool> mapGymTool;

    private Optional<User> currentUser = Optional.empty();
    private Optional<Account> currentAccount = Optional.empty();

    /**
     * 
     */
    public MyWorkoutBuddyModelImpl() {
        this.listUser = new ArrayList<>();
        this.listAccount = new ArrayList<>();
        this.listGymTool = new ArrayList<>();

        this.mapGymTool = new HashMap<>();
    }

    /**
     * 
     * @param indice int
     * @return the Account at indice pos of listAccount
     */
    @Override
    public Account getAccount(final int indice) {
        return this.listAccount.get(indice);
    }

    /**
     * 
     */
    @Override
    public void addGymTool(final GymTool gymTool) {
        this.listGymTool.add(gymTool);
        this.mapGymTool.put(gymTool.getCode(), gymTool);
    }

    /**
     * 
     * @param account Account
     * @param userData Person
     */
    @Override
    public void addUser(final Account account, final Person person) {

        if (!this.isAccount(account)) {
            this.listAccount.add(account);
        }

        this.currentAccount = Optional.of(account);
        this.listUser.add(new UserImpl(account, person)); // add this user to list user
    }

    /**
     * 
     * @param userData Person
     */
    @Override
    public void addUser(final Person userDatal) {
        this.addUser(this.currentAccount.get(), userDatal);
    }

    /**
     * 
     * @param name String
     * @param password String
     * @param avatar String
     */
    @Override
    public void addAccount(final Account account) {

        if (this.isAccount(account)) {
            return;
        }
        this.currentAccount = Optional.of(account);
        this.listAccount.add(this.currentAccount.get());
    }

    /**
     * 
     * @param userName String
     * @param password String
     */
    @Override
    public void loginUser(final String userName, final String password) {
        // final Account tempAccount = new UserImpl().new AccountImpl(userName, password);
        this.listUser.forEach(t-> {
            if (t.getAccount().getUserName().equals(userName) && t.getAccount().getPassword().equals(password)) {
                this.currentUser = Optional.of(t);
            }
        });
    }

    /**
     * 
     * @return Optional version of loginUser
     */
    @Override
    public User getLoginUser() {
        return this.currentUser.get();
    }

    /**
     * 
     * @param code String
     * @return Optional version of a GymTool, mapped for the param code
     */
    @Override
    public Optional<GymTool> getGymTool(final String code) {
        return Optional.of(this.mapGymTool.get(code));
    }

    /**
     * 
     * @return List<GymTool>
     */
    @Override
    public List<GymTool> getGymToolList() {
        return this.listGymTool;
    }

    /**
     * @return all users in the application
     */
    @Override
    public List<User> getUserList() {
        return this.listUser;
    }

    /**
     * 
     * @return map of Code Tool and relative Tool
     */
    public Map<String, GymTool> getMapGymTool() {
        return this.mapGymTool;
    }

    /**
     * 
     * @return true if exist the current user
     */
    @Override
    public boolean isCurrentUser() {
        return this.currentUser.isPresent();
    }

    /**
     * 
     * @return a list of measure for current user's body
     */
    @Override
    public List<BodyData> getMeasureList() {
        return this.getLoginUser().getMeasureList();
    }

    /**
     * 
     * @return a list of measure for current user's body
     */
    @Override
    public List<Double> scoreWorkout() {
        return this.getLoginUser().scoreWorkout();
    }

    /**
     * 
     * @return a list of measure for current user's body
     */
    @Override
    public Map<BodyPart, Double> scoreBodyPart() {
        return this.getLoginUser().scoreBodyPart();
    }

    /**
     * 
     * @return a list of measure for current user's body
     */
    @Override
    public Map<BodyZone, Double> scoreBodyZone() {
        return this.getLoginUser().scoreBodyZone();
    }

    /**
     * 
     * @return a list of time body part for the current user
     */
    @Override
    public Map<BodyPart, Double> timeBodyPart() {
        return this.getLoginUser().timeBodyPart();
    }

    /**
     * 
     * @return a list of time body zone for the current user
     */
    @Override
    public Map<BodyZone, Double> timeBodyZone() {
        return this.getLoginUser().timeBodyZone();
    }

    /**
     * 
     * @return a list of gym tool times for the current user
     */
    @Override
    public Map<String, Double> timeGymTool() {
        return this.getLoginUser().timeGymTool();
    }

    /**
     * 
     * @return trend of current user' s body mass
     */
    @Override
    public List<Double> trendBodyMass() throws NullPointerException, IllegalArgumentException {
        return this.getLoginUser().trendBodyMass();
    }

    /**
     * 
     * @return trend of current user' s body mass
     * @throws InvalidValueException 
     * @throws NullPointerException 
     */
    @Override
    public List<Double> trendBodyBMI() throws NullPointerException, IllegalArgumentException {
        return this.getLoginUser().trendBodyBMI();
    }

    private boolean isAccount(final Account account) {
        return !this.listAccount.stream().noneMatch(i->i.equals(account));
    }

}

class ExistentAccount extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6352669332423280257L;
}

class ErrorLogin extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6352669332423280257L;
}
