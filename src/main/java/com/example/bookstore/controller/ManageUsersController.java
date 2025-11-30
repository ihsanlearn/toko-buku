package com.example.bookstore.controller;

import com.example.bookstore.model.User;
import com.example.bookstore.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ManageUsersController {

    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, Integer> colUserId;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TextField detailId;
    @FXML
    private TextField detailUsername;
    @FXML
    private PasswordField detailPassword;
    @FXML
    private ComboBox<String> detailRole;

    @FXML
    private Button btnUpdateUser;
    @FXML
    private Button btnDeleteUser;

    private UserService userService = new UserService();
    private ObservableList<User> userList;

    @FXML
    public void initialize() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadUsers();

        tableUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                fillForm(newVal);
            }
        });

        btnUpdateUser.setOnAction(e -> handleUpdate());
        btnDeleteUser.setOnAction(e -> handleDelete());
    }

    private void loadUsers() {
        List<User> users = userService.getAllUsers();
        userList = FXCollections.observableArrayList(users);
        tableUsers.setItems(userList);
    }

    private void fillForm(User user) {
        detailId.setText(String.valueOf(user.getId()));
        detailUsername.setText(user.getUsername());
        detailPassword.setText(user.getPassword());
        detailRole.setValue(user.getRole());
    }

    private void handleUpdate() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Please select a user to update.");
            return;
        }

        try {
            int id = Integer.parseInt(detailId.getText());
            String username = detailUsername.getText();
            String password = detailPassword.getText();
            String role = detailRole.getValue();

            if (username.isEmpty() || role == null) {
                showAlert("Error", "Username and Role cannot be empty.");
                return;
            }

            selected.setUsername(username);
            selected.setPassword(password);
            selected.setRole(role);

            userService.updateUserCredentials(id, username, password, role);

            tableUsers.refresh();
            showAlert("Success", "User updated successfully.");

        } catch (Exception e) {
            showAlert("Error", "Failed to update user: " + e.getMessage());
        }
    }

    private void handleDelete() {
        User selected = tableUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Please select a user to delete.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete User");
        alert.setHeaderText("Are you sure you want to delete user " + selected.getUsername() + "?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            if (userService.deleteUser(selected.getId())) {
                userList.remove(selected);
                clearForm();
                showAlert("Success", "User deleted successfully.");
            } else {
                showAlert("Error", "Failed to delete user.");
            }
        }
    }

    private void clearForm() {
        detailId.clear();
        detailUsername.clear();
        detailPassword.clear();
        detailRole.setValue(null);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
