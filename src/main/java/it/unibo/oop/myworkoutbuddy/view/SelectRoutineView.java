package it.unibo.oop.myworkoutbuddy.view;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

/**
 * 
 * Main view when user select the routine to do.
 *
 */
public interface SelectRoutineView {

    /**
     * 
     * @return a map exercise name and pair of list of repetitions executed by
     *         user - KG moved.
     */
    List<Pair<String, Pair<List<Integer>, Integer>>> getUserResults();

    /**
     * @return index of the select routine chosen by user.
     */
    int getRoutineIndex();

}
