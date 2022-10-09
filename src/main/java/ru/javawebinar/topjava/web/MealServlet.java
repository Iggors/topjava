package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getMealsTo;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(ru.javawebinar.topjava.web.MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            log.debug("Get all meals");
            request.setAttribute("meals", getMealsTo());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "add":
                break;
            case "update":
                break;
            case "delete":
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
    }
}
