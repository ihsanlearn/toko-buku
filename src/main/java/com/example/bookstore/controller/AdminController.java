package com.example.bookstore.controller;

import com.example.bookstore.App;
import com.example.bookstore.session.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class AdminController {
    @FXML private Button btnDashboard;
    @FXML private Button btnManageBooks;
    @FXML private Button btnManageUsers;
    @FXML private Button btnReports;
    @FXML private Button btnLogout;

    @FXML private Label pageTitle;
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        btnLogout.setOnAction(e -> {
          SessionManager.logout();
          try {
            App.setRoot("LoginView");
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        });

        btnDashboard.setOnAction(e -> setPage("Dashboard Overview"));
        btnManageBooks.setOnAction(e -> setPage("Manage Books"));
        btnManageUsers.setOnAction(e -> setPage("Manage Users"));
        btnReports.setOnAction(e -> setPage("Reports"));
    }

    private void setPage(String title) {
        pageTitle.setText(title);
        contentArea.getChildren().setAll(new Label("Page: " + title));
    }
}
