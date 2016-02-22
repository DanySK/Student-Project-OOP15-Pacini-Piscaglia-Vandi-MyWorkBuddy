package it.unibo.oop.myworkoutbuddy.model;

import java.util.Map;
/**
 * GymTool available for training.
 * -------------------------------------------------------------
 */
public interface GymTool {

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
     * @param bodyPart String
     * @param percentage Double
     */
    void addBodyPart(final String bodyPart, Double percentage);

    /**
     * 
     * @return percentage grade of single muscle involvement
     */
    Map<String, Double> getBodyMap();
}
