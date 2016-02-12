package it.unibo.oop.myworkoutbuddy.util;

/**
 * It models a pair of two generic objects.
 *
 * @param <X>
 * @param <Y>
 */
public interface Pair<X, Y> {

    X getX();

    Y getY();

    void setX(X x);

    void setY(Y y);

}
