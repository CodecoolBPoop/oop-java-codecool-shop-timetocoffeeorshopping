package com.codecool.shop.model;

public class User {
    int id;
    private String name;
    private String password;
    private String email;
    private String sessionId;

    public User(int id, String name, String email, String password, String sessionId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.sessionId = sessionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
