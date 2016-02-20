package it.unibo.oop.myworkoutbuddy.model;

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
public interface MyWorkoutBuddyModel {
    /**
     * 
     * @param index int
     * @return an account at index of the Account list
     */
    Account getAccount(final int index);

    /**
     * login of User : set current login User.
     * @param userName String
     * @param password String
     */
    void loginUser(final String userName, final String password);

    /**
     * 
     * @return current login User, in Optional form
     */
    User getLoginUser();

    /**
     * Add a new Account.
     * @param account Account
     * @throws ExistentAccount 
     */
    void addAccount(final Account account) throws ExistentAccount;

    /**
     * 
     * @param gymTool GymTool
     */
    void addGymTool(final GymTool gymTool);

    /**
     * Add a new User.
     * @param account Account
     * @param userData UserData
     */
    void addUser(final Account account, final Person userData);

    /**
     * 
     * @param userData UserData
     */
    void addUser(Person userData);

    /**
     * 
     * @param code String
     * @return current login User, in Optional form
     */
    Optional<GymTool> getGymTool(final String code);

    /**
     * 
     * @return the list of GymTool in an application
     */
    List<GymTool> getGymToolList();

    /**
     * 
     * @return the list of Users
     */
    List<User> getUserList();

    /**
     * 
     * @return true if exists the current user
     */
    boolean isCurrentUser();

    /**
     * 
     * @return a list of current user's body measures
     */
    List<BodyData> getMeasureList();

    /**
     * 
     * @return list of performance score for the current User
     */
    List<Double> scoreWorkout();

    /**
     * 
     * @return map of performanceBodyPart for the current User
     */
    Map<BodyPart, Double> scoreBodyPart();

    /**
     * 
     * @return map of performanceBodyZone for the current User
     */
    Map<BodyZone, Double> scoreBodyZone();

    /**
     * 
     * @return map of timeBodyPart for the current User
     */
    Map<BodyPart, Double> timeBodyPart();

    /**
     * 
     * @return map of timeBodyZone for the current User
     */
    Map<BodyZone, Double> timeBodyZone();

    /**
     * 
     * @return map of timeGymTool for the current User
     */
    Map<String, Double> timeGymTool();

    /**
     * 
     * @return list of trendBodyMass for the current User
     * @throws IllegalArgumentException 
     * @throws NullPointerException 
     */
    List<Double> trendBodyMass() throws NullPointerException, IllegalArgumentException;

    /**
     * 
     * @return list of trendBMI for the current User
     * @throws IllegalArgumentException 
     * @throws NullPointerException 
     */
    List<Double> trendBodyBMI() throws NullPointerException, IllegalArgumentException;
}
