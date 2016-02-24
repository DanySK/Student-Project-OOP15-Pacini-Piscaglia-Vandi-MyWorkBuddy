package it.unibo.oop.myworkoutbuddy.util;

public interface Triple<X, Y, Z> extends Pair<X, Y> {

    Z getZ();

    void setZ(Z z);

}
