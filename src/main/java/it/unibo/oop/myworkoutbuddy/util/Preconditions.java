package it.unibo.oop.myworkoutbuddy.util;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;

/**
 * Static convenience methods that help a method or constructor check whether it was invoked correctly (whether its
 * <i>preconditions</i> have been met). These methods generally accept a {@code boolean} expression which is expected to
 * be {@code true} (or in the case of {@code checkNotNaN}, an number reference which is expected to be a number). When
 * {@code false} ){@code null} or {@code NaN}) is passed instead, the {@code Preconditions} method throws an unchecked
 * exception, which helps the calling method communicate to <i>its</i> caller that <i>that</i> caller has made a
 * mistake.
 */
public final class Preconditions {

    /**
     * Checks if the result of the boolean expression, if the result is {@code false} then throws an
     * {@link IllegalArgumentException} without details.
     * 
     * @param expression
     *            the expression to check for validity
     * @throws IllegalArgumentException
     *             if the result is of the {@code expression} is {@code false}
     */
    public static void checkArgument(final boolean expression) {
        checkArgument(expression, "");
    }

    /**
     * Checks if the result of the boolean expression, if the result is {@code false} then throws an
     * {@link IllegalArgumentException} with a detail message.
     * 
     * @param expression
     *            the expression to check for validity
     * @param message
     *            the detail message
     * @param args
     *            the arguments to the {@code message}
     * @throws IllegalArgumentException
     *             if the result is of the {@code expression} is {@code false}
     */
    public static void checkArgument(final boolean expression, final String message, final Object... args) {
        checkExpression(
                expression,
                () -> new IllegalArgumentException(String.format(message, args)));
    }

    /**
     * Checks if the result of the boolean expression, if the result is {@code false} then throws an
     * {@link IllegalStateException} without details.
     * 
     * @param expression
     *            the expression to check for validity
     * @throws IllegalArgumentException
     *             if the result is of the {@code expression} is {@code false}
     */
    public static void checkState(final boolean expression) {
        checkState(expression, "");
    }

    /**
     * Checks if the result of the boolean expression, if the result is {@code false} then throws an
     * {@link IllegalArgumentException} with a detail message.
     * 
     * @param expression
     *            the expression to check for validity
     * @param message
     *            the detail message
     * @param args
     *            the arguments to the {@code message}
     * @throws IllegalStateException
     *             if the result is of the {@code expression} is {@code false}
     */
    public static void checkState(final boolean expression, final String message, final Object... args) {
        checkExpression(
                expression,
                () -> new IllegalStateException(String.format(message, args)));
    }

    /**
     * Checks if the given number is not a number, if it is then throws an {@link ArithmeticException} with a detail
     * message saying: "{@code n} is not a number".
     * 
     * @param n
     *            the number to check
     * @throws NullPointerException
     *             if {@code n} is {@code null}
     * @throws ArithmeticException
     *             if {@code n} is {@code NaN}
     */
    public static void checkNonNaN(final Number n) {
        checkExpression(
                !Double.isNaN(n.doubleValue()),
                () -> new ArithmeticException(n + " is not a number"));
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
     *             if {@code value} and {@code defaultValue} are {@code null}
     */
    public static <T> T checkValue(final T value, final T defaultValue) {
        return isNull(value)
                ? requireNonNull(defaultValue)
                : value;
    }

    /**
     * Checks if {@code value} is {@code null}. If it is returns the value in the {@code defaultSupplier}, otherwise
     * returns the {@code value}.
     * If {@code value} is {@code null} and the {@code defaultSupplier}, or the supplied value, is {@code null} a
     * {@link NullPointerException} will be thrown.
     * 
     * @param value
     *            the value to check if is not {@code null}
     * @param defaultSupplier
     *            the supplier of the default value if {@code value} is {@code null}
     * @return the {@code value} if is not {@code null}, otherwise the {@code defaultValue}
     * @param <T>
     *            the type of the value
     * @throws NullPointerException
     *             if the {@code defaultSupplier} or the supplied value is {@code null}
     */
    public static <T> T checkValue(final T value, final Supplier<T> defaultSupplier) {
        return isNull(value)
                ? requireNonNull(requireNonNull(defaultSupplier).get())
                : value;
    }

    /**
     * Throws the supplied exception if the result of the boolean expression is {@code false}.
     * 
     * @param expression
     *            the expression to check
     * @param throwableSupplier
     *            the supplier of the exception to throw
     * @throws X
     *             if the result is of the {@code expression} is {@code false}
     * @throws NullPointerException
     *             if {@code throwableSupplier} is {@code null}
     */
    private static <X extends Throwable> void checkExpression(final boolean expression,
            final Supplier<X> throwableSupplier)
                    throws X {
        if (!expression) {
            throw requireNonNull(throwableSupplier).get();
        }
    }

    private Preconditions() {
        throw new IllegalAccessError("No instances of " + getClass().getName());
    }

}
