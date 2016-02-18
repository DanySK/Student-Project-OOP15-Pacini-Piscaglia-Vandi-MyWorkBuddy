package it.unibo.oop.myworkoutbuddy.util;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Models a mutable {@link Pair} of two generic objects.
 *
 * @param <X>
 *            the {@code x} value type
 * @param <Y>
 *            the {@code y} value type
 */
public class MutablePair<X, Y> implements Pair<X, Y> {

    private X x;
    private Y y;

    /**
     * Constructs a new {@code MutablePair} from the {@code x} and {@code y} values.
     * 
     * @param x
     *            the {@code x} value
     * @param y
     *            the {@code y} value
     * @throws NullPointerException
     *             if {@code x} or {@code y} is {@code null}
     */
    public MutablePair(final X x, final Y y) {
        this.x = requireNonNull(x);
        this.y = requireNonNull(y);
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
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(x, other.getX()) && Objects.equals(y, other.getY());
    }

    @Override
    public String toString() {
        return String.format("Pair[%s, %s]", x, y);
    }

}
