package com.example.xville_v1.Model;

public class Event{

    private String title;
    private String time;
    private String location;
    private String about; //the url where store the pdf
    private String type;
    private String img;
    private String heldbyClub;

//    private ArrayList<String> addbyUser;

    public Event() {
    }

    public Event(String title, String time, String location, String about, String type, String img, String heldbyClub) {

        this.title = title;
        this.time = time;
        this.location = location;
        this.about = about;
        this.type = type;
        this.img = img;
        this.heldbyClub = heldbyClub;
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

    public String getHeldbyClub() {
        return heldbyClub;
    }

    public void setHeldbyClub(String heldbyClub) {
        this.heldbyClub = heldbyClub;
    }
}