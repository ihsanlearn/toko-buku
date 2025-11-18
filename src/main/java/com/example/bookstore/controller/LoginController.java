package com.example.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.bookstore.App;
import com.example.bookstore.service.AuthService;
import com.example.bookstore.session.SessionManager;
import com.example.bookstore.model.User;

public class LoginController {

    private AuthService authService = new AuthService();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button btnBack;

    @FXML
    private Label messageLabel;

    @FXML
    private Hyperlink goToRegisterLink;

    @FXML
    public void initialize() {
        loginBtn.setOnAction(arg0 -> {
            try {
                handleLogin(arg0);
            } catch (Exception e) {
                // TODO Auto-generated catch block
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

    // ---------------------
    // HANDLE LOGIN
    // ---------------------
    private void handleLogin(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validasi input kosong
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
            if (loggedInUser != null && "admin".equals(loggedInUser.role)) {
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

    // ---------------------
    // METHOD UNTUK GANTI SCENE
    // ---------------------
    private void goToMainPage(ActionEvent event) throws Exception {
        try {
            MainController controller = App.setRootWithController("MainView");
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
