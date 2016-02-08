package it.unibo.oop.myworkoutbuddy.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;

/**
 * Builder for creating objects.
 * 
 * <p>
 * Each class that wants to use this Builder to create instances should run the
 * {@link ReflectionUtils#getConstructorShape(Class, Set)} method to know exactly how the arguments will be passed to
 * the constructor when the object will be built. This will prevent {@link UnsupportedOperationException}s at run-time.
 * 
 * @param <T>
 *            The type of the object.
 */
public class Builder<T> {

    private final Class<? extends T> clazz;

    private final Map<String, Class<?>> types;

    private final Map<String, Optional<?>> fields;

    private boolean built;

    /**
     * Creates a new {@link Builder} instance using the given class.
     * 
     * @param clazz
     *            The class to use.
     */
    public Builder(final Class<? extends T> clazz) {
        this(clazz, new HashSet<>());
    }

    /**
     * Creates a new {@link Builder} instance using the given class.
     * 
     * @param clazz
     *            The class instance to use.
     * @param excludedFields
     *            A {@link et} with all the field names to not include in the constructor.
     */
    public Builder(final Class<? extends T> clazz, final Set<String> excludedFields) {
        built = false;
        this.clazz = clazz;

        types = Arrays.stream(clazz.getDeclaredFields())
                .filter(filters(excludedFields))
                .collect(Collectors.toMap(Field::getName, Field::getType));

        fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(filters(excludedFields))
                .collect(Collectors.toMap(Field::getName, f -> Optional.empty()));
    }

    /**
     * Updates the {@code value} of the field specified using the {@code fieldName}.
     * 
     * @param fieldName
     *            The name of the field to update.
     * @param value
     *            The new value for the field.
     * @throws IllegalStateException
     *             if the {@link Builder} was already used to build some object.
     * @throws IllegalArgumentException
     *             if the given {@code fieldName} was not found or the given value is not assignable to the field.
     * @return it self.
     */
    public Builder<T> set(final String fieldName, final Object value) {
        Preconditions.checkState(!built);
        Preconditions.checkArgument(fields.get(fieldName) != null
                && ClassUtils.isAssignable(value.getClass(), types.get(fieldName)));

        fields.merge(fieldName, Optional.of(value), (o, n) -> n);
        return this;
    }

    /**
     * @throws IllegalStateException
     *             if the {@link Builder} was already used to build some object.
     * @throws NoSuchElementException
     *             if some fields are not set.
     * @throws UnsupportedOperationException
     *             if some errors happens while the instance is being created.
     * @return A new instance of the object using the {@link java.lang.Class} given to the constructor.
     */
    public T build() {
        Preconditions.checkState(!built);
        built = true;
        try {
            return (T) clazz
                    .getConstructor(types.values().toArray(new Class[types.values().size()]))
                    .newInstance(fields.values().stream()
                            .map(Optional::get)
                            .toArray());
        } catch (final InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException e) {
            throw new UnsupportedOperationException();
        }
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
        return f -> !Modifier.isStatic(f.getModifiers()) // Static fields should not be in a constructor
                && !f.getName().contains("this") // "this" should not be in a constructor
                && !excludedFields.contains(f.getName()); // We don't want any of these fields in the constructor
    }

}
