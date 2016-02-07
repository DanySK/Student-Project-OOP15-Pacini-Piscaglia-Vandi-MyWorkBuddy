package it.unibo.oop.myworkoutbuddy.model;

import java.util.HashMap;
import java.util.Map;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;
import javafx.util.Pair;

/**
 * 
 *
 */
public class GymToolImpl implements GymTool {

    private String name;
    private String imageFile;
    private Pair<Integer, Integer> rangeValue;
    private int numMax;
    private Map<BodyPart, Double> bodyMap;

    /**
     * 
     * @param name String
     * @param path String
     * @param max int
     * @param min int
     * @param numMax int
     */
    public GymToolImpl(final String name, final String path, final int max, final int min, final int numMax) {
        this.setString(name);
        this.setImageFile(imageFile);
        this.rangeValue = new Pair<>(max, min);
        this.setNumMax(numMax);
        this.bodyMap = new HashMap<>();
    }

    private void setString(final String name) {
        this.name = name;
    }

    private void setImageFile(final String imageFile) {
        this.imageFile = imageFile;
    }

    private void setNumMax(final int numMax) {
        this.numMax = numMax;
    }
    /**
     * 
     * @param bodyPart BodyPart
     * @param value Double
     */
    public void addBodyPart(final BodyPart bodyPart, final Double value) {
        this.bodyMap.put(bodyPart, value);
    }

    @Override
    public String getNameTool() {
        // TODO Auto-generated method stub
        return this.name;
    }

    @Override
    public String getImageFile() {
        // TODO Auto-generated method stub
        return this.imageFile;
    }

    /**
     * 
     * @return 2 values for a gymTool : maximum difficulty, minimum difficulty
     */
    public Pair<Integer, Integer> getRangeValue() {
        return this.rangeValue;
    }

    @Override
    public int getNumMax() {
        // TODO Auto-generated method stub
        return this.numMax;
    }

    @Override
    public Map<BodyPart, Double> getBodyMap() {
        // TODO Auto-generated method stub
        return this.bodyMap;
    }

}
