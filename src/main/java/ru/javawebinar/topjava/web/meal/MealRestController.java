package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public void create(Meal meal) {
        int userId = authUserId();
        log.info("Creat meal id={}, user id={}", meal, userId);
        checkNew(meal);
        service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("Delete meal id={}, user id={}", id, userId);
        service.delete(id, userId);
    }

    public Meal get(int id) {
        int userId = authUserId();
        log.info("Get meal id={}, user id={}", id, userId);
        return service.get(id, userId);
    }

    public List<MealTo> getAll() {
        int userId = authUserId();
        log.info("Get all meal, user id={}", userId);
        return service.getAll(userId);
    }

    public void update(Meal meal, int id) {
        int userId = authUserId();
        log.info("Update meal id={}, user id={}", meal, userId);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public List<MealTo> getAllBetweenDates(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = authUserId();
        log.info("get all meal filtered by date, user id={}", userId);
        return service.getAllBetweenDates(startDate, endDate, startTime, endTime, userId);
    }
}