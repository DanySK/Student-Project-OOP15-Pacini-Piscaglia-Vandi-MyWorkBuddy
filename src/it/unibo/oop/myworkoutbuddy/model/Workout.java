package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;

/**
 * 
 * User's data of a single training session. (WorkOut)
 * -------------------------------------------------------------
 */
 
public interface Workout {

    /**
     * 
     * @param state boolean
     */
    void modifyState(final boolean state);
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
    WorkoutRoutine getRoutine();

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
    /**
     * 
     * @return average of normalized scores
     */
    Double getWorkoutScore();

    /**
     * 
     * @return a map of bodyPart and the percentage for all human muscles
     */
    Map<BodyPart, Double> getPercentuageParts();

    /**
     * 
     * @return a map of bodyPart and the time of training for all human muscles
     */
    // Integer a dx di ,
    Map<BodyPart, Double> getTimeParts();

    /**
     * 
     * @return a map of GymTool codes and relatives numbers of time used
     */
    Map<String, Double> getTimeTools();
}
