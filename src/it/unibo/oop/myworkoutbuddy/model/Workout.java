package it.unibo.oop.myworkoutbuddy.model;

import java.util.List;

public interface Workout {

    /**
     * 
     * @return
     */
    List<ExerciseSet> getExercises();

    /**
     * 
     * @return
     */
    String getName();

    /**
     * 
     * @author nik
     *
     */
    public static interface Builder {
    }

}
