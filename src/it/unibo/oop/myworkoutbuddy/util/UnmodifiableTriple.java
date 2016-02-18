package it.unibo.oop.myworkoutbuddy.util;

/**
 * Models an unmodifiable {@link Triple} of three generic objects.
 *
 * @param <X>
 *            the {@code x} value type
 * @param <Y>
 *            the {@code y} value type
 * @param <Z>
 *            the {@code z} value type
 */
public class UnmodifiableTriple<X, Y, Z> extends MutableTriple<X, Y, Z> {

    /**
     * Constructs a new {@code UnmodifiableTriple} from the {@code x}, {@code y} and {@code z} values.
     * 
     * @param x
     *            the {@code x} value
     * @param y
     *            the {@code y} value
     * @param z
     *            the {@code z} value
     * @throws NullPointerException
     *             if {@code x}, {@code y} or {@code z} is {@code null}
     */
    public UnmodifiableTriple(final X x, final Y y, final Z z) {
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
