package com.example.bookstore.controller.user;

import com.example.bookstore.model.User;
import com.example.bookstore.session.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController {

    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;

    @FXML private PasswordField txtOldPassword;
    @FXML private PasswordField txtNewPassword;
    @FXML private PasswordField txtConfirmPassword;

    User currentUser = SessionManager.getCurrentUser();

    @FXML public void initialize() {
        txtUsername.setText(currentUser.getUsername());
        txtEmail.setText(currentUser.getUsername() + "@gmail.com");
        txtOldPassword.setText(currentUser.getPassword());
    }

    @FXML
    private void handleSave() {
        
    }
}
