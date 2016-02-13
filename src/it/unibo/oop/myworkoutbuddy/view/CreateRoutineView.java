package it.unibo.oop.myworkoutbuddy.view;

import java.util.List;
import java.util.Map;

import it.unibo.oop.myworkoutbuddy.util.Triple;

/**
 *
 * Shows the possible exercises and get the custom routine.
 *
 */
public interface CreateRoutineView {

    /**
     * Save the names of the exercises of the chosen routine by the user.
     * 
     * @return routine map
     *         composed by <Routine index, Routine description, <Workout name,
     *         <Exercise name, <Repetitions>>>
     */
    Triple<Integer, String, Map<String, Map<String, List<Integer>>>> getRoutine();

}
