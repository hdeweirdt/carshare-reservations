package be.harm.carshare.reservations;

import java.time.LocalDateTime;

public class DateUtils {
    public static boolean dateIsBetween(LocalDateTime dateTime, LocalDateTime start, LocalDateTime end) {
        return (dateTime.isAfter(start) && dateTime.isBefore(end)) || (dateTime.isBefore(start) && dateTime.isAfter(end));
    }
}
