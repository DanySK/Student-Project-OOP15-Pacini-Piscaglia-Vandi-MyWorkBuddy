package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
/**
 * 
 * User's data of a single training session. (WorkOut)
 * -------------------------------------------------------------
 */
 
public interface Workout {
    /**
     * 
     * @return data of Training
     */
    LocalDate getData();
    /**
     * 
     * @return time of Training
     */
    LocalTime getTime();
    /**
     * 
     * @return if the exercise is done
     */
    boolean getState();
    /**
     * 
     * @return training card of an exercise
     */
    TrainingCard getCard();
    /**
     * 
     * @return all the scores got for an exercise
     */
    List<Integer> getScoreList();
}
