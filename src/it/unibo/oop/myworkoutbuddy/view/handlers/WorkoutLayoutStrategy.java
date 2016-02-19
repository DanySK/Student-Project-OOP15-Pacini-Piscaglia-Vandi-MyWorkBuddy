package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.List;
import java.util.Map;

import it.unibo.oop.myworkoutbuddy.util.Pair;
import javafx.scene.Node;

/**
 * 
 *
 */
public interface WorkoutLayoutStrategy {

    /**
     * @param exercises
     *            data structure with workout informations.
     *            It is composed by a map exerciseName- list of repetitions.
     * @return a javaFx node to add workout to the scene.
     */
    Node addWorkoutNode(Map<String, List<Integer>> exercises);

    /**
     * 
     * @param workout
     *            to get the exercise results.
     * @return a pair exercise name, repetitions-kg.
     */
    Pair<String, Pair<List<Integer>, Integer>> getExerciseResults(Node workout);
}
