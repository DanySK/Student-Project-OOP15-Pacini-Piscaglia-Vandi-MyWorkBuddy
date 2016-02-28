package it.unibo.oop.myworkoutbuddy.model;
import java.util.List;
/**
 * Workout : composed by an exercises list (Exercise list).
 * 
 */
public interface Workout {

    /**
     * give the alphabetic code of a Workout.
     * @return a String
     */
    String getCode();

    /**
     * give the nameCard of a Workout.
     * @return a String
     */
    String getName();

    /**
     * give the scope of Workout.
     * @return a String
     */
    String getTarget();

    /**
     * give the list of gymExercise in a Workout.
     * @return a List<Exercise>
     */
    List<Exercise> getExerciseList();

    /**
     * add a new gymExercise for a workout.
     * @param exercise Exercise
     */
    void addGymExcercise(final Exercise exercise);
}
