package com.example.bookstore.controller;

import com.example.bookstore.App;
import com.example.bookstore.session.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class AdminController {
  @FXML
  private Button btnDashboard;
  @FXML
  private Button btnManageBooks;
  @FXML
  private Button btnManageUsers;
  @FXML
  private Button btnManageTransactions;
  @FXML
  private Button btnReports;
  @FXML
  private Button btnLogout;
  @FXML
  private Label pageTitle;
  @FXML
  private StackPane contentArea;

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

    setPage("Dashboard", "Dashboard Overview");
    btnDashboard.setOnAction(e -> setPage("Dashboard", "Dashboard Overview"));
    btnManageBooks.setOnAction(e -> setPage("ManageBooks", "Manage Books"));
    btnManageUsers.setOnAction(e -> setPage("ManageUsers", "Manage Users"));
    btnManageTransactions.setOnAction(e -> setPage("ManageTransactions", "Manage Transactions"));
    btnReports.setOnAction(e -> setPage("Reports", "Reports"));
  }

  private void setPage(String fxmlName, String title) {
    try {
      pageTitle.setText(title);
      Parent view = FXMLLoader.load(App.class.getResource("/com/example/bookstore/adminView/" + fxmlName + ".fxml"));
      contentArea.getChildren().setAll(view);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
