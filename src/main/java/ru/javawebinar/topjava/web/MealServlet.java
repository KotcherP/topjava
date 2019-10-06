package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // log.debug("redirect to meals");

        List<Meal> meals = MealsDao.getMeals();
        List<MealTo> mealsTo = MealsUtil.getFiltered(meals, LocalTime.MIN,LocalTime.MAX,CALORIES_PER_DAY);

        request.setAttribute("mealsTo",mealsTo);
        request.getRequestDispatcher("meals.jsp").forward(request,response);
    }

}
