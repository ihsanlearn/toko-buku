package com.example.bookstore.service;

import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;

import java.util.List;

public class UserService {

    private UserRepository repo = new UserRepository();

    public boolean isUsernameTaken(String username) {
        List<User> users = repo.getAll();
        return users.stream().anyMatch(u -> u.username.equals(username));
    }

    public boolean register(String username, String password) {
        List<User> users = repo.getAll();

        // cek username duplikat
        for (User u : users) {
            if (u.username.equalsIgnoreCase(username)) {
                return false;
            }
        }

        // generate id
        int id = repo.getNextId();

        // create user baru
        User newUser = new User(id, username, password);

        // simpan
        users.add(newUser);
        repo.saveAll(users);

        return true;
    }
}
