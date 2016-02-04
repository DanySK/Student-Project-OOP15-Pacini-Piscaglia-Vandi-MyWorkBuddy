package it.unibo.oop.myworkoutbuddy.view;

import java.util.Set;

/**
 *
 * Shows the possible exercises and get the custom routine.
 *
 */
public interface CreateRoutineView {

    /**
     * Save the names of the exercises of the chosen routine by the user.
     * 
     * @return a set of string with exercise names
     */
    Set<String> getRoutine();

}
