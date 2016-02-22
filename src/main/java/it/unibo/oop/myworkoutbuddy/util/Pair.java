package it.unibo.oop.myworkoutbuddy.util;

/**
 * It models a pair of two generic objects.
 *
 * @param <X>
 *            the {@code x} value type
 * @param <Y>
 *            the {@code y} value type
 */
public interface Pair<X, Y> {

    /**
     * Returns the {@code x} value of this pair.
     * 
     * @return the {@code x} value
     */
    X getX();

    /**
     * Returns the {@code y} value of this pair.
     * 
     * @return the {@code y} value
     */
    Y getY();

    /**
     * Sets the {@code x} value of this pair.
     * 
     * @param x
     *            the new x value
     * @throws NullPointerException
     *             if {@code x} is {@code null}
     */
    void setX(X x);

    /**
     * Sets the {@code y} value of this pair.
     * 
     * @param y
     *            the new y value
     * @throws NullPointerException
     *             if {@code y} is {@code null}
     */
    void setY(Y y);

}
