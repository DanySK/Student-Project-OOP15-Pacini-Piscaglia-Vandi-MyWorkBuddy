package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * User's data of a single training session.
 */
public interface Routine {

    /**
     * it gives the id of a specific Routine.
     * @return an integer
     */
    int getIdRoutine();

    /**
     * give the data of Routine.
     * @return a LocalDate
     */
    LocalDate getDate();

    /**
     * give the training card of an exercise.
     * @return a Routine
     */
    Workout getWorkout();

    /**
     * give the list of scores of map.
     * @return a List<Integer>
     */
    List<Integer> getValueList();

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
     * give the associations between Exercise and relative score.
     * @return a Map<String, Double>
     */
    Map<Exercise, Integer> getScoreMap();

    /**
     * give the average of normalized scores.
     * @return a Double
     */
    Double getRoutineScore();

    /**
     * add a new list of gymTools setting.
     * @param valueList List<Integer>
     */
    void addValue(final List<Integer> valueList);

    /**
     * return the Workout state.
     * @return a boolean
     */
    boolean isState();
}
