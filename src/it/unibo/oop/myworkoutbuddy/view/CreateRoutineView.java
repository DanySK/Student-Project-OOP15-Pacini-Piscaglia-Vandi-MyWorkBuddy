package it.unibo.oop.myworkoutbuddy.view;

import java.util.List;
import java.util.Map;

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
     *         composed by <Routine description, List<<Exercise name,
     *         <Repetitions>>>
     */
    Map<String, Map<String, List<Integer>>> getRoutine();

    /**
     * 
     * @return routine
     *         description.
     */
    String getRoutineDescription();
}
