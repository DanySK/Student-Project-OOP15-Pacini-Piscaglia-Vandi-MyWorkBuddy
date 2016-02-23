package it.unibo.oop.myworkoutbuddy.model;

import java.util.Set;
/**
 * 
 *
 */
public interface Exercise {

    /**
     * 
     * @return description of Exercise
     */
    String getDescription();

    /**
     * 
     * @return the GymTool used in a Exercise
     */
    GymTool getGymTool();

    /**
     * 
     * @return the difficulty grade of an Exercise
     */
    int getSettingValue();

    /**
     * 
     * @return the number of times to repeat
     */
    int getRepetition();

    /**
     * 
     * @return the duration in minutes
     */
    int getTime();

    /**
     * 
     * @return the number of Sessions to repeat
     */
    int getNumSession();

    /**
     * 
     * @return the minutes 
     */
    int getPause();

    /**
     * @return the set of body parts
     */
    Set<String> getBodyParts();

    /**
     * 
     * @param score Double
     * @return the normalized score for an exercise
     */
    Double getNormalizedScore(final Double score);
}