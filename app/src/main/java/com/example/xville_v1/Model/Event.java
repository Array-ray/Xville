package com.example.xville_v1.Model;

import java.util.ArrayList;

public class Event{
    private String eventid;
    private String title;
    private String time;
    private String location;
    private String about;
    private String type;
    private String img;

    private ArrayList<String> heldbyClub;
    private ArrayList<String> addbyUser;

    public Event() {
    }

    public Event(String eventid, String title, String time, String location, String about, String type, String img, ArrayList<String> heldbyClub, ArrayList<String> addbyUser) {
        this.eventid = eventid;
        this.title = title;
        this.time = time;
        this.location = location;
        this.about = about;
        this.type = type;
        this.img = img;
        this.heldbyClub = heldbyClub;
        this.addbyUser = addbyUser;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ArrayList<String> getHeldbyClub() {
        return heldbyClub;
    }

    public void setHeldbyClub(ArrayList<String> heldbyClub) {
        this.heldbyClub = heldbyClub;
    }

    public ArrayList<String> getAddbyUser() {
        return addbyUser;
    }

    public void setAddbyUser(ArrayList<String> addbyUser) {
        this.addbyUser = addbyUser;
    }

    public void addedByUser(String userid){
        addbyUser.add(userid);
    }
}