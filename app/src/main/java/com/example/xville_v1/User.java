package com.example.xville_v1;

public class User {
    private String Username;
    private String Email;
    private String ID;
    private String password;


    public User() {
    }

    public User(String username, String email, String ID, String password) {
        Username = username;
        Email = email;
        this.ID = ID;
        this.password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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
