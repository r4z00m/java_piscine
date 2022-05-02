DROP TABLE IF EXISTS userTable CASCADE;
CREATE TABLE userTable (identifier SERIAL PRIMARY KEY, email varchar(100));
INSERT INTO userTable (email) VALUES ('example@yandex.ru');
INSERT INTO userTable (email) VALUES ('example@mail.ru');
INSERT INTO userTable (email) VALUES ('example@gmail.com');