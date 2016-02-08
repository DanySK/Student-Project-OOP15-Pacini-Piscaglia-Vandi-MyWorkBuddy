package it.unibo.oop.myworkoutbuddy.model;

import java.util.List;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyTarget;
/**
 * Training card : composed by an exercises list (Exercise list).
 * -------------------------------------------------------------
 */
 
public interface WorkoutRoutine {
    /**
     * 
     * @param day DayInWeek
     */
    void addDay(final DayInWeek day);
    /**
     * 
     * @param exercise Exercise
     */
    void addGymExcercise(final Exercise exercise);
    /**
     * 
     * @return nameCard
     */
    String getName();
    /**
     * 
     * @return the aim of Training
     */
    BodyTarget getTarget();
    /**
     * 
     * @return list of Day in a week
     */
    List<DayInWeek> getDayAweek();
    /**
     * 
     * @return list of gymExercise in a training
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
