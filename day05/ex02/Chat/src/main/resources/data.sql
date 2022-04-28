INSERT INTO users (login, password) VALUES ('User1', 'password1');
INSERT INTO users (login, password) VALUES ('User2', 'password2');
INSERT INTO users (login, password) VALUES ('User3', 'password3');
INSERT INTO users (login, password) VALUES ('User4', 'password4');
INSERT INTO users (login, password) VALUES ('User5', 'password5');

INSERT INTO chatrooms (name, owner) VALUES ('room1', 1);
INSERT INTO chatrooms (name, owner) VALUES ('room2', 2);
INSERT INTO chatrooms (name, owner) VALUES ('room3', 3);
INSERT INTO chatrooms (name, owner) VALUES ('room4', 4);
INSERT INTO chatrooms (name, owner) VALUES ('room5', 5);

INSERT INTO messages (author, room, text, time) VALUES (1, 1, 'message1', current_timestamp);
INSERT INTO messages (author, room, text, time) VALUES (2, 2, 'message2', current_timestamp);
INSERT INTO messages (author, room, text, time) VALUES (3, 3, 'message3', current_timestamp);
INSERT INTO messages (author, room, text, time) VALUES (4, 4, 'message4', current_timestamp);
INSERT INTO messages (author, room, text, time) VALUES (5, 5, 'message5', current_timestamp);


