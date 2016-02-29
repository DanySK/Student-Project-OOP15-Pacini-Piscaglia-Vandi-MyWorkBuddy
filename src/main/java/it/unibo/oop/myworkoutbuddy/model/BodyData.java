package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * class Data of measurements.
 */
public class BodyData {
    private static final double ZERO_DOUBLE = 0.00;
    private static final int ZERO_INTEGER = 0;

    /*
     * BMR Formula : Mifflin St Jeor : Equation
     * LEAN MASS Formula : James
     */
    private static final double FACTOR_WEIGHT = 10.0;
    private static final double FACTOR_HEIGHT = 6.25;
    private static final double FACTOR_AGE = 5.0;
    private static final double FACTOR_BMR_MALE = 5.00;
    //private static final double FACTOR_BMR_FEMALE = -161;

    private static final double METER_TO_CM = 100.00;

    private LocalDate date;     // date of measurement
    private final Map<String, Double> bodyMeasure;  // value measure for each body measure

    /**
     * 
     * @param localDate LocalDate
     * @throws NullPointerException exception for null values
     */
    public BodyData(final LocalDate localDate) throws NullPointerException {
        this.setData(localDate);
        this.bodyMeasure = new HashMap<>();
    }

    /**
     * give the date of measurement.
     * @return a Date
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * give the bodyMass.
     * @return a Double
     */
    public Double getBodyBMI() {
        final Double mass = getMassHeight("WEIGHT");
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
     * @return a Double
     */
    public Double getBodyBMR(final Integer age) {
        if (age == null || age <= ZERO_INTEGER) {
            return ZERO_DOUBLE;
        }
        final Double mass = getMassHeight("WEIGHT");
        final Double height = getMassHeight("HEIGHT");
        final Double factorBmr = FACTOR_BMR_MALE;
        final Double valueTemp = (FACTOR_WEIGHT * mass) + (FACTOR_HEIGHT * METER_TO_CM * height);
        if (valueTemp <= ZERO_DOUBLE) {
            return ZERO_DOUBLE;
        }

        return (factorBmr + valueTemp - (FACTOR_AGE * age));
    }

    /**
     * calculation of Lean Body Mass.
     * @return a Double
     */
    public Double getBodyLBM() {
        final Double kg = getMassHeight("WEIGHT");
        final Double m = getMassHeight("HEIGHT");

        if (kg == null || m == null || (kg * m) <= ZERO_DOUBLE) {
            return ZERO_DOUBLE;
        }

        final Double hcm = (m * 100);
        // men
        final Double k1 = 1.10;
        final Double k2 = 128.00;

        /* women
        final Double k1 = 1.07;
        final Double k2 = 148.00;
        */

        // formula James : (1.10 * kg) - 128 * ((kg)2 / (100 * h)2)
        final Double value = (k1 * kg) - (k2 * ((kg * kg) / (hcm * hcm)));
        return (value <= ZERO_DOUBLE) ? ZERO_DOUBLE : value;
    }

    /**
     * calculation of Fat Body Mass.
     * @return a Double
     */
    public Double getBodyFMI() {
        final Double kg = getMassHeight("WEIGHT");
        final Double m = getMassHeight("HEIGHT");
        final Double h = (m * 100);

        // men
        final Double k1 = 1.10;
        final Double k2 = 128.00;

        /* women
        final Double k1 = 1.07;
        final Double k2 = 148.00;
        */

        // formula WillMore e Behnke : ( 495 / ( 1.0324 - ( 0.19077 * ( log ( vita - collo ) ) + 0.15456 * ( log( statura ) - 450 ) ) ) )
        final Double value = (k1 * kg) - (k2 * ((kg * kg) / (h * h)));
        if (value <= ZERO_DOUBLE) {
            return ZERO_DOUBLE;
        }
        return value;
    }

    /**
     * give a map of measurable body zone and relatives measurement.
     * @return a Map<String, Double>
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
     * return mass or height according to the measure.
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
    private void setData(final LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "\n BodyData " + "[data = " + this.getDate() + " bodyMeasure = " + this.getBodyMeasure() + "]";
    }
}
