package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    private enum Month {
        
        JANUARY( 31),
        FEBRUARY(28),
        MARCH(31),
        APRIL( 30),
        MAY( 31),
        JUNE(30),
        JULY( 31),
        AUGUST( 31),
        SEPTEMBER(30),
        OCTOBER( 31),
        NOVEMBER( 30),
        DECEMBER( 31);

        private final int days;

        Month(int days) {
            this.days = days;
        }

        public static Month fromString(String monthName) {
            Objects.requireNonNull(monthName);
            try {
                return valueOf(monthName);
            } catch (IllegalArgumentException e) {
                // Fallback to manual scan before giving up
                Month match = null;
                for (final Month month: values()) {
                    if (month.toString().toLowerCase(Locale.ROOT).startsWith(monthName.toLowerCase(Locale.ROOT))) {
                        if (match != null) {
                            throw new IllegalArgumentException(
                                monthName + " is ambiguous: both " + match + " and " + month + " would be valid matches",
                                e
                            );
                        }
                        match = month;
                    }
                }
                if (match == null) {
                    throw new IllegalArgumentException("No matching months for " + monthName, e);
                }
                return match;
            }
        }
    }

    private class SortByMonthOrder implements Comparator<String> {
        @Override
        public int compare(String m1, String m2) {
            return Month.fromString(m1).compareTo(Month.fromString(m2));
        }
    }

    private class  SortByDate implements Comparator<String> {
        @Override
        public int compare(String m1, String m2) {
            return Integer.compare(Month.fromString(m1).days, Month.fromString(m2).days);
        }
    }

}
