package it.unibo.oop.myworkoutbuddy.util;

import java.util.Arrays;
import java.util.Objects;

import com.google.common.base.Supplier;

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
    public static void requireNonNulls(Object... objs) {
        Arrays.stream(objs)
                .filter(Objects::isNull)
                .findAny()
                .ifPresent(o -> {
                    throw new NullPointerException();
                });
    }

    /**
     * Checks if {@code value} is {@code null}. If it is returns the {@code defaultValue}, otherwise returns the
     * {@code value}.
     * If {@code value} and {@code defaultValue} are {@code null} a {@link NullPointerException} will be thrown.
     * 
     * @param value
     *            the value to check if is not {@code null}
     * @param defaultValue
     *            the value to return if {@code value} is {@code null}
     * @return the {@code value} if is not {@code null}, otherwise the {@code defaultValue}
     * @param <T>
     *            the type of the value
     * @throws NullPointerException
     *             if the {@code value} and the {@code defaultValue} are {@code null}
     */
    public static <T> T requireNonNull(T value, T defaultValue) {
        return Objects.isNull(value) ? Objects.requireNonNull(defaultValue) : value;
    }

    /**
     * Checks if {@code value} is {@code null}. If it is returns the value in the {@code defaultValueSupplier},
     * otherwise returns the {@code value}.
     * If {@code value} is {@code null} and the {@code defaultValueSupplier} or the supplied value, is {@code null} a
     * {@link NullPointerException} will be thrown.
     * 
     * @param value
     *            the value to check if is not {@code null}
     * @param defaultValueSupplier
     *            the supplier of the default value if {@code value} is {@code null}
     * @return the {@code value} if is not {@code null}, otherwise the value supplied by the
     *         {@code defaultValueSupplier}
     * @param <T>
     *            the type of the value
     * @throws NullPointerException
     *             if the {@code value}, the {@code defaultValueSupplier} or the supplied value is {@code null}
     */
    public static <T> T requireNonNull(T value, Supplier<? extends T> defaultValueSupplier) {
        return requireNonNull(value, defaultValueSupplier.get());
    }

    private ObjectUtils() {
    }

}
