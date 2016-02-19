package it.unibo.oop.myworkoutbuddy.model;

import java.util.Map;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;
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
     * @param bodyPart BodyPart
     * @param value Double
     */
    void addBodyPart(BodyPart bodyPart, Double value);

    /**
     * 
     * @return percentage grade of single muscle involvement
     */
    Map<BodyPart, Double> getBodyMap();
}
