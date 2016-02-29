package it.unibo.oop.myworkoutbuddy.controller.validation;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static it.unibo.oop.myworkoutbuddy.util.CollectionUtils.copy;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * Validator.
 */
public class Validator {

    private final Table<Predicate<Object>, Object, String> validations;

    private Optional<List<String>> errorMessages;

    /**
     * Creates a new {@Validator}.
     */
    public Validator() {
        validations = HashBasedTable.create();
        errorMessages = Optional.empty();
    }

    /**
     * Adds a new validation.
     * 
     * @param validationStrategy
     *            the validator to perform on the {@code value}
     * @param value
     *            the value to validate
     * @param errorMessage
     *            the message to retrieve if the {@code validator} rejects the given {@code value}
     * @return it self
     * @param <T>
     *            the type of the parameter
     * @throws IllegalStateException
     *             if the validation was already performed
     * @throws NullPointerException
     *             if the {@code validator} or the {@code errorMessage} is {@code null}
     * @throws IllegalArgumentException
     *             if the {@code errorMessage} is empty
     */
    @SuppressWarnings("unchecked")
    public <T> Validator addValidation(
            final Predicate<? super T> validationStrategy,
            final T value,
            final String errorMessage) {
        checkValidationNotPerformed();
        checkArgument(requireNonNull(errorMessage).length() > 0);
        validations.put((Predicate<Object>) requireNonNull(validationStrategy), value, errorMessage);
        return this;
    }

    /**
     * Performs the validation of all the given validations.
     * If a validation does not pass the test adds the associated error message to the error messages list.
     * 
     * @throws IllegalStateException
     *             if the validation was already performed or there's no validation to do
     */
    public void validate() {
        checkValidationNotPerformed();
        checkState(!validations.isEmpty(), "Nothing to validate");
        final List<String> errorMessages = validations.cellSet().stream()
                .map(c -> new ImmutablePair<>(c.getRowKey().test(c.getColumnKey()), c.getValue()))
                .filter(p -> !p.getLeft())
                .map(Pair::getRight)
                .collect(Collectors.toList());
        this.errorMessages = Optional.of(errorMessages);
    }

    /**
     * Retrieves a list containing all the error messages.
     * 
     * @return a list containing all the error messages, an empty list if the validation passed with success
     * 
     * @throws IllegalStateException
     *             if no validation was performed
     */
    public List<String> getErrorMessages() {
        checkValidationPerformed();
        return copy(errorMessages.get());
    }

    /**
     * Returns true if the validation passed, false otherwise.
     * 
     * @return true if the validation passed, false otherwise
     * 
     * @throws IllegalStateException
     *             if no validation was performed
     */
    public boolean isValid() {
        checkValidationPerformed();
        return errorMessages.get().isEmpty();
    }

    /**
     * If the validation passed invokes the specified {@code runnable}.
     * 
     * @param runnable
     *            the block to be executed if the validator is valid
     * @throws IllegalStateException
     *             if no validation was performed
     * @throws NullPointerException
     *             if {@code runnable} is {@code null}
     */
    public void ifValid(final Runnable runnable) {
        if (isValid()) {
            runnable.run();
        }
    }

    private void checkValidationPerformed() {
        checkState(isValidationPerformed(), "Validation was not performed");
    }

    private void checkValidationNotPerformed() {
        checkState(!isValidationPerformed(), "Validation already performed");
    }

    private boolean isValidationPerformed() {
        return errorMessages.isPresent();
    }

}
