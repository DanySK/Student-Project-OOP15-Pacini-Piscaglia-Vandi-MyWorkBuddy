package it.unibo.oop.myworkoutbuddy.model;

import java.util.List;

public interface WorkoutRoutine {

    /**
     * 
     * @return
     */
    List<Workout> getWorkouts();

    /**
     * 
     * @return
     */
    String getName();

    /**
     * 
     * @return
     */
    String getDescription();

    public static interface Builder {
    }

}
