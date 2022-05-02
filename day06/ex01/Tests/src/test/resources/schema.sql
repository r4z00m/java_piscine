drop table if exists PRODUCTTABLE cascade;
CREATE TABLE productTable (id INTEGER IDENTITY PRIMARY KEY , name varchar(30), price INTEGER NOT NULL);