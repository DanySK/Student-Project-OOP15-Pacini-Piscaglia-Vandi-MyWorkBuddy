package it.unibo.oop.myworkoutbuddy.util;

/**
 * It models a triple of generic objects.
 *
 * @param <X>
 * @param <Y>
 * @param <Z>
 */
public interface Triple<X, Y, Z> extends Pair<X, Y> {

    /**
     * 
     * @return Z value.
     */
    Z getZ();

    /**
     * 
     * @param z value.
     */
    void setZ(Z z);

}
