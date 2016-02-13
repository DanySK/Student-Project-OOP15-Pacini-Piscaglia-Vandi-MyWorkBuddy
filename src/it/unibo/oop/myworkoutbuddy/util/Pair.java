package it.unibo.oop.myworkoutbuddy.util;

/**
 * It models a pair of two generic objects.
 *
 * @param <X>
 * @param <Y>
 */
public interface Pair<X, Y> {

    /**
     * 
     * @return X value.
     */
    X getX();

    /**
     * 
     * @return Y value.
     */
    Y getY();

    /**
     * 
     * @param x
     *            value.
     */
    void setX(X x);

    /**
     * 
     * @param y
     *            value.
     */
    void setY(Y y);

}
