package it.unibo.oop.myworkoutbuddy.util;

public class UnmodifiableTriple<X, Y, Z> extends MutableTriple<X, Y, Z> {

    public UnmodifiableTriple(X x, Y y, Z z) {
        super(x, y, z);
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
    public void setZ(final Z z) {
        throw new UnsupportedOperationException("setZ");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime + super.hashCode();
    }

}
