package com.example.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.bookstore.App;

public class LoginController {

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
    public void initialize() {
        loginBtn.setOnAction(arg0 -> {
            try {
                handleLogin(arg0);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    // ---------------------
    // HANDLE LOGIN
    // ---------------------
    private void handleLogin(ActionEvent event) throws Exception {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        // Validasi input kosong
        if (user.isEmpty() || pass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Username dan password wajib diisi!");
            alert.show();
            return;
        }

        // Login dummy
        if (user.equals("admin") && pass.equals("123")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Berhasil");
            alert.setHeaderText(null);
            alert.setContentText("Selamat datang, " + user + "!");
            alert.showAndWait();

            goToMainPage(event); // pindah scene

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Gagal");
            alert.setHeaderText(null);
            alert.setContentText("Username atau password salah!");
            alert.show();
        }
    }

    // ---------------------
    // METHOD UNTUK GANTI SCENE
    // ---------------------
    private void goToMainPage(ActionEvent event) throws Exception {
        try {
            MainController controller = App.setRootWithController("MainView");
            controller.setAdminMode();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
