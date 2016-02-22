package it.unibo.oop.myworkoutbuddy.util.json;

import java.util.Objects;

/**
 * Wraps a {@link JSONException} with an unchecked exception.
 */
public class UncheckedJSONException extends RuntimeException {

    private static final long serialVersionUID = 4877535718122925979L;

    public UncheckedJSONException(final JSONException cause) {
        super(cause);
    }

    public UncheckedJSONException(final String message, final JSONException cause) {
        super(message, Objects.requireNonNull(cause));
    }

}
