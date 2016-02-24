package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * class Data of measurements.
 * -------------------------------------------------------------
 */
public class BodyData {
    private static final double ZERO_DOUBLE = 0.00;
    private static final int ZERO_INTEGER = 0;

    private static final  double FACTOR_BMR = 66.5;
    private static final double FACTOR_WEIGHT = 13.75;
    private static final double FACTOR_HEIGHT = 5.003;
    private static final double FACTOR_AGE = 6.775;

    private static final double METER_TO_CM = 100.00;

    private LocalDate data;     // date of measurement
    private final Map<String, Double> bodyMeasure;  // value measure for each body measure

    /**
     * 
     * @param data LocalDate
     * @throws NullPointerException exception for null values
     */
    public BodyData(final LocalDate data) throws NullPointerException {
        this.setData(data);
        this.bodyMeasure = new HashMap<>();
    }

    /**
     * 
     * @return date of measurement
     */
    public LocalDate getDate() {
        return this.data;
    }

    /**
     * 
     * @return bodyMass a Double
     * @throws NullPointerException exception for null values
     * @throws IllegalArgumentException exception for invalid values
     */
    public Double getBodyMass() throws NullPointerException, IllegalArgumentException {
        final Double mass = getMassHeight("MASS");
        final Double height = getMassHeight("HEIGHT");
        final Double den = height * height;
        if (den <= ZERO_DOUBLE) {
            return ZERO_DOUBLE;
        }

        return (mass / den);
    }

    /**
     * calculation of BMI(Body Mass Index).
     * @param age Integer
     * @return bodyBMI
     */
    public Double getBodyBMI(final Integer age) {
        if (age == null || age <= ZERO_INTEGER) {
            return ZERO_DOUBLE;
        }
        //final UnmodifiablePair<Double, Double> pairValues = getMassHeight();
        final Double mass = getMassHeight("MASS");
        final Double height = getMassHeight("HEIGHT");
        final Double valueTemp = (FACTOR_WEIGHT * mass) + (FACTOR_HEIGHT * METER_TO_CM * height);
        if (valueTemp <= ZERO_DOUBLE) {
            return ZERO_DOUBLE;
        }
        return (FACTOR_BMR + valueTemp - (FACTOR_AGE * age));
    }

    /**
     * give a map of measurable body zone and relatives measurement.
     * @return Map<String, Double>
     */
    public Map<String, Double> getBodyMeasure() {
        return this.bodyMeasure;
    }

    /**
     * add a new measure for the body in map of body measure and relative data.
     * @param bodyMeasure String
     * @param measure Double
     */
    public void addBodyMeasure(final String bodyMeasure, final Double measure) {
        this.bodyMeasure.put(bodyMeasure, measure);
    }

    /**
     * return mass or height according to the measure
     * @param measure String
     * @return a Double
     */
    private Double getMassHeight(final String measure) {
        final Double heightMass = this.getBodyMeasure().get(measure);
        if (heightMass == null || heightMass <= ZERO_DOUBLE) {
            return ZERO_DOUBLE;
        }
        return heightMass;
    }

    /**
     * set the data of measure
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
