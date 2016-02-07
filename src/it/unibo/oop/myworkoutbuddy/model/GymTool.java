package it.unibo.oop.myworkoutbuddy.model;

import java.util.Map;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;
import javafx.util.Pair;;

/**
 * GymTool available for training.
 * -------------------------------------------------------------
 */
public interface GymTool {
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
     * @return the maximum and the minimum levels of difficulty
     */
    Pair<Integer, Integer> getRangeValue();

    /**
     * 
     * @return number Max of available tools in the gym.
     */
    int getNumMax();
    /**
     * 
     * @return percentage grade of single muscle involvement
     */
    Map<BodyPart, Double> getBodyMap();
}
