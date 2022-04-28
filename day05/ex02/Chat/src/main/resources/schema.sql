drop table if exists messages cascade;
drop table if exists chatrooms cascade;
drop table if exists users cascade;
CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, login varchar(30) UNIQUE NOT NULL, password varchar(30));
CREATE TABLE IF NOT EXISTS chatRooms (id SERIAL PRIMARY KEY, name varchar(30) UNIQUE NOT NULL, owner INTEGER REFERENCES users(id) NOT NULL);
CREATE TABLE IF NOT EXISTS messages (id SERIAL PRIMARY KEY, author INTEGER REFERENCES users(id) NOT NULL, room INTEGER REFERENCES chatRooms(id) NOT NULL, text TEXT NOT NULL, time TIMESTAMP NOT NULL);