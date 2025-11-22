package com.example.bookstore.repository;

import com.example.bookstore.storage.UserData;
import com.example.bookstore.model.User;
import com.example.bookstore.utils.JsonUtil;

import java.util.List;

public class UserRepository {
    private static final String PATH = "data/users.json";

    public List<User> getAll() {
        UserData data = JsonUtil.load(PATH, UserData.class);
        return data.users;
    }

    public void saveAll(List<User> users) {
        UserData data = new UserData();
        data.users = users;
        JsonUtil.save(data, PATH);
    }

    public int getNextId() {
        List<User> users = getAll();
        if (users == null || users.isEmpty()) {
            return 1;
        }

        int maxId = 0;
        for (User u : users) {
            if (u.getId() > maxId) {
                maxId = u.getId();
            }
        }

        return maxId + 1;
    }
}


