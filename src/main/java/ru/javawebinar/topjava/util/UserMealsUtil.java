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

        //Сделал отдельными функциями с фильтром по часам и дням. Поидее,надо использовать получившийся результат List<UserMealWithExceed>
        //в обоих функциях для применения двух фильтров:и по дням и по часам (судя по логике работы на http://topjava.herokuapp.com/meals).
        //Но оставил как есть,чтобы не менять аргументы функции getFilteredWithExceeded
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredWithExceeded(mealList, LocalDate.of(2015,Month.MAY,30), LocalDate.of(2015,Month.MAY,31), 2000);
        //.toLocalDate();
        //.toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        List<UserMealWithExceed> resultList = new ArrayList<>();

        //добавил функцию getDay() в UserMeal для вызова Collectors.groupingBy. Через getDateTime().getDayOfMonth() не получилось.
        Map<Integer, List<UserMeal>> mapDayCollect = mealList.stream().collect(Collectors.groupingBy(UserMeal::getDay));

        //пункт 3 замечаний к HW0: не понял,как сделать без вложенных циклов.Stream на Map не работает. Map появился при группировке
        //калорий по дням для вычисления суммы калорий за день(заполнение поля exceed) до фильтра по часам и дням.
        for (Map.Entry<Integer, List<UserMeal>> entry : mapDayCollect.entrySet()
        ) {

            int totalCaloriesPerDay = entry.getValue().stream().mapToInt(x -> x.getCalories()).sum();

            //пункт 5 замечаний к HW0: map не заполняет resultList,заменил на forEach
            entry.getValue().stream()
                    .filter(x -> x.getDateTime().toLocalTime().compareTo(startTime) >= 0 && x.getDateTime().toLocalTime().compareTo(endTime) <= 0)
                    .forEach(x -> resultList.add(new UserMealWithExceed(x.getDateTime(), x.getDescription(),
                            x.getCalories(), totalCaloriesPerDay > caloriesPerDay)));

        }

        return resultList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalDate startTime, LocalDate endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        List<UserMealWithExceed> resultList = new ArrayList<>();
        Map<Integer, List<UserMeal>> mapDayCollect = mealList.stream().collect(Collectors.groupingBy(UserMeal::getDay));

        for (Map.Entry<Integer, List<UserMeal>> entry : mapDayCollect.entrySet()
        ) {

            int totalCaloriesPerDay = entry.getValue().stream().mapToInt(x -> x.getCalories()).sum();

            entry.getValue().stream()
                    .filter(x -> x.getDateTime().toLocalDate().compareTo(startTime) >= 0 && x.getDateTime().toLocalDate().compareTo(endTime) <= 0)
                    .forEach(x -> resultList.add(new UserMealWithExceed(x.getDateTime(), x.getDescription(),
                            x.getCalories(), totalCaloriesPerDay > caloriesPerDay)));
        }

        return resultList;
    }

}
