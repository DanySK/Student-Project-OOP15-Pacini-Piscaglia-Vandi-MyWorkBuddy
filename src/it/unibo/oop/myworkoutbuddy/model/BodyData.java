package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.util.UnmodifiablePair;

/**
 * 
 *
 */
public class BodyData {
    private static final int ZERO_VALUE = 0;

    private static final  double FACTOR_BMR = 66.5;
    private static final double FACTOR_WEIGHT = 13.75;
    private static final double FACTOR_HEIGHT = 5.003;
    private static final double FACTOR_AGE = 6.775;

    private static final double METER_TO_CM = 100.00;

    private LocalDate data;     // date of measurement
    private Map<String, Double> bodyMeasure;  // value measure for each body measure

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
        final UnmodifiablePair<Double, Double> pairValues = getMassHeight();
        final Double den = pairValues.getY() * pairValues.getY();
        if (den <= 0.00) {
            return (double) ZERO_VALUE;
        }
        return (pairValues.getX() / den);
    }

    /**
     * 
     * @param age Integer
     * @return bodyBMI
     * @throws NullPointerException exception for null values
     * @throws IllegalArgumentException exception for invalid values
     */
    public Double getBodyBMI(final Integer age) throws NullPointerException, IllegalArgumentException {
        final Optional<Integer> valueOpt = (age == null || age <= ZERO_VALUE) ? Optional.of(ZERO_VALUE) : Optional.of(age);
        if (valueOpt.get() <= ZERO_VALUE) {
            return (double) ZERO_VALUE;
        }
        final UnmodifiablePair<Double, Double> pairValues = getMassHeight();

        final Double valueTemp = (FACTOR_WEIGHT * pairValues.getX()) + (FACTOR_HEIGHT * METER_TO_CM * pairValues.getY());
        if (valueTemp <= ZERO_VALUE) {
            return (double) ZERO_VALUE;
        }
        return (FACTOR_BMR + valueTemp - (FACTOR_AGE * age));
    }

    /**
     * 
     * @return a map of measurable body zone and relatives measurement
     */
    public Map<String, Double> getBodyMeasure() {
        return this.bodyMeasure;
    }

    /**
     * 
     * @param bodyMeasure String
     * @param measure Double
     */
    public void addBodyMeasure(final String bodyMeasure, final Double measure) {
        this.bodyMeasure.put(bodyMeasure, measure);
    }

    private UnmodifiablePair<Double, Double> getMassHeight() {
        final Double height = this.getBodyMeasure().get("HEIGHT");
        final Double mass = this.getBodyMeasure().get("WEIGHT");
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

    private void setData(final LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "\n BodyData " + "[data = " + this.getDate() + " bodyMeasure = " + this.getBodyMeasure() + "]";
    }
}
