package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.dao.MealsDaoMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final MealsDao mealsDao = new MealsDaoMemory() {{
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 18, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 15, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 17, 0), "Ужин", 510));
    }};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "edit":
                    request.setAttribute("mealEdit", mealsDao.getById(Long.parseLong(request.getParameter("id"))));
                    request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                    log.debug("doGet: edit meals");
                    break;
                case "add":
                    request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                    log.debug("doGet: add to meals");
                    break;
                case "delete":
                    mealsDao.delete(Long.parseLong(request.getParameter("id")));
                    log.debug("doGet: delete from meals");
                    response.sendRedirect(request.getContextPath() + "/meals");
                    return;
            }
        }

        List<MealTo> mealsTo = MealsUtil.getFiltered(mealsDao.readAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);

        request.setAttribute("dateTimeFormatter", dateTimeFormatter);
        request.setAttribute("mealsTo", mealsTo);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (id == null || id.isEmpty()) {
            mealsDao.add(new Meal(dateTime, description, calories));
            log.debug("doPost: add to meals");
        } else {
            Meal meal = new Meal(Long.parseLong(id), dateTime, description, calories);
            mealsDao.edit(meal);
            log.debug("doPost: edit meals");
        }

        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
