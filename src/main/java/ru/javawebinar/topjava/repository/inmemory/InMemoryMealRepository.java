package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(1, meal));
        MealsUtil.MEALS2.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);

            log.info("user id {}, save {}", userId, meal);

            repository.computeIfAbsent(userId, k -> new HashMap<>()).put(meal.getId(), meal);

            return meal;
        }

        // treat case: update, but not present in storage
        Map<Integer, Meal> entryMeals = repository.get(userId);
        if (entryMeals != null && entryMeals.get(meal.getId()) != null) {
            log.info("user id {}, update {}", userId, meal);
            return entryMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }

        log.info("user id {}, no save/update {}", userId, meal);
        return null;
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("user id {}, delete id {}", userId, id);

        Map<Integer, Meal> entryMeals = repository.get(userId);
        if (entryMeals != null) {
            return entryMeals.remove(id) != null;
        }

        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("user id {}, get id {}", userId, id);

        Map<Integer, Meal> entryMeals = repository.get(userId);
        return entryMeals != null ? entryMeals.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("user id {}, get all", userId);

        Map<Integer, Meal> entryMeals = repository.get(userId);
        return entryMeals == null ? Collections.emptyList() : entryMeals.values().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

