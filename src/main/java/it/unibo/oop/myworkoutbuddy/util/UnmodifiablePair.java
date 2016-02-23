package it.unibo.oop.myworkoutbuddy.util;

public class UnmodifiablePair<X, Y> extends MutablePair<X, Y> {

    public UnmodifiablePair(X x, Y y) {
        super(x, y);
    }

    @Override
    public void setX(final X x) {
        throw new UnsupportedOperationException("setX");
    }

    @Override
    public void setY(final Y y) {
        throw new UnsupportedOperationException("setX");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime + super.hashCode();
    }

}
