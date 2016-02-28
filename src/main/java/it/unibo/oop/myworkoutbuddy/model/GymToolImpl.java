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
    private int numTools;
    private final int valueMin;
    private final int valueMax;

    private Map<String, Double> bodyMap;
    private Optional<String> imageFile = Optional.empty();

    /**
     * 
     * @param code String
     * @param name String
     * @param imageFile String
     * @param numTools integer
     * @param valueMin integer
     * @param valueMax integer
     */
    private GymToolImpl(final String code, final String name, final String imageFile, final int numTools, final int valueMin, final int valueMax) {
        this.code = code;
        this.name = name;
        this.imageFile  = Optional.of(imageFile);
        this.numTools = numTools;
        this.valueMin = valueMin;
        this.valueMax = valueMax;

        this.bodyMap = new HashMap<>(); // map to create with the specific add function
    }

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

    /**
     * return the alphabetic code of a GymTool.
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * return the name of the tool.
     */
    @Override
    public String getNameTool() {
        return this.name;
    }

    /**
     * give the string pattern of tool image.
     */
    @Override
    public String getImageFile() {
        return this.imageFile.orElse("none");
    }

    /**
     * @return the Max value for the tool
     */
    @Override
    public int getNumTools() {
        return this.numTools;
    }

    /**
     * @return the Max value for the tool
     */
    @Override
    public int getMinValue() {
        return this.valueMin;
    }

    /**
     * @return the Max value for the tool
     */
    @Override
    public int getMaxValue() {
        return this.valueMax;
    }

    @Override
    public Map<String, Double> getBodyMap() {
        return this.bodyMap;
    }

    @Override
    public String toString() {
        return "\n\n GymToolImpl" 
                + "\n [code = " + this.getCode() + ", name = " + this.getNameTool() + ", imageFile = " + getImageFile()
                + "\n numTools = " + this.getNumTools() + ", valueMin = " + this.getMinValue() + ", valueMax = " + this.getMaxValue()
                + "\n bodyMap = " + this.getBodyMap()
                + "]";
    }

    /**
     * 
     *
     */
    public static class Builder {
        private String code;
        private String name;
        private int numTools;
        private int valueMin;
        private int valueMax;

        private Optional<String> imageFile = Optional.empty();

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

        /**
         * 
         * @param imageFile String
         * @return a builder of Optional
         */
        public Builder imageFile(final String imageFile) {
            this.imageFile = Optional.ofNullable(imageFile);
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

            if (this.numTools < 0) {
                throw new IllegalStateException();
            }

            return new GymToolImpl(this.code, this.name, this.imageFile.orElse("none"), this.numTools, this.valueMin, this.valueMax);
        }
    }
}
