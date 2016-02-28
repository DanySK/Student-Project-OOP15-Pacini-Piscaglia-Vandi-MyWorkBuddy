package it.unibo.oop.myworkoutbuddy.view;

import java.util.List;
import java.util.OptionalDouble;

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
     * @return name of the selected routine chosen by user.
     */
    String getSelectedRoutine();

    /**
     * 
     * @return the inserted user weight updated, otherwise an empty optional.
     */
    OptionalDouble getWeight();

}
