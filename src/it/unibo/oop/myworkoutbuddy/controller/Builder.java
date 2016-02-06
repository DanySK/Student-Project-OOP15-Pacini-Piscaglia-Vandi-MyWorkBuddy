package it.unibo.oop.myworkoutbuddy.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Builder<T> {

    private final Class<T> clazz;

    private final Class<?>[] fieldTypes;

    private final Map<String, Optional<?>> fields;

    public Builder(Class<T> clazz) {
        this.clazz = clazz;
        final List<Class<?>> declaredFields = new ArrayList<>();

        fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.getName().contains("this"))
                .peek(f -> declaredFields.add(f.getType()))
                .collect(Collectors.toMap(f -> f.getName(), f -> Optional.empty()));

        fieldTypes = declaredFields.toArray(new Class[declaredFields.size()]);
    }

    public Builder<T> set(String fieldName, Object value) {
        if (fields.get(fieldName) == null) {
            throw new IllegalArgumentException("Field not found.");
        }
        fields.merge(fieldName, Optional.of(value), (o, n) -> n);
        return this;
    }

    /**
     * @return The field names represented by this class.
     */
    public Set<String> getFieldNames() {
        return Collections.unmodifiableSet(fields.keySet());
    }

    /**
     * 
     * @return a new instance of the given class.
     * @throws NoSuchMethodException
     *             if a matching method is not found.
     * @throws SecurityException
     *             If a security manager, <i>s</i>, is present and
     *             the caller's class loader is not the same as or an
     *             ancestor of the class loader for the current class and
     *             invocation of {@link SecurityManager#checkPackageAccess
     *             s.checkPackageAccess()} denies access to the package
     *             of this class.
     *
     * @throws IllegalAccessException
     *             if this {@code Constructor} object
     *             is enforcing Java language access control and the underlying
     *             constructor is inaccessible.
     * @throws IllegalArgumentException
     *             if the number of actual and formal parameters differ; if an unwrapping conversion for primitive
     *             arguments fails; or if, after possible unwrapping, a parameter value cannot be converted to the
     *             corresponding formal parameter type by a method invocation conversion; if this constructor pertains
     *             to an enum type.
     * @throws InstantiationException
     *             if the class that declares the underlying constructor represents an abstract class.
     * @throws InvocationTargetException
     *             if the underlying constructor
     *             throws an exception.
     */
    public T build() throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException,
            InvocationTargetException, NoSuchMethodException {
        return (T) clazz
                .getConstructor(fieldTypes)
                .newInstance(fields.values().stream()
                        .map(Optional::get)
                        .toArray());
    }

}
