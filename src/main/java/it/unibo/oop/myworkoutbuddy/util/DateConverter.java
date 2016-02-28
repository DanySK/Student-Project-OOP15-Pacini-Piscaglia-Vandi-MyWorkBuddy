package it.unibo.oop.myworkoutbuddy.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Utility class to convert different date types to each other.
 * e.g. convert {@link Date}s to {@link LocalDate} and viceversa.
 *
 */
public final class DateConverter {

    public static LocalDate dateToLocalDate(final Date date) {
        return date
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date localDateToDate(final LocalDate localDate) {
        return Date.from(
                localDate
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant());
    }

    private DateConverter() {
    }

}
