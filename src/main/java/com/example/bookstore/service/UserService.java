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

    public boolean update(
        int id,
        String fullName,
        String username,
        String email,
        String phone,
        String address,
        String city,
        String postalCode,
        String favoriteGenre
    ) {
        List<User> users = repo.getAll();

        boolean usernameUsed = users.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username) && u.getId() != id);

        if (usernameUsed) {
            System.out.println("username dah digunakan");
            return false;
        }

        boolean emailUsed = users.stream()
        .anyMatch(u -> email != null &&
                u.getEmail() != null &&
                u.getEmail().equalsIgnoreCase(email) &&
                u.getId() != id);

        if (emailUsed) {
            System.out.println("email dah digunakan");
            return false;
        }

        for (User u : users) {
            if (u.getId() == id) {

                u.setFullName(fullName);
                u.setUsername(username);
                u.setEmail(email);
                u.setPhone(phone);
                u.setAddress(address);
                u.setCity(city);
                u.setPostalCode(postalCode);
                u.setFavoriteGenre(favoriteGenre);

                repo.saveAll(users);
                return true;
            }
        }

        return false;
    }

}
