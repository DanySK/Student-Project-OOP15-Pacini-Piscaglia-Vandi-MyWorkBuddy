package it.unibo.oop.myworkoutbuddy.model;
import java.util.List;
/**
 * WorkoutRoutine : composed by an exercises list (Exercise list).
 * -------------------------------------------------------------
 */
public interface Routine {

    /**
     * add a week day for doing a workout.
     * @param day DayInWeek
     * 
     * @throws IllegalArgumentException exception for check not empty value
     */
    void addDay(final DayInWeek day);

    /**
     * add a new gymExercise for a workout.
     * @param exercise Exercise
     * 
     * @throws IllegalArgumentException exception for check not empty value
     */
    void addGymExcercise(final Exercise exercise);

    /**
     * 
     * @return alphabetic code
     */
    String getCode();

    /**
     * 
     * @return nameCard
     */
    String getName();

    /**
     * 
     * @return the scope of Workout
     */
    String getTarget();

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
     * enum of week day.
     *
     */
    enum DayInWeek {
       /**
        * day of Week.
        */
       MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    }
}
