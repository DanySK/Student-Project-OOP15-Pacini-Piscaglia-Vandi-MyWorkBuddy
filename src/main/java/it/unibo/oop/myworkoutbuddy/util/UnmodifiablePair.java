package it.unibo.oop.myworkoutbuddy.util;

/**
 * Models an unmodifiable {@link Pair} of two generic objects.
 *
 * @param <X>
 *            the {@code x} value type
 * @param <Y>
 *            the {@code y} value type
 */
public class UnmodifiablePair<X, Y> extends MutablePair<X, Y> {

    /**
     * Constructs a new {@code UmmodifiablePair} from the {@code x} and {@code y} values.
     * 
     * @param x
     *            the {@code x} value
     * @param y
     *            the {@code y} value
     * @throws NullPointerException
     *             if {@code x} or {@code y} is {@code null}
     */
    public UnmodifiablePair(final X x, final Y y) {
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
