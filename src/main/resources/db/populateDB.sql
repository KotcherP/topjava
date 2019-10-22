DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, '2019-10-19 10:00:00', 'завтрак', 500),
       (100000, '2019-10-19 12:00:00', 'обед', 1000),
       (100000,'2019-10-19 14:00:00', 'ужин', 500),
       (100001,'2019-10-20 11:00:00', 'завтрак', 500),
       (100001,'2019-10-20 13:00:00', 'обед', 1000),
       (100001,'2019-10-20 15:00:00', 'ужин', 510),
       (100001,'2019-10-21 11:00:00', 'завтрак', 600);
