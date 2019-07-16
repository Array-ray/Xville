package com.example.xville_v1.Model;


public class Club{
    private String name;
    private String img;
    private String brief;
    private String password;

    //constructor
    public Club() {
    }

    public Club(String name, String password){
        this.name = name;
        this.password = password;
    }

    public Club(String name, String img, String brief) {
        this.name = name;
        this.img = img;
        this.brief = brief;
    }

    public Club(String name, String img, String brief, String password) {
        this.name = name;
        this.img = img;
        this.brief = brief;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
