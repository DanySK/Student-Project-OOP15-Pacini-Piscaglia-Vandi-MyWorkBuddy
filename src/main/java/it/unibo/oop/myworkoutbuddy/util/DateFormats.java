package it.unibo.oop.myworkoutbuddy.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class to convert {@link Date}s into string formats and viceversa.
 */
public final class DateFormats {

    private static final DateFormat UTC_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * Formats a date into an UTC format string.
     * 
     * @param date
     *            the time value to be formatted into a time string
     * @return the formatted UTC time string
     */
    public static String toUTCString(final Date date) {
        return UTC_FORMAT.format(date);
    }

    /**
     * Parses the given UTC string into a date.
     * 
     * @param utcTime
     *            the UTC time string to parse into a date
     * @return a date parsed from the string
     * @throws IllegalArgumentException
     *             if the given {@code utcString} is not valid
     */
    public static Date parseUTC(final String utcTime) {
        try {
            return UTC_FORMAT.parse(utcTime);
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private DateFormats() {
    }

}
