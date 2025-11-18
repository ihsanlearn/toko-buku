package com.example.bookstore.controller;

import com.example.bookstore.App;
import com.example.bookstore.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerBtn;

    @FXML
    private Hyperlink backToLogin;

    private UserService userService = new UserService();

    @FXML
    public void initialize() {

        // Event tombol daftar
        registerBtn.setOnAction(e -> handleRegister(e));

        // Event kembali ke login
        backToLogin.setOnAction(e -> {
            try {
                App.setRoot("loginView");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirm = confirmPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Semua field wajib diisi!");
            return;
        }

        if (!password.equals(confirm)) {
            showAlert(Alert.AlertType.ERROR, "Password tidak sama!");
            return;
        }

        if (userService.isUsernameTaken(username)) {
            showAlert(Alert.AlertType.ERROR, "Username sudah digunakan!");
            return;
        }

        userService.register(username, password);

        showAlert(Alert.AlertType.INFORMATION, "Akun berhasil dibuat!");
        goToLogin();
    }

    private void goToLogin() {
        try {
            App.setRoot("LoginView");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
