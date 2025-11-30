package com.example.bookstore.service;

import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.model.User;
import com.example.bookstore.util.PasswordUtil;

public class AuthService {
    private UserRepository userRepo = new UserRepository();

    public User login(String username, String password) {
        for (User u : userRepo.getAll()) {
            if (u.getUsername().equals(username) && PasswordUtil.verifyPassword(password, u.getPassword())) {
                return u;
            }
        }

        return null;
    }
}
