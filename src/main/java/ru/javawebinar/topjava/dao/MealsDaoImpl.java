package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealsDaoImpl implements MealsDao {

    private static AtomicLong id = new AtomicLong(0);
    private static Map<Long,Meal> meals = new ConcurrentHashMap<>();

    static {
        Meal meal = new Meal(id.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        meals.put(meal.getId(),meal);
        meal = new Meal(id.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        meals.put(meal.getId(),meal);
        meal = new Meal(id.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        meals.put(meal.getId(),meal);
        meal = new Meal(id.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        meals.put(meal.getId(),meal);
        meal = new Meal(id.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        meals.put(meal.getId(),meal);
        meal = new Meal(id.getAndIncrement(),LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        meals.put(meal.getId(),meal);
    }

    @Override
    public List<Meal> readAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void add(Meal meal) {
        meal.setId(id.getAndIncrement());
        meals.put(meal.getId(),meal);
    }

    @Override
    public void edit(Meal meal) {
        meals.put(meal.getId(),meal);
    }

    @Override
    public void delete(long id) {
        meals.remove(id);
    }

    @Override
    public Meal getById(long id) {
        return meals.get(id);
    }
}
