package it.unibo.oop.myworkoutbuddy.model;

import java.util.Set;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;

/**
 * Single exercise for a TrainingCard. 
 * -------------------------------------------------------------
 */
public interface Exercise {

    /**
     * 
     * @return the description of Exercise
     */
    String getDescription();
    /**
     * 
     * @return the GymTool used in that GymExercise
     */
    GymTool getGymTool();
    /**
     * 
     * @return the difficulty grade of a single GymExercise
     */
    int getSettingValue();
    /**
     * 
     * @return time of repetition x a single exercise
     */
    int getRepetition();
    /**
     * 
     * @return the duration of a single exercise
     */
    int getTime();
    /**
     * 
     * @return the number of Session
     */
    int getNumSession();
    /**
     * 
     * @return time of Pause
     */
    int getPause();

    /**
     * 
     * @return the Set of BodyParts
     */
    Set<BodyPart> getBodyParts();
}