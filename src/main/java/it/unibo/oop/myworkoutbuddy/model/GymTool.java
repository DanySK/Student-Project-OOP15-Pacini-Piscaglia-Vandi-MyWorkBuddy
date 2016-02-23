package it.unibo.oop.myworkoutbuddy.model;

import java.util.Map;
/**
 * GymTool available for training.
 * -------------------------------------------------------------
 */
public interface GymTool {

    /**
     * add a percentage value for a human body muscle.
     * @param bodyPart String
     * @param percentage Double
     */
    void addBodyPart(final String bodyPart, Double percentage);

    /**
     * 
     * @return code of GymTool
     */
    String getCode();

    /**
     * 
     * @return toolName
     */
    String getNameTool();

    /**
     * 
     * @return path of file
     */
    String getImageFile();

    /**
     * 
     * @return the Max value for that exercise
     */
    int getMaxValue();

    /**
     * 
     * @return the Max value for that exercise
     */
    int getMinValue();

    /**
     * 
     * @return number Max of available tools in the gym.
     */
    int getNumTools();

    /**
     * 
     * @return percentage grade of single muscle involvement
     */
    Map<String, Double> getBodyMap();
}
