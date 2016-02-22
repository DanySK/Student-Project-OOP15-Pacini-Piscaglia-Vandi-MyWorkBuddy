package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import it.unibo.oop.myworkoutbuddy.util.UnmodifiablePair;
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
        private static final int ZERO_VALUE = 0;
        /*
         * factors for BMI calculation
         */
        private static final  double FACTOR_BMR = 66.5;
        private static final double FACTOR_WEIGHT = 13.75;
        private static final double FACTOR_HEIGHT = 5.003;
        private static final double FACTOR_AGE = 6.775;

        private static final double METER_TO_CM = 100.00;

        private LocalDate data;     // date of measurement
        private Map<Measure, Double> bodyMeasure;  // value measure for each body measure

        /**
         * @throws NullPointerException an exception for null value
         * @param data LocalDate
         */
        public BodyData(final LocalDate data) throws NullPointerException {
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
         * @return BodyMass value.
         * @throws NullPointerException exception for not null values
         * @throws IllegalArgumentException exception for not negative values
         */
        public Double getBodyMass() throws NullPointerException, IllegalArgumentException {
            final UnmodifiablePair<Double, Double> pairValues = getMassHeight();

            return (pairValues.getX() / (pairValues.getY() * pairValues.getY()));
        }

        /**
         * 
         * @param age Integer
         * @return BMI value 
         * @throws NullPointerException exception for not null values
         * @throws IllegalArgumentException exception for not negative values
         */
        public Double getBodyBMI(final Integer age) throws NullPointerException, IllegalArgumentException {
            final Optional<Integer> valueOpt = (age == null || age <= ZERO_VALUE) ? Optional.of(ZERO_VALUE) : Optional.of(age);
            if (valueOpt.get() <= ZERO_VALUE) {
                return (double) ZERO_VALUE;
            }
            final UnmodifiablePair<Double, Double> pairValues = getMassHeight();

            return (FACTOR_BMR + (FACTOR_WEIGHT * pairValues.getX()) + (FACTOR_HEIGHT * METER_TO_CM * pairValues.getY()) - (FACTOR_AGE * age));
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

        private UnmodifiablePair<Double, Double> getMassHeight() {
            final Double height = this.getBodyMeasure().get(Body.Measure.HEIGHT);
            final Double mass = this.getBodyMeasure().get(Body.Measure.WEIGHT);
            final Double zeroValue = (double) ZERO_VALUE;
            if (height == null || mass == null) {
                System.out.println("BodyBMIException = " + new NullPointerException());
                return new UnmodifiablePair<>(zeroValue, zeroValue);
            }
            if (height <= 0 || mass <= 0) {
                System.out.println("BodyBMI = " + new IllegalArgumentException());
                return new UnmodifiablePair<>(zeroValue, zeroValue);
            }

            return new UnmodifiablePair<>(mass, height);
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