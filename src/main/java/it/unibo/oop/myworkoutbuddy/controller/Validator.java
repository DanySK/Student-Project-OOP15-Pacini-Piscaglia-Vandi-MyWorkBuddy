package it.unibo.oop.myworkoutbuddy.controller;

import static com.google.common.base.Preconditions.checkState;
import static it.unibo.oop.myworkoutbuddy.util.CollectionUtils.copy;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class Validator {

    private final Table<Predicate<Object>, Object, String> validations;

    private List<String> errorMessages;

    private boolean validationDone;

    public Validator() {
        validations = HashBasedTable.create();
    }

    @SuppressWarnings("unchecked")
    public <T> void addValidation(final Predicate<? super T> validator, T obj, final String errorMessage) {
        checkState(!validationDone);
        validations.put((Predicate<Object>) requireNonNull(validator), obj, requireNonNull(errorMessage));
    }

    public void validate() {
        checkState(!validationDone && isNull(errorMessages));
        validationDone = true;
        errorMessages = validations.cellSet().stream()
                .map(c -> new SimpleEntry<>(c.getRowKey().test(c.getColumnKey()), c.getValue()))
                .filter(e -> !e.getKey())
                .map(Entry::getValue)
                .collect(Collectors.toList());
    }

    public List<String> getErrorMessages() {
        checkErrorMessages();
        return copy(errorMessages);
    }

    public boolean isValid() {
        checkErrorMessages();
        return errorMessages.isEmpty();
    }

    public void addErrorMessage(final String errorMessage) {
        checkErrorMessages();
        errorMessages.add(errorMessage);
    }

    private void checkErrorMessages() {
        checkState(validationDone && !isNull(errorMessages));
    }

}
