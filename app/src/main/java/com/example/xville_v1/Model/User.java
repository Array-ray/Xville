package com.example.xville_v1.Model;

public class User {
    private String username;
    private String email;
    private String ID;
    private String password;


    public User() {
    }

    public User(String username, String email, String ID, String password) {
        this.username = username;
        this.email = email;
        this.ID = ID;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
