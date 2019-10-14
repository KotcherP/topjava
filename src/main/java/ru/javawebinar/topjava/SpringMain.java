package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            // System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "John", "email1@mail.ru", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "Boris", "email2@mail.ru", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "Born", "email3@mail.ru", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 15, 10, 0), "Завтрак", 500));

//            mealRestController.getAll(LocalDate.of(2015, 05, 13),
//                    null,
//                    LocalTime.of(10, 0),
//                    LocalTime.of(11, 0)
//            ).forEach(System.out::println);

            mealRestController.getAll(LocalDate.of(2015, 05, 13),
                    null,
                    null,
                    null
            ).forEach(System.out::println);

        }
    }
}
