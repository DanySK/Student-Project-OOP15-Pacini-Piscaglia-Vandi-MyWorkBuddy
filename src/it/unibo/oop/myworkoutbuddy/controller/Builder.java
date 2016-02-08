package it.unibo.oop.myworkoutbuddy.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.unibo.oop.myworkoutbuddy.util.Preconditions;

/**
 * 
 * @param <T>
 */
public class Builder<T> {

    private final Class<? extends T> clazz;

    private final List<Class<?>> types;

    private final Map<String, Optional<?>> fields;

    private boolean built;

    /**
     * Creates a new {@link it.unibo.oop.myworkoutbuddy.controller.Builder} instance using the given class.
     * 
     * @param clazz
     *            The class instance to represent.
     */
    public Builder(final Class<? extends T> clazz) {
        this(clazz, new HashSet<>());
    }

    /**
     * Creates a new {@link it.unibo.oop.myworkoutbuddy.controller.Builder} instance using the given class.
     * 
     * @param clazz
     *            The class instance to represent.
     * @param excludedFields
     *            A {@link java.util.Set} with all the field names to not include in the constructor.
     */
    public Builder(final Class<? extends T> clazz, final Set<String> excludedFields) {
        built = false;
        this.clazz = clazz;

        types = Arrays.stream(clazz.getDeclaredFields())
                .filter(filters(excludedFields))
                .map(Field::getType)
                .collect(Collectors.toList());

        fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(filters(excludedFields))
                .collect(Collectors.toMap(Field::getName, f -> Optional.empty()));
    }

    /**
     * 
     * @param fieldName
     * @param value
     * @return
     */
    public Builder<T> set(final String fieldName, final Object value) {
        Preconditions.checkState(!built);
        Preconditions.checkArgument(fields.get(fieldName) != null);
        fields.merge(fieldName, Optional.of(value), (o, n) -> n);
        return this;
    }

    /**
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public T build() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        Preconditions.checkState(!built);
        built = true;
        return (T) clazz
                .getConstructor(types.toArray(new Class[types.size()]))
                .newInstance(fields.values().stream()
                        .map(Optional::get)
                        .toArray());
    }

    /**
     * @return A map representation of the fields. Values are set to {@code null} if the field hasn't a value yet.
     */
    public Map<String, Object> toMap() {
        Preconditions.checkState(!built);
        return fields.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().orElse(null)));
    }

    private static Predicate<? super Field> filters(final Set<String> excludedFields) {
        return f -> !Modifier.isStatic(f.getModifiers())
                && !f.getName().contains("this")
                && !excludedFields.contains(f.getName());
    }

}
