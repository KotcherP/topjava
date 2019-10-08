package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDao {
    List<Meal> readAll();

    Meal add(Meal meal);

    Meal edit(Meal meal);

    void delete(long id);

    Meal getById(long id);
}
