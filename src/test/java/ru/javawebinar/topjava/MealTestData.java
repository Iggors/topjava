package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final LocalDate DATE_START = LocalDate.of(2020, 1, 31);
    public static final LocalDate DATE_END = LocalDate.of(2020, 1, 31);
    public static final Meal userMeal100003 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal100004 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal100005 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal100006 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal100007 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal100008 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal100009 = new Meal(START_SEQ + 9, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин", 410);

    public static final Meal adminMeal100010 = new Meal(START_SEQ + 10, LocalDateTime.of(2015, 6, 1, 10, 0), "Админ завтрак", 300);
    public static final Meal adminMeal100011 = new Meal(START_SEQ + 11, LocalDateTime.of(2015, 6, 1, 14, 0), "Админ ланч", 510);
    public static final Meal adminMeal100012 = new Meal(START_SEQ + 12, LocalDateTime.of(2015, 6, 1, 21, 0), "Админ ужин", 1500);


    public static final List<Meal> userMealsTestData = Stream.of(
                    userMeal100003,
                    userMeal100004,
                    userMeal100005,
                    userMeal100006,
                    userMeal100007,
                    userMeal100008,
                    userMeal100009)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());


    public static final List<Meal> adminMealsTestData = Stream.of(
                    adminMeal100010,
                    adminMeal100011,
                    adminMeal100012)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> userMealsWithDateInterval = Stream.of(
                    userMeal100006,
                    userMeal100007,
                    userMeal100008,
                    userMeal100009)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static Meal getUpdatedForUser() {
        return new Meal(START_SEQ + 3, userMeal100003.getDateTime().plus(2, ChronoUnit.MINUTES), "Обновленный завтрак", 200);
    }

    public static Meal getNewMeal() {
        return new Meal(null, LocalDateTime.of(2022, 10, 23, 15, 00), "newMeal", 100);
    }
}
