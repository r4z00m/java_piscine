DROP TABLE IF EXISTS userTable CASCADE;
CREATE TABLE userTable (identifier SERIAL PRIMARY KEY, name varchar(30) UNIQUE , password TEXT NOT NULL);