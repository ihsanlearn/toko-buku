package com.example.bookstore.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController {

    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;

    @FXML private PasswordField txtOldPassword;
    @FXML private PasswordField txtNewPassword;
    @FXML private PasswordField txtConfirmPassword;

    @FXML
    public void initialize() {
        // Load data user dari database nanti di sini
    }

    @FXML
    private void handleSave() {
        // Logic menyimpan perubahan user
        // Validasi password
        // Update database
    }
}
