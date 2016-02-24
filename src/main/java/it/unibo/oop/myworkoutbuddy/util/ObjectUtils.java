package it.unibo.oop.myworkoutbuddy.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class consists of {@code static} utility methods for operating on objects. These utilities include {@code null}
 * -safe or {@code null}-tolerant methods.
 */
public final class ObjectUtils {

    /**
     * Checks that the specified objects are not null. This method is designed primarily for doing parameter
     * validation in methods and constructors, as demonstrated below:
     * <blockquote>
     * 
     * <pre>
     * public Foo(Bar... bars) {
     *     this.bars = ObjectsUtils.requireNonNulls(bars);
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param objs
     *            the objects to check for nullity
     * @throws NullPointerException
     *             if any object in {@code objs} is {@code null}
     */
    public static void requireNonNulls(final Object... objs) {
        Arrays.stream(objs)
                .filter(Objects::isNull)
                .findAny()
                .ifPresent(o -> {
                    throw new NullPointerException();
                });
    }

    private ObjectUtils() {
    }

}
