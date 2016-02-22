package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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
    Routine getRoutine();

    /**
     * 
     * @param index Integer
     * @param score Integer
     * 
     * @throws NullPointerException exception for check about notNullValue
     * @throws IllegalArgumentException for check about notNegativeValue
     */
    void addScore(final Integer index, final Integer score) throws NullPointerException, IllegalArgumentException;

    /**
     * 
     * @return a map of Exercise and relative score
     */
    Map<Exercise, Integer> getScoreMap();

    /**
     * 
     * @return list of scores of map
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
    Map<String, Double> getPercentuageParts();

    /**
     * 
     * @return a map of bodyPart and the time of training for all human muscles
     */
    Map<String, Double> getTimeParts();

    /**
     * 
     * @return a map of GymTool codes and relatives numbers of time used
     */
    Map<String, Double> getTimeTools();

    /**
     * 
     * @return a map of GymTool codes and relatives numbers of score obtained
     */
    Map<String, Double> getScoreTools();

    /**
     * 
     * @param methodName String
     * @return the return type of available service
     */
    Map<String, Double> getService(final String methodName);
}
