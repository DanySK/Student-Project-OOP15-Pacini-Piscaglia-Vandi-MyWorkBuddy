package it.unibo.oop.myworkoutbuddy.util;

import java.util.Objects;

public class MutablePair<X, Y> implements Pair<X, Y> {

    private X x;
    private Y y;

    public MutablePair(final X x, final Y y) {
        this.x = Objects.requireNonNull(x);
        this.y = Objects.requireNonNull(y);
    }

    @Override
    public X getX() {
        return x;
    }

    @Override
    public Y getY() {
        return y;
    }

    @Override
    public void setX(final X x) {
        this.x = Objects.requireNonNull(x);
    }

    @Override
    public void setY(final Y y) {
        this.y = Objects.requireNonNull(y);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(x);
        result = prime * result + Objects.hashCode(y);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Pair)) {
            return false;
        }
        final Pair<?, ?> pair = (Pair<?, ?>) obj;
        return Objects.equals(x, pair.getX()) && Objects.equals(y, pair.getY());
    }

    @Override
    public String toString() {
        return String.format("Pair[%s, %s]", x, y);
    }

}
