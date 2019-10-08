package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealsDaoMemory implements MealsDao {

    private AtomicLong id = new AtomicLong(0);
    private Map<Long, Meal> meals = new ConcurrentHashMap<>();

    @Override
    public List<Meal> readAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(id.getAndIncrement());
        meals.put(meal.getId(), meal);

        return meal;
    }

    @Override
    public Meal edit(Meal meal) {
        meals.put(meal.getId(), meal);
        return meal;
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
