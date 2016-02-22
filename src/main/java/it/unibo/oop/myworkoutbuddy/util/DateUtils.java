package it.unibo.oop.myworkoutbuddy.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Utility class for Date conversions.
 */
public final class DateUtils {

    /**
     * Converts a {@link LocalDate} to a {@link Date}.
     * 
     * @param localDate
     *            the local date to insert
     * @return the {@link Date}
     */
    public static Date localDateToDate(final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private DateUtils() {
        throw new IllegalAccessError();
    }
}
