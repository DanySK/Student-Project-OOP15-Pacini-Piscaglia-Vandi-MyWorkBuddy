package it.unibo.oop.myworkoutbuddy.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ReflectionUtils {

    public static <T> String getConstructorShape(Class<T> clazz, Set<String> excludedFields) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(filters(excludedFields))
                .collect(Collectors.toMap(f -> f.getName(), f -> f.getType().getName()))
                .entrySet().stream()
                .map(e -> e.getValue() + " " + e.getKey())
                .collect(Collectors.joining(", ", clazz.getName() + "(", ")"));
    }

    private static Predicate<? super Field> filters(final Set<String> excludedFields) {
        return f -> !Modifier.isStatic(f.getModifiers())
                && !f.getName().contains("this")
                && !excludedFields.contains(f.getName());
    }

    private ReflectionUtils() {
        throw new IllegalAccessError();
    }

}
