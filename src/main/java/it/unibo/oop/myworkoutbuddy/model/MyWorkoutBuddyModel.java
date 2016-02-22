package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
     * 
     * @param name String
     * @param password String
     */
    void loginUser(final String name, final String password);

    /**
     * 
     * @param code String
     * @param nameRoutine String
     * @param target String
     */
    void addRoutine(final String code, final String nameRoutine, final String target);

    /**
     * 
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
     * 
     * @param nameRoutine String
     * @param localDate LocalDate
     * @param localTime LocalTime
     * @param state boolean
     */
    void addWorkout(final String nameRoutine, final LocalDate localDate, final LocalTime localTime, final boolean state);

    /**
     * 
     * @param localDate LocalDate
     */
    void addDataMeasure(final LocalDate localDate);

    /**
     * 
     * @param bodyZone String
     * @param bodyParts Collection<String>
     */
    void addMapZone(final String bodyZone, final Collection<String> bodyParts);

    /**
     * 
     * @param measureBodyZone String
     * @param measure Double
     */
    void addBodyMeasure(final String measureBodyZone, final Double measure);

    /**
     * 
     * @param numEx Integer
     * @param score Integer
     */
    void addExerciseScore(final Integer numEx, final Integer score);

    /**
     * 
     * @return true if current User has been set
     */
    boolean isLoginUser();

    /**
     * Add a new Account.
     * @param userName String
     * @param password String
     * @param avatar String
     * @throws ExistentAccount 
     */
    void addAccount(final String userName, final String password, final String avatar) throws ExistentAccount;

    /**
     * Add a new User.
     * @param firstName String
     * @param secondName String
     * @param age Integer
     * @param email String
     */
    void addUser(final String firstName, final String secondName, final int age, final String email);

    /**
     * @param description String
     * @param nameTool String
     * @param nameImage String
     * @param num Integer
     * @param valueMin Integer
     * @param valueMax Integer
     */
    void addGymTool(final String description, final String nameTool, final String nameImage, final int num, final int valueMin, final int valueMax);

    /**
     * 
     * @param toolCode String
     * @param bodyPart String
     * @param percentage Double
     */
    void addBodyPart(final String toolCode, final String bodyPart, final Double percentage);

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
     * @param codeRoutine String
     * @return dimension of an exercise list with codeRoutine
     */
    int getNumExercise(final String codeRoutine);

    /**
     * 
     * @return true if exists the current user
     */
    boolean isCurrentUser();

    /**
     * 
     * @return the current user's measure list
     */
    List<BodyData> getMeasureList();

    /**
     * 
     * @return the current user's routine list
     */
    List<Routine> getRoutineList();

    /**
     * 
     * @return the current user's Workout list
     */
    List<Workout> getWorkoutList();

    /**
     * 
     * @return the current user's Workout score list
     */
    List<Double> scoreWorkout();

    /**
     * 
     * @return associations between muscles and relative scores obtained
     */
    Map<String, Double> scoreBodyPart();

    /**
     * 
     * @return associations between parts of body and relative scores obtained
     */
    Map<String, Double> scoreBodyZone();

    /**
     * 
     * @return associations between gymTool names and relative scores obtained
     */
    Map<String, Double> scoreGymTool();

    /**
     * 
     * @return associations between muscles and relative times obtained
     */
    Map<String, Double> timeBodyPart();

    /**
     * 
     * @return associations between parts of body and relative times obtained
     */
    Map<String, Double> timeBodyZone();

    /**
     * 
     * @return associations between gymTool names and relative scores obtained
     */
    Map<String, Double> timeGymTool();

    /**
     * 
     * @return the current User's trending of Body Mass
     */
    List<Double> trendBodyMass();

    /**
     * 
     * @return the current User's trending of BMI
     */
    List<Double> trendBodyBMI();
}
