package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        assertMatch(service.get(MEAL1.getId(), USER_ID), MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(MEAL1.getId(), ADMIN_ID);
    }

    @Test
    public void delete() {
        List<Meal> meals = Arrays.asList(MEAL3, MEAL2);
        service.delete(MealTestData.MEAL1.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), meals);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() {
        service.delete(MEAL2.getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(MEAL1.getDate(), MEAL2.getDate(), USER_ID);
        assertMatch(meals, Arrays.asList(MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), Arrays.asList(MEAL6, MEAL5, MEAL4));
    }

    @Test
    public void update() {
        MEAL1.setCalories(600);
        service.update(MEAL1, USER_ID);
        assertMatch(service.get(MEAL1.getId(), USER_ID), MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        MEAL1.setCalories(600);
        service.update(MEAL1, ADMIN_ID);
        assertMatch(service.get(MEAL1.getId(), ADMIN_ID), MEAL1);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.of(2019, 10, 19, 20, 0, 0), "поздний ужин", 2000);
        service.create(meal, USER_ID);
        assertMatch(service.get(meal.getId(), USER_ID), meal);
    }
}
