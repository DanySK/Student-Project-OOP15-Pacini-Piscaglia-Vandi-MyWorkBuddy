package it.unibo.oop.myworkoutbuddy.model;

import java.util.List;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyData;


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
     * @return avatar of User
     */
    String getAvatar();
    /**
     * 
     * @return the specific Data of User
     */
    UserData getUserData();
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
    List<WorkoutRoutine> getWorkoutRoutineList();
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
     * @param workoutRoutine of User
     */
    void addWorkoutRoutine(WorkoutRoutine workoutRoutine);
    /**
     * Update the Status of User's workout.
     */
    void upDateStatus();
    /**
     * Class to access system.
     * -------------------------------------------------------------
     */
    public interface Account {
        /**
         * 
         * @return userName
         */
        String getUserName();

        /**
         * 
         * @return userPassword
         */
        String getPassword();
    }
}
