package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2019, Month.OCTOBER, 12, 6, 0), "Завтрак user 1", 500),
            new Meal(LocalDateTime.of(2019, Month.OCTOBER, 12, 9, 0), "Обед user 1", 500),
            new Meal(LocalDateTime.of(2019, Month.OCTOBER, 13, 11, 0), "Обед user 1", 300),
            new Meal(LocalDateTime.of(2019, Month.OCTOBER, 13, 13, 0), "Ужин user 1", 2100)
    );

    public static final List<Meal> MEALS2 = Arrays.asList(
            new Meal(LocalDateTime.of(2019, Month.MAY, 20, 10, 0), "Завтрак user 2", 500),
            new Meal(LocalDateTime.of(2019, Month.MAY, 21, 13, 0), "Обед user 2", 2100),
            new Meal(LocalDateTime.of(2019, Month.MAY, 30, 10, 0), "Завтрак user 2", 500),
            new Meal(LocalDateTime.of(2019, Month.MAY, 30, 10, 0), "Обед user 2", 500)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return getFiltered(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return getFiltered(meals, caloriesPerDay, meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime));
    }

    private static List<MealTo> getFiltered(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );
        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}