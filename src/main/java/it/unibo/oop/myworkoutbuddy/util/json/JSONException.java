package it.unibo.oop.myworkoutbuddy.util.json;

/**
 * Signals that a JSON exception of some sort has occurred. This class is the general class of exceptions produced by
 * failed JSON operations.
 */
public class JSONException extends Exception {

    private static final long serialVersionUID = -2340427922641753527L;

    public JSONException() {
        super();
    }

    public JSONException(final String message) {
        super(message);
    }

    public JSONException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JSONException(final Throwable cause) {
        super(cause);
    }

}
