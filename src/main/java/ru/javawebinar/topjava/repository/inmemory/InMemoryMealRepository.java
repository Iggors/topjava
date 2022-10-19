package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.ADMIN_ID;
import static ru.javawebinar.topjava.web.SecurityUtil.USER_ID;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, USER_ID));
        save(new Meal(LocalDateTime.of(2022, Month.OCTOBER, 19, 9, 0), "Завтрак администратора", 600), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2022, Month.OCTOBER, 19, 14, 0), "Обед администратора", 900), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2022, Month.OCTOBER, 19, 21, 0), "Ужин администратора", 1000), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2022, Month.OCTOBER, 20, 21, 0), "Завтрак администратора", 500), ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("Saving meal id={} for user id={}", meal.getId(), userId);
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, key -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), new Meal(meal.getId(), meal.getDateTime(),
                    meal.getDescription(), meal.getCalories()));
            return meal;
        }
        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("Deleting meal id={} of user id={}", id, userId);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("Geting meal id={} of user id={}", id, userId);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("Geting all meal, user id={}", userId);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? Collections.emptyList() : meals.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllBetweenDate(LocalDate startDate, LocalDate endDate, int userId) {
        Map<Integer, Meal> mealsBetweenDate = repository.get(userId);
        return mealsBetweenDate == null ? Collections.emptyList() : mealsBetweenDate.values().stream()
                .filter(m -> m.getDate().isAfter(startDate) && m.getDate().isBefore(endDate))
                .collect(Collectors.toList());
    }
}


