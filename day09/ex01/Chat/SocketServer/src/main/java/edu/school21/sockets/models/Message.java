package edu.school21.sockets.models;

import java.sql.Timestamp;

public class Message {

    private int id;
    private String text;
    private Timestamp time;

    public Message() {
    }

    public Message(String text, Timestamp time) {
        this.text = text;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
