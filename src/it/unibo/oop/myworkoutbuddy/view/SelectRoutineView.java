package it.unibo.oop.myworkoutbuddy.view;

import java.util.List;
import java.util.Map;

import it.unibo.oop.myworkoutbuddy.util.Pair;

/**
 * 
 * Main view when user select the routine to do.
 *
 */
public interface SelectRoutineView {

    /**
     * 
     * @return a map exercise name and pair of
     *         list of repetitions executed by user - KG moved.
     */
    Map<String, Pair<List<Integer>, Integer>> getUserResults();

    /**
     * @return index of the saved routines of the user.
     */
    int getRoutineIndex();

}
