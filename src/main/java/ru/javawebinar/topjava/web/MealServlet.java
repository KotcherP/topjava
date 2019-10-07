package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.dao.MealsDaoImpl;
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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final MealsDao mealsDao = new MealsDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // log.debug("redirect to meals");

        String action = request.getParameter("action");

        if(action != null){
            if(action.equalsIgnoreCase("edit")){
                request.setAttribute("mealEdit",mealsDao.getById(Long.parseLong(request.getParameter("id"))));
                request.getRequestDispatcher("editMeal.jsp").forward(request,response);
                return;
            }
            else if(action.equalsIgnoreCase("add")){
                request.getRequestDispatcher("editMeal.jsp").forward(request,response);
                return;
            }
            else if(action.equalsIgnoreCase("delete")){
                mealsDao.delete(Long.parseLong(request.getParameter("id")));
            }
        }

        List<MealTo> mealsTo = MealsUtil.getFiltered(mealsDao.readAll(), LocalTime.MIN,LocalTime.MAX,CALORIES_PER_DAY);

        request.setAttribute("dateTimeFormatter",dateTimeFormatter);
        request.setAttribute("mealsTo",mealsTo);
        request.getRequestDispatcher("meals.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if(id == null || id.isEmpty()){
            mealsDao.add(new Meal(dateTime,description,calories));
        }else{
            Meal meal = mealsDao.getById(Long.parseLong(id));
            meal.setDateTime(dateTime);
            meal.setDescription(description);
            meal.setCalories(calories);
            mealsDao.edit(meal);
        }

        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
