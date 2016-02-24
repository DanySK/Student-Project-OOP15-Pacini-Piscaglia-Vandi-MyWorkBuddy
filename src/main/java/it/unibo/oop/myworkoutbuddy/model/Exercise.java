package it.unibo.oop.myworkoutbuddy.model;

import java.util.Set;
/**
 * Class for Exercise.
 * -------------------------------------------------------------
 */
public interface Exercise {

    /**
     * give the description of Exercise.
     * @return a String
     */
    String getDescription();

    /**
     * give the GymTool used in a Exercise.
     * @return a GymTool
     */
    GymTool getGymTool();

    /**
     * give the difficulty grade of an Exercise.
     * @return an integer
     */
    int getSettingValue();

    /**
     * give the number of times to repeat.
     * @return an integer
     */
    int getRepetition();

    /**
     * give the duration in minutes.
     * @return an integer
     */
    int getTime();

    /**
     * give the number of Sessions to repeat.
     * @return an integer
     */
    int getNumSession();

    /**
     * give the minutes of pause doing an exercise.
     * @return an integer
     */
    int getPause();

    /**
     * give the set of body parts.
     * @return a Set<String>
     */
    Set<String> getBodyParts();

    /**
     * give the normalized score for an exercise.
     * @param score Double
     * @return a Double
     */
    Double getNormalizedScore(final Double score);
}
