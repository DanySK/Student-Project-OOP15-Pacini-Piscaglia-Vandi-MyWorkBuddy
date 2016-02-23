package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * User's data of a single training session. (WorkOut)
 * -------------------------------------------------------------
 */
public interface Workout {

    /**
     * add a new Score with passed informations to a Workout.
     * @param index Integer
     * @param score Integer
     */
    void addScore(final Integer index, final Integer score);

    /**
     * set the state of a Workout : done -> to do or to do -> done.
     * @param state boolean
     */
    void modifyState(final boolean state);

    /**
     * give the data of Workout.
     * @return a LocalDate
     */
    LocalDate getDate();

    /**
     * give the time of Workout.
     * @return a LocalTime
     */
    LocalTime getTime();

    /**
     * give true if the exercise is done.
     * @return a boolean
     */
    boolean getState();

    /**
     * give the training card of an exercise.
     * @return a Routine
     */
    Routine getRoutine();

    /**
     * give the list of scores of map.
     * @return a List<Integer>
     */
    List<Integer> getScoreList();

    /**
     * give the average of normalized scores.
     * @return a Double
     */
    Double getWorkoutScore();

    /**
     * give the associations between muscles and the relative percentages.
     * @return a Map<String, Double>
     */
    Map<String, Double> getPercentuageParts();

    /**
     * give the associations between muscles and the relative time of training.
     * @return a Map<String, Double>
     */
    Map<String, Double> getTimeParts();

    /**
     * give the associations between codes and relative numbers of time used.
     * @return a Map<String, Double>
     */
    Map<String, Double> getTimeTools();

    /**
     * give the associations between GymTools and relative numbers of score obtained.
     * @return a Map<String, Double>
     */
    Map<String, Double> getScoreTools();

    /**
     * 
     * give the associations between Exercise and relative score.
     * @return a Map<String, Double>
     */
    Map<Exercise, Integer> getScoreMap();
}
