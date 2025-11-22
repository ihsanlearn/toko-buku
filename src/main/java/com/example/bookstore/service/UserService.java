package com.example.bookstore.service;

import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;

import java.util.List;

public class UserService {

    private UserRepository repo = new UserRepository();

    public boolean isUsernameTaken(String username) {
        List<User> users = repo.getAll();
        return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public boolean register(String username, String password) {
        List<User> users = repo.getAll();

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }

        int id = repo.getNextId();

        User newUser = new User(id, username, password);

        users.add(newUser);
        repo.saveAll(users);

        return true;
    }
}
