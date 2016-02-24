package it.unibo.oop.myworkoutbuddy.model;
import java.util.List;
/**
 * WorkoutRoutine : composed by an exercises list (Exercise list).
 * -------------------------------------------------------------
 */
public interface Routine {

    /**
     * give the alphabetic code of a Routine.
     * @return a String
     */
    String getCode();

    /**
     * give the nameCard of a Routine.
     * @return a String
     */
    String getName();

    /**
     * give the scope of Workout.
     * @return a String
     */
    String getTarget();

    /**
     * give the list of Day in a week.
     * @return a List<DayInWeek>
     */
    List<DayInWeek> getDayAweek();

    /**
     * give the list of gymExercise in a Workout.
     * @return a List<Exercise>
     */
    List<Exercise> getExerciseList();

    /**
     * add a week day for doing a workout.
     * @param day DayInWeek
     * @throws IllegalArgumentException exception for check not empty value
     */
    void addDay(final DayInWeek day);

    /**
     * add a new gymExercise for a workout.
     * @param exercise Exercise
     * @throws IllegalArgumentException exception for check not empty value
     */
    void addGymExcercise(final Exercise exercise);

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
