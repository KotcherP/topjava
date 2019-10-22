package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-basis.xml",
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
        assertMatch(service.get(USER_MEAL2.getId(), USER_ID), USER_MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(USER_MEAL1.getId(), ADMIN_ID);
    }

    @Test
    public void delete() {
        List<Meal> meals = Arrays.asList(USER_MEAL3, USER_MEAL2);
        service.delete(USER_MEAL1.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), meals);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() {
        service.delete(USER_MEAL2.getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(ADMIN_MEAL4.getDate(), ADMIN_MEAL5.getDate(), ADMIN_ID);
        assertMatch(meals, Arrays.asList(ADMIN_MEAL6, ADMIN_MEAL5, ADMIN_MEAL4));
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), Arrays.asList(ADMIN_MEAL7,ADMIN_MEAL6, ADMIN_MEAL5, ADMIN_MEAL4));
    }

    @Test
    public void update() {
        USER_MEAL1.setCalories(600);
        service.update(USER_MEAL1, USER_ID);
        assertMatch(service.get(USER_MEAL1.getId(), USER_ID), USER_MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        USER_MEAL1.setCalories(600);
        service.update(USER_MEAL1, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.of(2019, 10, 19, 20, 0, 0), "поздний ужин", 2000);
        service.create(meal, USER_ID);
        assertMatch(service.get(meal.getId(), USER_ID), meal);
    }
}
