package edu.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {

    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private Timestamp dateTime;

    public Message(Long id, User author, Chatroom room, String text, Timestamp timestamp) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.dateTime = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(author, message.author) && Objects.equals(room, message.room) && Objects.equals(text, message.text) && Objects.equals(dateTime, message.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, dateTime);
    }

    @Override
    public String toString() {
        return "Message : {\n" +
                "id=" + id +
                ", \nauthor=" + author +
                ", \nroom=" + room +
                ", \ntext='" + text + '\'' +
                ", \ndateTime=" + dateTime + "\n" +
                '}';
    }
}
