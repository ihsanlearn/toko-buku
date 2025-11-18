package com.example.bookstore.service;

import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.model.User;

public class AuthService {
    private UserRepository userRepo = new UserRepository();

    public User login(String username, String password) {
        for (User u : userRepo.getAll()) {
          System.out.println(u);
          if (u.username.equals(username) && u.password.equals(password)) {
              return u;
          }
        }

        return null;
    }
}
