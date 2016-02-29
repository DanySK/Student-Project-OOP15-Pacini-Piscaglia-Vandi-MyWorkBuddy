package it.unibo.oop.myworkoutbuddy.controller.validation;

import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.validator.routines.EmailValidator;

import it.unibo.oop.myworkoutbuddy.controller.Service;

/**
 * Implementations of Predicate that implement various useful validation operations.
 */
public final class ValidationStrategies {

    /**
     * @param minLength
     *            the minimum length for the value.
     * @return a Predicate to check if the length of a string is greater than or equal to the given {@code minLength}
     */
    public static Predicate<? super String> minLengthValidator(final int minLength) {
        return s -> !isNull(s) && (s.length() >= minLength);
    }

    /**
     * @param maxLength
     *            the minimum length for the value.
     * @return a Predicate to check if the length of a string is less than or equal to the given {@code maxLength}
     */
    public static Predicate<? super String> maxLengthValidator(final int maxLength) {
        return s -> !isNull(s) && (s.length() <= maxLength);
    }

    /**
     * @param service
     *            the {@code Service} to use for the interaction of the database
     * @param fieldName
     *            the name of the field in the database
     * 
     * @return a Predicate to check if the given field with is already taken by this value
     * @param <T>
     *            the type of the value
     */
    public static <T> Predicate<? super T> alreadyTakenValidator(final Service service, final String fieldName) {
        return v -> service.getDBService()
                .getOneByParams(newParameter(fieldName, v))
                .isPresent();
    }

    /**
     * @param other
     *            the
     * @return a Predicate to check if a value is equal to another
     * @param <T>
     *            the type of the Predicate and the {@code other} value
     */
    public static <T> Predicate<? super T> confirmValidator(final T other) {
        return v -> v.equals(other);
    }

    /**
     * @return a Predicate to check if an email is valid
     */
    public static Predicate<? super String> emailValidator() {
        return e -> !isNull(e) && EmailValidator.getInstance().isValid(e);
    }

    /**
     * @return a predicate to check if a number is greater than zero
     */
    public static Predicate<? super Number> positiveNumberValidator() {
        return n -> !isNull(n) && Double.compare(n.doubleValue(), 0) > 0;
    }

    private static Map<String, Object> newParameter(final String name, final Object value) {
        final Map<String, Object> param = new HashMap<>();
        param.put(name, value);
        return param;
    }

    private ValidationStrategies() {
    }

}
