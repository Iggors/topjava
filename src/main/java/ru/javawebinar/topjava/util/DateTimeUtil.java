package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return (startTime == null || lt.compareTo(startTime) >= 0) && (endTime == null || lt.compareTo(endTime) < 0);
    }

    public static LocalDate getStartDateOrMin(LocalDate localDate) {
        return localDate == null ? LocalDate.of(1, 1, 1) : localDate.minusDays(1);
    }

    public static LocalDate getEndDateOrMax(LocalDate localDate) {
        return localDate == null ? LocalDate.of(5000, 1, 1) : localDate.plusDays(1);
    }

    public static LocalDate parseLocalDate(String str) {
        return str == null || str.isEmpty() ? null : LocalDate.parse(str);
    }

    public static LocalTime parseLocalTime(String str) {
        return str == null || str.isEmpty() ? null : LocalTime.parse(str);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

