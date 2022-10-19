package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.getEndDateOrMax;
import static ru.javawebinar.topjava.util.DateTimeUtil.getStartDateOrMin;
import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository mealRepository) {
        this.repository = mealRepository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<MealTo> getAll(int userId) {
        return getTos(repository.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public List<MealTo> getAllBetweenDates(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        final List<Meal> mealsBetweenDate = repository.getAllBetweenDate(getStartDateOrMin(startDate), getEndDateOrMax(endDate), userId);
        return getFilteredTos(mealsBetweenDate, DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }
}