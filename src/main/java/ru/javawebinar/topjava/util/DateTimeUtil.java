package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, T start, T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static LocalDateTime getStartDateOrMin(LocalDate localDate) {
        return localDate == null ? LocalDateTime.of(LocalDate.MIN, LocalTime.MIN) : localDate.atStartOfDay();
    }

    public static LocalDateTime getEndDateOrMax(LocalDate localDate) {
        return localDate == null ? LocalDateTime.of(LocalDate.MAX, LocalTime.MAX) : localDate.plus(1, ChronoUnit.DAYS).atStartOfDay();
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

