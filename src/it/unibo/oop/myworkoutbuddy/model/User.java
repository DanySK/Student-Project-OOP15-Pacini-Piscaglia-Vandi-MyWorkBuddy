package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 
 * Informations about User's activities :
 * Account, Data, Measure, Training, TrainingCard.
 * -------------------------------------------------------------
 */
public interface User {
    /**
     * 
     * @return the Account of User
     */
    Account getAccount();
    /**
     * 
     * @return the specific Data of User
     */
    Person getPerson();
    /**
     * 
     * @return the body data for a specific user
     */
    List<BodyData> getMeasureList();
    /**
     * 
     * @return the Workout list for a specific user
     */
    List<Workout> getWorkoutList(); // workoutRoutine
    /**
     * 
     * @return the WorkoutRoutine list for a specific user
     */
    List<Routine> getRoutineList();

    /**
     * 
     * @param localDate LocalDate
     * @param measureBodyZone String
     * @param measure Double
     * @throws NullPointerException an exception for null pointers
     */
    void addMesure(final LocalDate localDate, final String measureBodyZone, final Double measure) throws NullPointerException;

    /**
     * 
     * @param workout of User
     */
    void addWorkout(Workout workout);

    /**
     * 
     * @param routine of User
     */
    void addRoutine(Routine routine);

    /**
     * 
     * @return a list of BMI calculated
     */
    List<Double> trendBodyBMI();

    /**
     * 
     * @return list of performanced scores.
     */
    List<Double> scoreWorkout();

    /**
     * 
     * @return Improved Performance of Body (muscles level).
     */
    Map<String, Double> scoreBodyPart();

    /**
     * 
     * @return Improved Performance of Body (parts level).
     */
    Map<String, Double> scoreBodyZone();

    /**
     * 
     * @return work times for each bodyPart
     */
    Map<String, Double> timeBodyPart();

    /**
     * 
     * @return work times for each bodyZone
     */
    Map<String, Double> timeBodyZone();

    /**
     * 
     * @return a double array of trend values for a human body
     */
    List<Double> trendBodyMass();

    /**
     * 
     * @return a map made of associations between a GymTool code and its relative time of use
     */
    Map<String, Double> timeGymTool();

    /**
     * 
     * @return a map made of associations between a codeTool and its relative increment/decrement of use
     */
    Map<String, Double> scoreGymTool();
}
