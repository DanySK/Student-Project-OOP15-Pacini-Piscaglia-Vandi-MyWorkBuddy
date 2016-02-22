package it.unibo.oop.myworkoutbuddy.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/**
 Class for using a GymTool

     code : alphabetic identifier code
     name : 
     imageFile : optional field for an image
     numTools : number of Tools available in the Gym
     valueMin : minimum value to setting a tool
     valueMax : maximum value to setting a tool
     bodyMap : map<body parts, percentage values> (BodyPart = a single muscle of the body, Percentage value = % of muscle using the tool).
*/
public final class GymToolImpl implements GymTool {

    private String code;
    private String name;
    private Optional<String> imageFile = Optional.empty();
    private int numTools;
    private final int valueMin;
    private final int valueMax;

    private Map<String, Double> bodyMap;

    /**
     * 
     * @param code String
     * @param name String
     * @param imageFile String
     * @param numTools integer
     * @param valueMin integer
     * @param valueMax integer
     */
    public GymToolImpl(final String code, final String name, final String imageFile, final int numTools, final int valueMin, final int valueMax) {
        this.code = code;
        this.name = name;
        this.imageFile = Optional.of(imageFile);
        this.numTools = numTools;
        this.valueMin = valueMin;
        this.valueMax = valueMax;

        this.bodyMap = new HashMap<>(); // map to create with the specific add function
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getNameTool() {
        return this.name;
    }

    @Override
    public String getImageFile() {
        return this.imageFile.orElse("none");
    }

    /**
     * 
     * @return the Max value for that exercise
     */
    @Override
    public int getMinValue() {
        return this.valueMin;
    }

    /**
     * 
     * @return the Max value for that exercise
     */
    @Override
    public int getMaxValue() {
        return this.valueMax;
    }

    @Override
    public int getNumTools() {
        return this.numTools;
    }

    //         gymTool.addBodyPart(bodyPart, percentage);
    /**
     * Add to BodyMap a specific body part with relative value of percentage
     * Example : TapisRoulant = <m1, 20%>, <m2,30%>, <m5,20%>, ...
     * @param bodyPart BodyPart
     * @param value Double
     */
    @Override
    public void addBodyPart(final String bodyPart, final Double percentage) {
        this.bodyMap.put(bodyPart, percentage);
    }

    @Override
    public Map<String, Double> getBodyMap() {
        return this.bodyMap;
    }

    @Override
    public String toString() {
        return "\n\n GymToolImpl" 
                + "\n [code = " + code + ", name = " + name + ", imageFile = " + imageFile 
                + "\n numTools = " + numTools + ", valueMin = " + this.valueMin + ", valueMax = " + this.valueMax
                + "\n bodyMap = " + bodyMap
                + "]";
    }

    /**
     * 
     *
     */
    public static class Builder {
        private String code;
        private String name;
        private String imageFile;
        private int numTools;
        private int valueMin;
        private int valueMax;

        /**
         * 
         * @param code String
         * @return a builder of GymTool
         */
        public Builder code(final String code) {
            this.code = code;
            return this;
        }
        /**
         * 
         * @param name String
         * @return a builder of GymTool
         */
        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        /**
         * 
         * @param imageFile String
         * @return a builder of GymTool
         */
        public Builder imageFile(final String imageFile) {
            this.imageFile = imageFile;
            return this;
        }

        /**
         * 
         * @param numTools integer
         * @return a builder of GymTool
         */
        public Builder numTools(final int numTools) {
            this.numTools = numTools;
            return this;
        }

        /**
         * 
         * @param valueMin integer
         * @return a builder of GymTool
         */
        public Builder valueMin(final int valueMin) {
            this.valueMin = valueMin;
            return this;
        }

        /**
         * 
         * @param valueMax integer
         * @return a builder of GymTool
         */
        public Builder valueMax(final int valueMax) {
            this.valueMax = valueMax;
            return this;
        }

        private void checkNotNull(final Object object) throws NullPointerException {
            if (object == null) {
                throw new NullPointerException();
            }
        }

        /**
         * 
         * @return Builder
         * @throws IllegalStateException exception
         */
        public GymToolImpl build() throws IllegalStateException {
            this.checkNotNull(this.code);
            this.checkNotNull(this.name);
            this.checkNotNull(this.imageFile);

            if (this.numTools < 0) {
                throw new IllegalStateException();
            }

            return new GymToolImpl(this.code, this.name, this.imageFile, this.numTools, this.valueMin, this.valueMax);
        }
    }
}
