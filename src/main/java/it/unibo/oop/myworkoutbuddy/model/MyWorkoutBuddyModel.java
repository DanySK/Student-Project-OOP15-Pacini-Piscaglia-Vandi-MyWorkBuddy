package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * 
 * the principal class of model.
 * -------------------------------------------------------------
 */
public interface MyWorkoutBuddyModel {
    /**
     * get the account at list in index.
     * @param index int
     * @return an account at index of the Account list
     */
    Account getAccount(final int index);

    /**
     * user's login.
     * @param name String
     * @param password String
     */
    void loginUser(final String name, final String password);

    /**
     * the current user's logout.
     */
    void logoutUser();

    /**
     * add a new routine for current user.
     * @param code String
     * @param nameRoutine String
     * @param target String
     */
    void addRoutine(final String code, final String nameRoutine, final String target);

    /**
     * add a new Exercise.
     * @param nameRoutine String
     * @param target String
     * @param nameTool String
     * @param settingValue Integer
     * @param repetition Integer
     * @param time Integer
     * @param numSession Integer
     * @param pause Integer
     */
    void addGymExcercise(final String nameRoutine, final String target, final String nameTool, 
            final int settingValue, final int repetition, final int time, final int numSession, final int pause);

    /**
     * add a new Workout for current User.
     * @param nameRoutine String
     * @param localDate LocalDate
     * @param localTime LocalTime
     * @param state boolean
     */
    void addWorkout(final String nameRoutine, final LocalDate localDate, final LocalTime localTime, final boolean state);

    /**
     * add a new data of measure.
     * @param localDate LocalDate
     */
    void addDataMeasure(final LocalDate localDate);

    /**
     * add new muscles for the bodyZone.
     * @param bodyZone String
     * @param bodyParts Collection<String>
     */
    void addMapZone(final String bodyZone, final Collection<String> bodyParts);

    /**
     * add a new BodyMeasure for current User.
     * @param measureBodyZone String
     * @param measure Double
     * @param firstTime boolean
     */
    void addBodyMeasure(final String measureBodyZone, final Double measure, final boolean firstTime);

    /**
     * add a new score for the specified exercise by the numEx.
     * @param numEx Integer
     * @param score Integer
     */
    void addExerciseScore(final Integer numEx, final Integer score);

    /**
     * true if current User has been set.
     * @return boolean
     */
    boolean isLoginUser();

    /**
     * Add a new Account.
     * @param userName String
     * @param password String
     * @param avatar String
     */
    void addAccount(final String userName, final String password, final String avatar); //throws ExistentAccount;

    /**
     * Add a new User.
     * @param firstName String
     * @param secondName String
     * @param age Integer
     * @param email String
     */
    void addUser(final String firstName, final String secondName, final int age, final String email);

    /**
     * add a new GymTool.
     * @param description String
     * @param nameTool String
     * @param nameImage String
     * @param num Integer
     * @param valueMin Integer
     * @param valueMax Integer
     */
    void addGymTool(final String description, final String nameTool, final String nameImage, final int num, final int valueMin, final int valueMax);

    /**
     * add a new muscle with relative percentage measure in the tool specified by the toolCode.
     * @param toolCode String
     * @param bodyPart String
     * @param percentage Double
     */
    void addBodyPart(final String toolCode, final String bodyPart, final Double percentage);

    /**
     * give the list of GymTool in an application.
     * @return a List<GymTool>
     */
    List<GymTool> getGymToolList();

    /**
     * give the list of Users.
     * @return a List<User>
     */
    List<User> getUserList();

    /**
     * give for each application GymTool name the relatives tool.
     * @return a Map<String, GymTool>
     */
    Map<String, GymTool> getMapGymTool();

    /**
     * give the dimension of an exercise list with codeRoutine.
     * @param codeRoutine String
     * @return an Integer
     */
    int getNumExercise(final String codeRoutine);

    /**
     * give the application Body.
     * @return a Body
     */
    Body getBody();

    /**
     * give the current user's measure list.
     * @return a List<BodyData>
     */
    List<BodyData> getMeasureList();

    /**
     * give the current user's routine list.
     * @return a List<Routine>
     */
    List<Routine> getRoutineList();

    /**
     * give the current user's Workout list.
     * @return a List<Workout>
     */
    List<Workout> getWorkoutList();

    /**
     * give the current user's Workout score list.
     * @return a List<Double>
     */
    List<Double> scoreWorkout();

    /**
     * give the associations between muscles and relative scores obtained.
     * @return a Map<String, Double>
     */
    Map<String, Double> scoreBodyPart();

    /**
     * give the associations between parts of body and relative scores obtained.
     * @return a Map<String, Double>
     */
    Map<String, Double> scoreBodyZone();

    /**
     * give the associations between gymTool names and relative scores obtained.
     * @return a Map<String, Double>
     */
    Map<String, Double> scoreGymTool();

    /**
     * give the associations between muscles and relative times obtained.
     * @return a Map<String, Double>
     */
    Map<String, Double> timeBodyPart();

    /**
     * give the associations between parts of body and relative times obtained.
     * @return a Map<String, Double>
     */
    Map<String, Double> timeBodyZone();

    /**
     * 
     * give the associations between gymTool names and relative scores obtained.
     * @return a Map<String, Double>
     */
    Map<String, Double> timeGymTool();

    /**
     * give the current User's trending of Body Mass.
     * @return a List<Double>
     */
    List<Double> trendBodyMass();

    /**
     * 
     * give the current User's trending of BMI.
     * @return a List<Double>
     */
    List<Double> trendBodyBMI();
}
