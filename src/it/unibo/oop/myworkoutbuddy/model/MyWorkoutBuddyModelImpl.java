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
 * 
 *
 */
public class MyWorkoutBuddyModelImpl implements MyWorkoutBuddyModel {

    private List<User> listUser;
    private List<Account> listAccount;
    private List<GymTool> listGymTool;

    private Map<String, GymTool> mapGymTool;

    private Optional<User> loginUser;
    private Optional<Account> currentAccount;

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
        this.listUser.add(new UserImpl(account, person));
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
        this.loginUser = Optional.empty();
        // final Account tempAccount = new UserImpl().new AccountImpl(userName, password);
        this.listUser.forEach(t-> {
            if (t.getAccount().getUserName().equals(userName) && t.getAccount().getPassword().equals(password)) {
                this.loginUser = Optional.of(t);
            }
        });
    }

    /**
     * 
     * @return Optional version of loginUser
     */
    @Override
    public User getLoginUser() {
        return this.loginUser.get();
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
        return this.loginUser != null;
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
    public List<Double> performanceScore() {
        return this.getLoginUser().performanceScore();
    }

    /**
     * 
     * @return a list of measure for current user's body
     */
    @Override
    public Map<BodyPart, Double> performanceBodyPart() {
        return this.getLoginUser().performanceBodyPart();
    }

    /**
     * 
     * @return a list of measure for current user's body
     */
    @Override
    public Map<BodyZone, Double> performanceBodyZone() {
        return this.getLoginUser().performanceBodyZone();
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
    public List<Double> trendBodyMass() {
        return this.getLoginUser().trendBodyMass();
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
