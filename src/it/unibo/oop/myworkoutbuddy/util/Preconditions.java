package it.unibo.oop.myworkoutbuddy.util;

import java.util.function.Supplier;

public final class Preconditions {

    public static void checkArgument(boolean expression) {
        checkArgument(expression, "");
    }

    public static void checkArgument(boolean expression, String message, Object... args) {
        checkExpression(expression, () -> new IllegalArgumentException(String.format(message, args)));
    }

    public static void checkState(boolean expression) {
        checkState(expression, "");
    }

    public static void checkState(boolean expression, String message, Object... args) {
        checkExpression(expression, () -> new IllegalStateException(String.format(message, args)));
    }

    private static <X extends Throwable> void checkExpression(boolean expression, Supplier<X> exceptionSupplier)
            throws X {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

    private Preconditions() {
        throw new IllegalAccessError("No instances of " + getClass().getName());
    }

}
