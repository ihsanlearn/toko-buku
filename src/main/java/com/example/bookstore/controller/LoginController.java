package com.example.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.example.bookstore.App;
import com.example.bookstore.service.AuthService;
import com.example.bookstore.session.SessionManager;
import com.example.bookstore.model.User;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginBtn;
    @FXML private Button btnBack;
    @FXML private Label messageLabel;
    @FXML private Hyperlink goToRegisterLink;
    
    private AuthService authService = new AuthService();

    @FXML public void initialize() {
        loginBtn.setOnAction(arg0 -> {
            try {
                handleLogin(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        goToRegisterLink.setOnAction(e -> {
            try {
                App.switchTo("RegisterView");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void handleLogin(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Username dan password wajib diisi!");
            alert.show();
            return;
        }

        User loggedInUser = authService.login(username, password);

        if (loggedInUser != null) {
            SessionManager.setCurrentUser(loggedInUser);
            if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
                try {
                    App.setRoot("AdminDashboard");
                    return;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            goToMainPage(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("username atau password salah");
            alert.show();
        }
    }

    private void goToMainPage(ActionEvent event) throws Exception {
        try {
            App.setRoot("MainView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
