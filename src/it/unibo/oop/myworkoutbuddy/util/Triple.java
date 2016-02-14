package it.unibo.oop.myworkoutbuddy.util;

/**
 * It models a triple of three generic objects.
 *
 * @param <X>
 *            the {@code x} value type
 * @param <Y>
 *            the {@code y} value type
 * @param <Z>
 *            the {@code z} value type
 */
public interface Triple<X, Y, Z> extends Pair<X, Y> {

    /**
     * Returns the {@code x} value of this triple.
     * 
     * @return the {@code x} value
     */
    X getX();

    /**
     * Returns the {@code y} value of this triple.
     * 
     * @return the {@code y} value
     */
    Y getY();

    /**
     * Returns the {@code z} value of this triple.
     * 
     * @return the {@code z} value
     */
    Z getZ();

    /**
     * Sets the {@code x} value of this triple.
     * 
     * @param x
     *            the new {@code x} value
     * @throws NullPointerException
     *             if {@code x} is {@code null}
     */
    void setX(X x);

    /**
     * Sets the {@code y} value of this triple.
     * 
     * @param y
     *            the new {@code y} value
     * @throws NullPointerException
     *             if {@code y} is {@code null}
     */
    void setY(Y y);

    /**
     * Sets the {@code z} value of this triple.
     * 
     * @param z
     *            the new {@code z} value
     * @throws NullPointerException
     *             if {@code z} is {@code null}
     */
    void setZ(Z z);

}
