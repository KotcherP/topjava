package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)

                ,new UserMeal(LocalDateTime.of(2015, Month.MAY, 22, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 15, 7, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 9, 12, 0), "Ужин", 510)
        );

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //.toLocalDate();
        //.toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> res = new ArrayList<>();
        Map<LocalDate, Integer> mapSum = new HashMap<>();

        for (UserMeal um: mealList
             ) {
            mapSum.merge(toLocalDate(um),um.getCalories(),Integer::sum);
        }

        for (UserMeal um: mealList
        ) {
            if(TimeUtil.isBetween(toLocalTime(um),startTime,endTime)){
                res.add(new UserMealWithExceed(um.getDateTime(), um.getDescription(),
                        um.getCalories(), mapSum.get(toLocalDate(um)) > caloriesPerDay));
            }
        }

        return res;

    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapSum = mealList.stream()
                .collect(Collectors.groupingBy(UserMealsUtil::toLocalDate,Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(um->TimeUtil.isBetween(toLocalTime(um),startTime,endTime))
                .map(um -> new UserMealWithExceed(um.getDateTime(), um.getDescription(),
                        um.getCalories(), mapSum.get(toLocalDate(um)) > caloriesPerDay))
                .collect(Collectors.toList());

    }

    public static LocalDate toLocalDate(UserMeal userMeal){
        return userMeal.getDateTime().toLocalDate();
    }

    public static LocalTime toLocalTime(UserMeal userMeal){
        return userMeal.getDateTime().toLocalTime();
    }
}
