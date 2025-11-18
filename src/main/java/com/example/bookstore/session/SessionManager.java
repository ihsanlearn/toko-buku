package com.example.bookstore.session;

import com.example.bookstore.model.User;

public class SessionManager {
    public static User currentUser;

    public static boolean isAdmin() {
        return currentUser != null && currentUser.role.equals("admin");
    }

    public static void setCurrentUser(User user) { currentUser = user; }
    public static User getCurrentUser() { return currentUser; }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }
}

