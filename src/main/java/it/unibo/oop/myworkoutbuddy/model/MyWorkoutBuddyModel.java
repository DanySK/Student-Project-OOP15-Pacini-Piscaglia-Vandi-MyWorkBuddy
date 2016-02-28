package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * principal class of model.
 *
 */
public interface MyWorkoutBuddyModel {
    /**
     * Add a new Account.
     * @param userName String
     * @param password String
     */
    void addAccount(final String userName, final String password);

    /**
     * Add a new User.
     * @param firstName String
     * @param secondName String
     * @param age Integer
     * @param email String
     */
    void addUser(final String firstName, final String secondName, final int age, final String email);

    /**
     * user's login.
     * @param name String
     * @param password String
     */
    void loginUser(final String name, final String password);

    /**
     * give alphabetic name of Current Account.
     * @return a String
     */
    Optional<String> getCurrentUserName();

    /**
     * the current user's logout.
     */
    void logoutUser();

    /**
     * add a new workout for current user.
     * @param code String
     * @param nameWorkout String
     * @param target String
     */
    void addWorkout(final String code, final String nameWorkout, final String target);

    /**
     * 
     * @param nameWorkout String
     * @param target String
     * @param nameTool String
     * @param numSessions List<Integer> sessions number for each repetitions
     */
    void addGymExcercise(final String nameWorkout, final String target, final String nameTool, 
            final List<Integer> numSessions);

    /**
     * 
     * @param nameRoutine String
     * @param localDate LocalDate
     */
    void addRoutine(final String nameRoutine, final LocalDate localDate);

    /**
     * add a new List of setting exercise values.
     * @param valueList List<Integer>
     */
    void addExerciseValue(final List<Integer> valueList);

    /**
     * the default body.
     */
    void resetBody();

    /**
     * add a new bodyPart mapped in specific set of bodyZone.
     * @param bodyPart String
     * @param bodyZone String
     */
    void setBody(final String bodyPart, final String bodyZone);

    /**
     * add a new bodyPart mapped in specific set of current bodyZone.
     * @param bodyPart String
     */
    void setBody(final String bodyPart);

    /**
     * add a new data of measure.
     * @param localDate LocalDate
     */
    void addDataMeasure(final LocalDate localDate);

    /**
     * add a new BodyMeasure for current User.
     * @param measureBodyZone String
     * @param measure Double
     * @param firstTime boolean
     */
    void addBodyMeasure(final String measureBodyZone, final Double measure, final boolean firstTime);

    /**
     * add a new GymTool.
     * @param description String
     * @param nameTool String
     * @param num Integer
     * @param valueMin Integer
     * @param valueMax Integer
     */
    void addGymTool(final String description, final String nameTool, final int num, final int valueMin, final int valueMax);

    /**
     * add a new muscle with relative percentage measure in the tool specified by the toolCode.
     * @param toolName String
     * @param bodyPart String
     * @param percentage Double
     */
    void addBodyPart(final String toolName, final String bodyPart, final Double percentage);

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
     * give the dimension of an exercise list with codeRoutine.
     * @return an Integer
     */
    int getNumExercise();

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
    List<Double> scoreRoutine();

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
    List<Double> trendBodyBMR();

    /**
     * 
     * give the current User's trending of BMI.
     * @return a List<Double>
     */
    List<Double> trendBodyBMI();

    /*
     * calcolo indice massa grassa e magra (opzionale).
     */
}
