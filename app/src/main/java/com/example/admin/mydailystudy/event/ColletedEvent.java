package com.example.admin.mydailystudy.event;

public class ColletedEvent {
    private boolean isCollected;
    private int id;

    public ColletedEvent(int id, boolean isCollected) {
        this.id = id;
        this.isCollected = isCollected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
