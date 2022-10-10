package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(ru.javawebinar.topjava.web.MealServlet.class);
    private MealStorage mealStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealStorage = new InMemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        final int caloriesPerDay = 2000;
        final Meal meal;

        switch (action == null ? "all" : action) {
            case "all":
                log.info("Get all meals");
                request.setAttribute("meals", MealsUtil.filteredMealTo(mealStorage.getAll(), caloriesPerDay));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                return;
            case "add":
                log.info("Add new meal");
                meal = new Meal(LocalDateTime.now().withNano(0), "", 100);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                response.sendRedirect("meals");
                break;
            case "update":
                log.info("Update meal with id=" + Integer.parseInt(request.getParameter("id")));
                meal = mealStorage.get(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                response.sendRedirect("meals");
                break;
            case "delete":
                log.info("Delete meal with id=" + Integer.parseInt(request.getParameter("id")));
                mealStorage.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals");
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        final Meal meal = new Meal(
                id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );
        mealStorage.save(meal);
        response.sendRedirect("meals");
    }
}
