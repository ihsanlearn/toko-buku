package com.example.bookstore.model;

public class User {
    public int id;
    public String username;
    public String password;
    public String role;
    int latestId;

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(int id, String username, String password) {
        this(id, username, password, "user");
    }
}
