package it.unibo.oop.myworkoutbuddy.model;

import java.util.Map;
/**
 * GymTool available for training.
 */
public interface GymTool {

    /**
     * Add to BodyMap a specific body part with relative value of percentage
     * Example : TapisRoulant = <m1, 20%>, <m2,30%>, <m5,20%>, ...
     * @param bodyPart BodyPart
     * @param percentage Double
     */
    void addBodyPart(final String bodyPart, Double percentage);

    /**
     * give the code of GymTool.
     * @return a String
     */
    String getCode();

    /**
     * it gives the toolName.
     * @return a String
     */
    String getNameTool();

    /**
     * it gives the path of file.
     * @return a String
     */
    String getImageFile();

    /**
     * it gives the Max value for that exercise.
     * @return an Integer
     */
    int getMaxValue();

    /**
     * it gives the Minimum value for that exercise.
     * @return an Integer
     */
    int getMinValue();

    /**
     * the number Max of available tools in the gym.
     * @return an Integer
     */
    int getNumTools();

    /**
     * it gives the percentage grade of single muscle involvement.
     * @return a Map<String, Double>
     */
    Map<String, Double> getBodyMap();
}
