package it.unibo.oop.myworkoutbuddy.view;

import java.util.List;
import java.util.Map;

/**
 * 
 * Main view when user select the routine to do.
 *
 */
public interface SelectRoutineView {

    /**
     * 
     * @return a map exercise name
     *         list of repetitions executed by user.
     */
    Map<String, List<Integer>> getUserResults();

    /**
     * @return index of the saved routines of the user.
     */
    int getRoutineIndex();

}
