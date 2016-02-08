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

    private String code;
    private String name;
    private String imageFile;
    private Pair<Integer, Integer> rangeValue;
    private int numTools;
    private Map<BodyPart, Double> bodyMap;

    /**
     * @param code String
     * @param name String
     * @param path String
     * @param max int
     * @param min int
     * @param num int
     */
    public GymToolImpl(final String code, final String name, final String path, final int max, final int min, final int num) {
        this.setCode(code);
        this.setString(name);
        this.setImageFile(imageFile);
        this.rangeValue = new Pair<>(max, min);
        this.setNumTools(num);
        this.bodyMap = new HashMap<>();
    }

    private void setCode(final String code) {
        this.code = code;
    }

    private void setString(final String name) {
        this.name = name;
    }

    private void setImageFile(final String imageFile) {
        this.imageFile = imageFile;
    }

    private void setNumTools(final int num) {
        this.numTools = num;
    }

    @Override
    public String getCode() {
        return this.code;
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
    public int getNumTools() {
        // TODO Auto-generated method stub
        return this.numTools;
    }

    /**
     * 
     * @param bodyPart BodyPart
     * @param value Double
     */
    @Override
    public void addBodyPart(final BodyPart bodyPart, final Double value) {
        this.bodyMap.put(bodyPart, value);
    }

    @Override
    public Map<BodyPart, Double> getBodyMap() {
        // TODO Auto-generated method stub
        return this.bodyMap;
    }
}
