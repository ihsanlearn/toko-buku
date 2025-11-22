package com.example.bookstore.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserProfileContentController {

    @FXML private TextField txtFullName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private TextField txtAddress;

    @FXML private Button btnSaveProfile;

    @FXML
    public void initialize() {
        btnSaveProfile.setOnAction(e -> saveProfile());
    }

    private void saveProfile() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Saved");
        alert.setHeaderText("Profile Updated");
        alert.setContentText("Your profile has been saved successfully.");
        alert.show();
    }
}
