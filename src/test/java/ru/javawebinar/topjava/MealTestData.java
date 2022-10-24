package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final int USER_MEAL1_ID = START_SEQ + 3;
    public static final int ADMIN_MEAL_ID = START_SEQ + 10;
    public static final LocalDate DATE_START = LocalDate.of(2020, 1, 31);
    public static final LocalDate DATE_END = LocalDate.of(2020, 1, 31);
    public static final Meal userMeal1 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(START_SEQ + 9, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин", 410);

    public static final Meal adminMeal1 = new Meal(START_SEQ + 10, LocalDateTime.of(2015, 6, 1, 10, 0), "Админ завтрак", 300);
    public static final Meal adminMeal2 = new Meal(START_SEQ + 11, LocalDateTime.of(2015, 6, 1, 14, 0), "Админ ланч", 510);
    public static final Meal adminMeal3 = new Meal(START_SEQ + 12, LocalDateTime.of(2015, 6, 1, 21, 0), "Админ ужин", 1500);
    public static final List<Meal> userMealsTestData = Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    public static final List<Meal> adminMealsTestData = Arrays.asList(adminMeal3, adminMeal2, adminMeal1);
    public static final List<Meal> userMealsWithDateInterval = Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static Meal getUpdatedForUser() {
        return new Meal(USER_MEAL1_ID, userMeal1.getDateTime().plus(2, ChronoUnit.MINUTES), "Обновленный завтрак", 200);
    }

    public static Meal getNewMeal() {
        return new Meal(null, LocalDateTime.of(2022, 10, 23, 15, 0), "newMeal", 100);
    }
}
