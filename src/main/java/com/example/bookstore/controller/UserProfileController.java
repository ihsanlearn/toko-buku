package com.example.bookstore.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import java.io.IOException;

import com.example.bookstore.App;

public class UserProfileController {

    @FXML private StackPane contentArea;
    @FXML private Label pageTitle;

    @FXML private Button btnProfile;
    @FXML private Button btnOrders;
    @FXML private Button btnSettings;
    @FXML private Button btnLogout;

    @FXML
    public void initialize() {
        btnProfile.setOnAction(e -> setPage("UserProfileContent.fxml", "User Profile"));
        btnOrders.setOnAction(e -> setPage("UserOrders.fxml", "My Orders"));
        btnSettings.setOnAction(e -> setPage("UserSettings.fxml", "Settings"));
        btnLogout.setOnAction(e -> backButton());

        setPage("UserProfileContent.fxml", "User Profile");
    }

    private void setPage(String fileName, String title) {
      try {
          FXMLLoader loader = new FXMLLoader(
                  getClass().getResource("/com/example/bookstore/UserProfileView/" + fileName)
          );

          Node view = loader.load();

          contentArea.getChildren().setAll(view);
          pageTitle.setText(title);

      } catch (IOException e) {
          e.printStackTrace();
      }
    }

    private void backButton(){
        try {
            App.setRoot("MainView");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}