package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-app-jdbc.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal userMeal = service.get(USER_MEAL1_ID, USER_ID);
        assertMatch(userMeal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void getAll() {
        List<Meal> userMeals = service.getAll(USER_ID);
        assertMatch(userMeals, MealTestData.userMeals);
        List<Meal> adminMeals = service.getAll(ADMIN_ID);
        assertMatch(adminMeals, MealTestData.adminMeals);
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(DATE_START, DATE_END, USER_ID), userMealsWithDateInterval);
    }

    @Test
    public void getBetweenWithNullDates() {
        assertMatch(service.getBetweenInclusive(null, null, USER_ID), userMeals);
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL1_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdatedForUser();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL1_ID, USER_ID), getUpdatedForUser());
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(userMeal1, ADMIN_ID));
        assertMatch(service.get(USER_MEAL1_ID, USER_ID), userMeal1);
    }

    @Test
    public void create() {
        Meal createdForUser = service.create(getNewMeal(), USER_ID);
        Integer newIdForUser = createdForUser.getId();
        Meal newMealForUser = getNewMeal();
        newMealForUser.setId(newIdForUser);
        assertMatch(createdForUser, newMealForUser);
        assertMatch(service.get(newIdForUser, USER_ID), newMealForUser);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(userMeal1.getDateTime(), "Duplicate", 100), USER_ID));
    }
}