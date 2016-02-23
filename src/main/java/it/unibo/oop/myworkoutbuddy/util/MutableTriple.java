package it.unibo.oop.myworkoutbuddy.util;

import java.util.Objects;

public class MutableTriple<X, Y, Z> extends MutablePair<X, Y> implements Triple<X, Y, Z> {

    private Z z;

    public MutableTriple(final X x, final Y y, final Z z) {
        super(x, y);
        this.z = Objects.requireNonNull(z);
    }

    @Override
    public Z getZ() {
        return z;
    }

    @Override
    public void setZ(final Z z) {
        this.z = Objects.requireNonNull(z);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + super.hashCode();
        result = prime * result + Objects.hashCode(z);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Triple)) {
            return false;
        }
        final Triple<?, ?, ?> triple = (Triple<?, ?, ?>) obj;
        return super.equals(obj) && Objects.equals(z, triple.getZ());
    }

    @Override
    public String toString() {
        return String.format("Triple[%s, %s, %s]", getX(), getY(), z);
    }

}
