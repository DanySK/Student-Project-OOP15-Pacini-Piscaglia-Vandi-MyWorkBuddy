package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
/**
 * 
 * User's data of a single training session. (WorkOut)
 * -------------------------------------------------------------
 */
 
public interface Workout {
    /**
     * 
     * @return data of Workout
     */
    LocalDate getDate();
    /**
     * 
     * @return time of Workout
     */
    LocalTime getTime();
    /**
     * 
     * @return if the exercise is done
     */
    boolean getState();
    /**
     * 
     * @return training card of an exercise
     */
    WorkoutRoutine getCard();

    /**
     * 
     * @param indExercise int
     * @param score Integer
     */
    void addScore(final int indExercise, Integer score);

    /**
     * 
     * @return all the scores got for an exercise
     */
    List<Integer> getScoreList();
}
