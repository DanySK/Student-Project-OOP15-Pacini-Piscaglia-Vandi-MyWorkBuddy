package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *  it contains body data :
 *  BodyZone    : enum of body zone for mapping;
 *  BodyPart    : enum of body parts/muscles;
 *  BodyMeasure : enum of body parts for measurement data;
 *  BodyTarget  : enum of aims for each single training;
 *
 *  BodyData : class for body parts measurement.
 */
public class Body {

    private Map<BodyPart, BodyZone> bodyMap;  // mapping muscles -> zones
    /**
     * 
     */
    public Body() {
        this.bodyMap = new HashMap<>();
    }

    /**
     * add new mapping muscle -> zone.
     * @param muscle BodyMuscle
     * @param part BodyPart
     */
    public void addMapping(final BodyPart muscle, final BodyZone part) {
        this.bodyMap.put(muscle, part);
    }

    /**
     * 
     * @return the mapping for each zone of human body
     */
    public Map<BodyPart, BodyZone> getMapping() {
        return this.bodyMap;
    }

    /**
     * 
     *
     */
    public enum BodyTarget {
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
        CHEST, BACK, BICEPS, TRICEPS, QUADRICEPS, HAMSTRINGS, CALVES, FOREARMS, SHOULDERS, TRAPS;

        @Override
        public String toString() {
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }

    /**
     * 
     *
     */
    public enum BodyMeasure {
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
        private Map<BodyMeasure, Double> bodyMeasure;  // value measure for each body measure

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
            return 0.00;
        }
        /**
         * 
         * @return measureData
         */
        public Map<BodyMeasure, Double> getBodyMeasure() {
            return this.bodyMeasure;
        }
        /**
         * 
         * @param bodyMeasure BodyMeasure
         * @param measure Double
         */
        public void addBodyMeasure(final BodyMeasure bodyMeasure, final Double measure) {
            this.bodyMeasure.put(bodyMeasure, measure);
        }

        /**
         * 
         * @param data LocalDate
         */
        private void setData(final LocalDate data) {
            this.data = data;
        }
    }
}