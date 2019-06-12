package com.example.xville_v1;


public class Club{
    private String name;
    private String img;
    private String brief;

    public Club() {
    }

    public Club(String name, String img, String brief) {
        this.name = name;
        this.img = img;
        this.brief = brief;
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
}
