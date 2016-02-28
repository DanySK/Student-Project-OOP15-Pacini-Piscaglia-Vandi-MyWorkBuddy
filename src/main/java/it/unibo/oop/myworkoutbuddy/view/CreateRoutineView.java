package it.unibo.oop.myworkoutbuddy.view;

import java.util.List;
import java.util.Map;

/**
 *
 * Shows the possible exercises and get the custom routine created by the user.
 *
 */
public interface CreateRoutineView {

    /**
     * Save the names of the exercises of the chosen routine by the user.
     * 
     * @throws NumberFormatException
     *             if user insert a string for repetition.
     * 
     * @return routine map composed by a map <Routine description, Map <Exercise
     *         name, List <Exercise repetitions>>
     */
    Map<String, Map<String, List<Integer>>> getRoutine() throws NumberFormatException;

    /**
     * 
     * @return routine description inserted by user.
     */
    String getRoutineDescription();

    /**
     * 
     * @return routine name inserted by user.
     */
    String getRoutineName();
}
