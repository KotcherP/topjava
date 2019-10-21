package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final Meal MEAL1 = new Meal(START_SEQ + 2, LocalDateTime.of(2019, 10, 19, 10, 0, 0), "завтрак", 500);
    public static final Meal MEAL2 = new Meal(START_SEQ + 3, LocalDateTime.of(2019, 10, 19, 12, 0, 0), "обед", 1000);
    public static final Meal MEAL3 = new Meal(START_SEQ + 4, LocalDateTime.of(2019, 10, 19, 12, 0, 0), "ужин", 500);
    public static final Meal MEAL4 = new Meal(START_SEQ + 5, LocalDateTime.of(2019, 10, 20, 11, 0, 0), "завтрак", 500);
    public static final Meal MEAL5 = new Meal(START_SEQ + 6, LocalDateTime.of(2019, 10, 20, 13, 0, 0), "обед", 1000);
    public static final Meal MEAL6 = new Meal(START_SEQ + 7, LocalDateTime.of(2019, 10, 20, 15, 0, 0), "ужин", 510);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
