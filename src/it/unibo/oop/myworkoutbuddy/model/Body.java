package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 Class Body to manage human body

     Body contains body data :
     BodyZone    : enum of body zone for mapping;
     BodyPart    : enum of body parts/muscles;
     BodyMeasure : enum of body parts for measurement data;
     BodyTarget  : enum of aims for each single training;
     BodyData : class for body parts measurement.
*/
public final class Body {

    private static Map<BodyZone, Set<String>> bodyMap;  // mapping muscles -> zones

    /**
     * 
     * @return a new Body
     */
    public static Body build() {
        return new Body();
    }

    private Body() {
    }

    private static void initBodyMap() {
        bodyMap = new HashMap<>();

        for (BodyPart muscle : BodyPart.values()) {
            final Set<String> setBodyPart = new HashSet<>();
            setBodyPart.add(muscle.name);
            bodyMap.merge(muscle.bodyZone, setBodyPart, (sS1, sS2) -> { 
                setBodyPart.addAll(bodyMap.get(muscle.bodyZone)); 
                return setBodyPart; 
            });
        }
    }
    /**
     * 
     * @param part BodyPart
     * @return the key for muscle
     */
    public static BodyZone getBodyZone(final String part) {
        if (bodyMap == null) {
            initBodyMap();
        }

        return bodyMap.keySet().stream().filter(i -> bodyMap.get(i).contains(part)).findAny().get();
    }

    /**
     * 
     *
     */
    public enum Target {
        /**
         * the aim of a single exercise.
         */
        SLIMMING, TONING, BODY_BUILDING, SPORTING;

        @Override
        public String toString() {
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }

    /**
     * 
     *
     */
    public enum BodyZone {
        /**
         * zone of human body.
         */
        CHEST, BACK, ARM, LEG;

        @Override
        public String toString() {
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }
    /**
     * 
     *
     */
    public enum BodyPart {
        /**
         * 
         */
        PECTORALIS_MAJOR("Pectoralis_Major", BodyZone.CHEST),
        /**
         * 
         */
        PECTORALIS_MINOR("Pectoralis_Minor", BodyZone.CHEST),
        /**
         * 
         */
        SHOULDERS("Shoulder", BodyZone.CHEST),

        /**
         * 
         */
        BICEPS("Biceps", BodyZone.ARM),
        /**
         * 
         */
        TRICEPS("Triceps", BodyZone.ARM),
        /**
         * 
         */
        FOREARMS("ForeArms", BodyZone.ARM),

        /**
         * 
         */
        TRAPEZIUS("Trapezius", BodyZone.BACK),
        /**
         * 
         */
        LOWER_BACK("Lower_Back", BodyZone.BACK),
        /**
         * 
         */
        QUADRICEPS("Quadriceps", BodyZone.LEG),

        /**
         * 
         */
        HAMSTRINGS("Hamstrings", BodyZone.LEG),
        /**
         * 
         */
        CALVES("Calves", BodyZone.LEG);

        private final String name;
        private final BodyZone bodyZone;

        BodyPart(final String name, final BodyZone bodyZone) {
            this.name = name;
            this.bodyZone = bodyZone;
        }

        /**
         * 
         * @return the name of BodyPart
         */
        public String getName() {
            return this.name;
        }

        /**
         * 
         * @return the BodyZone of BodyPart
         */
        public BodyZone getBodyZone() {
            return this.bodyZone;
        }

        /**
         * 
         * @return toString of a BodyPart
         */
        @Override
        public String toString() {
            return "" + this.getName();
        }
    }

    /**
     * 
     *
     */
    public enum Measure {
        /**
         * parts of human body.
         */
        HEIGHT, WEIGHT, UPPER_BODY, LOWER_BODY, FLANK, UPPER_ARM, LOWER_ARM, UPPER_LEG, LOWER_LEG;

        @Override
        public String toString() {
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }

    /**
     * 
     *
     */
    public class BodyData {
        private LocalDate data;     // date of measurement
        private Map<Measure, Double> bodyMeasure;  // value measure for each body measure

        /**
         * 
         * @param data LocalDate
         */
        public BodyData(final LocalDate data) {
            this.setData(data);
            this.bodyMeasure = new HashMap<>();
        }

        /**
         * 
         * @return data of Measurement.
         */
        public LocalDate getDate() {
            return this.data;
        }

        /**
         * 
         * @return bodyMass of a single person
         */
        public Double getBodyMass() {
            // to calculate second BodyMeasure values

            final Double mass, height;

            mass = this.bodyMeasure.get(Body.Measure.WEIGHT);
            height = this.bodyMeasure.get(Body.Measure.HEIGHT);

            return (mass / (height * height));
        }
        /**
         * 
         * @return measureData
         */
        public Map<Body.Measure, Double> getBodyMeasure() {
            return this.bodyMeasure;
        }
        /**
         * 
         * @param bodyMeasure BodyMeasure
         * @param measure Double
         */
        public void addBodyMeasure(final Body.Measure bodyMeasure, final Double measure) {
            this.bodyMeasure.put(bodyMeasure, measure);
        }

        /**
         * 
         * @param data LocalDate
         */
        private void setData(final LocalDate data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "\n BodyData " + "[data = " + this.getDate() + " bodyMeasure = " + this.getBodyMeasure() + "]";
        }
    }
}