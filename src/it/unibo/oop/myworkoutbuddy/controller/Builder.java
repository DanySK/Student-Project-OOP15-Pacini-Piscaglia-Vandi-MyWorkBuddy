package it.unibo.oop.myworkoutbuddy.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.oop.myworkoutbuddy.util.Preconditions;

/**
 * 
 * @param <T>
 */
public class Builder<T> {

    private final Class<T> clazz;

    private final Class<?>[] fieldTypes;

    private final Map<String, Optional<?>> fields;

    private boolean built;

    public Builder(Class<T> clazz) {
        built = false;
        this.clazz = clazz;
        final List<Class<?>> declaredFields = new ArrayList<>();

        fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.getName().contains("this"))
                .peek(f -> declaredFields.add(f.getType()))
                .collect(Collectors.toMap(f -> f.getName(), f -> Optional.empty()));

        fieldTypes = declaredFields.toArray(new Class[declaredFields.size()]);
    }

    public Builder<T> set(String fieldName, Object value) {
        Preconditions.checkState(!built);
        Preconditions.checkArgument(fields.get(fieldName) != null);
        fields.merge(fieldName, Optional.of(value), (o, n) -> n);
        return this;
    }

    public Set<String> getFieldNames() {
        Preconditions.checkState(!built);
        return Collections.unmodifiableSet(fields.keySet());
    }

    public T build() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        Preconditions.checkState(!built);
        built = true;
        return (T) clazz
                .getConstructor(fieldTypes)
                .newInstance(fields.values().stream()
                        .map(Optional::get)
                        .toArray());
    }

    public Map<String, Object> toMap() {
        Preconditions.checkState(!built);
        return fields.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().get()));
    }

}
