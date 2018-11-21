package com.example.admin.mydailystudy.event;

public class TodoEvent {
    private int type;
    private int position;

    public TodoEvent() {}

    public TodoEvent(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
