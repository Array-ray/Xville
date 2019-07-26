package com.example.xville_v1.Model;

public class EventToday {
    private String title;
    private String description;
    private int localPos;

    public EventToday() {
    }

    public EventToday(String title, String description, int localPos) {
        this.title = title;
        this.description = description;
        this.localPos = localPos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLocalPos() {
        return localPos;
    }

    public void setLocalPos(int localPos) {
        this.localPos = localPos;
    }
}
