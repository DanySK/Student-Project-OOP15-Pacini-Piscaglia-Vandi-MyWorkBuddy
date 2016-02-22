package it.unibo.oop.myworkoutbuddy.model;
import java.util.List;

import it.unibo.oop.myworkoutbuddy.model.Body.Target;
/**
 * WorkoutRoutine : composed by an exercises list (Exercise list).
 * -------------------------------------------------------------
 */
public interface WorkoutRoutine {
    /**
     * 
     * @param day DayInWeek
     * 
     * @throws IllegalArgumentException exception for check not empty value
     */
    void addDay(final DayInWeek day);
    /**
     * 
     * @param exercise Exercise
     * 
     * @throws IllegalArgumentException exception for check not empty value
     */
    void addGymExcercise(final Exercise exercise);
    /**
     * 
     * @return nameCard
     */
    String getName();
    /**
     * 
     * @return the scope of Workout
     */
    Target getTarget();
    /**
     * 
     * @return list of Day in a week
     */
    List<DayInWeek> getDayAweek();
    /**
     * 
     * @return list of gymExercise in a Workout
     */
    List<Exercise> getExerciseList();

    /**
     * 
    *
    */
    enum DayInWeek {
       /**
        * day of Week.
        */
       MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    }
}
