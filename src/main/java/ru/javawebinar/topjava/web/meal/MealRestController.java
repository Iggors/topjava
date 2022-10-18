package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public void create(Meal meal) {
        int userId = authUserId();
        checkNew(meal);
        service.create(meal, authUserId());
    }

    public void delete(int id) {
        int userId = authUserId();
        service.delete(id, userId);
    }

    public Meal get(int id) {
        int userId = authUserId();
        return service.get(id, userId);
    }

    public Collection<MealTo> getAll() {
        int userId = authUserId();
        return service.getAll(userId);
    }

    public void update(Meal meal, int id) {
        int userId = authUserId();
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }
}