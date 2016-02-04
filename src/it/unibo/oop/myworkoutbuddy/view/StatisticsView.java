package it.unibo.oop.myworkoutbuddy.view;

/**
 * 
 * Provides methods to show indexes and other statistics to user.
 *
 */
public interface StatisticsView {

    /**
     * 
     * @return user BMI
     */
    double getBMI();

    /**
     * 
     * @return user height
     */
    int getHeight();

    /**
     * 
     * @return user weight
     */
    int getWeight();

}
