package it.unibo.oop.myworkoutbuddy.view;

import java.util.Set;

/**
 *
 * Shows the possible exercises and get the custom routine.
 *
 */
public interface CreateRoutineView {

    /**
     * save the names of the exercises of the chosen routine by the user
     */
    Set<String> getRoutine();
    
}
