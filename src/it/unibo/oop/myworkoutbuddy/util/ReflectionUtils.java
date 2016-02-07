package it.unibo.oop.myworkoutbuddy.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class ReflectionUtils {

    public static <T> String getConstructorShape(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.getName().contains("this"))
                .collect(Collectors.toMap(f -> f.getName(), f -> f.getType().getName()))
                .entrySet().stream()
                .map(e -> e.getValue() + " " + e.getKey())
                .collect(Collectors.joining(", ", clazz.getName() + "(", ")"));
    }

    private ReflectionUtils() {
        throw new IllegalAccessError();
    }

}
