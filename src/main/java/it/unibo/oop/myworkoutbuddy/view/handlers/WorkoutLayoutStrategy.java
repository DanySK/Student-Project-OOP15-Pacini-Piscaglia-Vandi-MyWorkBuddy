package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import javafx.scene.Node;

/**
 * The strategy used to build routine GUI and fetch results in
 * SelectRoutineView.
 *
 */
public interface WorkoutLayoutStrategy {

    /**
     * @param workouts
     *            data structure with workouts informations.
     *            It is composed by a map workoutName - exercises contained in a
     *            map exerciseName- list of repetitions.
     * @return a javaFx node to add workout to the scene.
     */
    Node addWorkoutNodes(Map<String, Map<String, List<Integer>>> workouts);

    /**
     * 
     * @param workout
     *            to get the exercise results.
     * @return a pair exercise name, repetitions-kg.
     */
    Pair<String, Pair<List<Integer>, Integer>> getExerciseResults(Node workout);
}
