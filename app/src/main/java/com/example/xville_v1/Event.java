package com.example.xville_v1;
import java.util.Date;

public class Event {
    private  String title;
    private  String description;
    private int localPos;
    private String evClub;
    private Date evDate;
    private String evID;
    private String evLocation;
    private String evName;
    private String poster;
    private String tag;

    public Event() {
    }

    public Event(String title, String description, int localPos) {
        this.title = title;
        this.description = description;
        this.localPos = localPos;
    }

    public Event(String title, String description, int localPos, String evClub, Date evDate, String evID, String evLocation, String evName, String poster, String tag) {
        this.title = title;
        this.description = description;
        this.localPos = localPos;
        this.evClub = evClub;
        this.evDate = evDate;
        this.evID = evID;
        this.evLocation = evLocation;
        this.evName = evName;
        this.poster = poster;
        this.tag = tag;
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

    public String getEvClub() {
        return evClub;
    }

    public void setEvClub(String evClub) {
        this.evClub = evClub;
    }

    public Date getEvDate() {
        return evDate;
    }

    public void setEvDate(Date evDate) {
        this.evDate = evDate;
    }

    public String getEvID() {
        return evID;
    }

    public void setEvID(String evID) {
        this.evID = evID;
    }

    public String getEvLocation() {
        return evLocation;
    }

    public void setEvLocation(String evLocation) {
        this.evLocation = evLocation;
    }

    public String getEvName() {
        return evName;
    }

    public void setEvName(String evName) {
        this.evName = evName;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

