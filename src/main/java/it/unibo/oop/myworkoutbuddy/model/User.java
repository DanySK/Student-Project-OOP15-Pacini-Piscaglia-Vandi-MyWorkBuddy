package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 
 * Informations about User's activities :
 * Account, Data, Measure, Training, TrainingCard.
 */
public interface User {

    /**
     * give the Account of User.
     * @return an Account
     */
    Account getAccount();

    /**
     * give the specific Data of User.
     * @return a Person
     */
    Person getPerson();

    /**
     * give the body data for a specific user.
     * @return a List<BodyData>
     */
    List<BodyData> getMeasureList();

    /**
     * give the Workout list for a specific user.
     * @return a List<Workout>
     */
    List<Workout> getWorkoutList();

    /**
     * give the WorkoutRoutine list for a specific user.
     * @return a List<Routine>
     */
    List<Routine> getRoutineList();

    /**
     * add a new measure for the user.
     * @param localDate LocalDate
     * @param measureBodyZone String
     * @param measure Double
     */
    void addMesure(final LocalDate localDate, final String measureBodyZone, final Double measure);

    /**
     * add a new workout for the user.
     * @param workout of User
     */
    void addWorkout(Workout workout);

    /**
     * add a new routine for the user.
     * @param routine of User
     */
    void addRoutine(Routine routine);

    /**
     * remove the routine with the specified idRoutine.
     * @param idRoutine integer
     */
    void removeRoutine(final int idRoutine);

    /**
     * remove the workout with the specified codeWorkout.
     * @param codeWorkout String
     */
    void removeWorkout(final String codeWorkout);

    /**
     * list of BMI calculated.
     * @return a List<Double>
     */
    List<Double> trendBodyBMR();

    /**
     * give a double array of trend values for a human body.
     * @return a List<Double>
     */
    List<Double> trendBodyBMI();

    /**
     * give a double array of trend values for a human body.
     * @return a List<Double>
     */
    List<Double> trendBodyLBM();

    /**
     * list of performance scores.
     * @return a List<Double>
     */
    List<Double> scoreRoutine();

    /**
     * give the improved performance of body (muscles level).
     * @return a Map<String, Double>
     */
    Map<String, Double> scoreBodyPart();

    /**
     * give the improved performance of body (parts level).
     * @return a Map<String, Double>
     */
    Map<String, Double> scoreBodyZone();

    /**
     * give work times for each bodyPart.
     * @return a Map<String, Double>
     */
    Map<String, Double> timeBodyPart();

    /**
     * give the work times for each bodyZone.
     * @return a Map<String, Double>
     */
    Map<String, Double> timeBodyZone();

    /**
     * give the associations between a GymTool code and its relative time of use.
     * @return a Map<String, Double>
     */
    Map<String, Double> timeGymTool();

    /**
     * give the associations between a codeTool and its relative increment/decrement of use.
     * @return a Map<String, Double>
     */
    Map<String, Double> scoreGymTool();
}
