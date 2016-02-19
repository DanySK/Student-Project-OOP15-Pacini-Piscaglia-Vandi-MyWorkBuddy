package it.unibo.oop.myworkoutbuddy.model;

import java.util.List;
import java.util.Map;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyData;
import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;
import it.unibo.oop.myworkoutbuddy.model.Body.BodyZone;
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
    List<WorkoutRoutine> getRoutineList();
    /**
     * 
     * @param bodyMeasure of User
     */
    void addMesure(BodyData bodyMeasure);
    /**
     * 
     * @param workout of User
     */
    void addWorkout(Workout workout);

    /**
     * 
     * @param routine of User
     */
    void addRoutine(WorkoutRoutine routine);
    /**
     * Update the Status of User's workout.
     */
    void upDateStatus();

    /**
     * 
     * @return trend of BMI values 
     */
    List<Double> calculateBMI();

    /**
     * 
     * @return list of performanced scores.
     */
    List<Double> performanceScore();

    /**
     * 
     * @return Improved Performance of Body (muscles level).
     */
    Map<BodyPart, Double> performanceBodyPart();

    /**
     * 
     * @return Improved Performance of Body (parts level).
     */
    Map<BodyZone, Double> performanceBodyZone();

    /**
     * 
     * @return work times for each bodyPart
     */
    Map<BodyPart, Double> timeBodyPart();

    /**
     * 
     * @return work times for each bodyZone
     */
    Map<BodyZone, Double> timeBodyZone();

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
}
