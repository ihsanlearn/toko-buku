package com.example.bookstore.controller.user;

import com.example.bookstore.model.User;
import com.example.bookstore.session.SessionManager;
import com.example.bookstore.util.PasswordUtil;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import com.example.bookstore.service.UserService;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class SettingsController {

    private UserService userService = new UserService();

    @FXML
    private Text lblUsername;
    @FXML
    private Text lblEmail;
    @FXML
    private Text lblPhone;
    @FXML
    private Text lblAddress;

    User currentUser = SessionManager.getCurrentUser();

    @FXML
    public void initialize() {
        if (currentUser != null) {
            lblUsername.setText(currentUser.getUsername());

            setLabelText(lblEmail, currentUser.getEmail(), "email");
            setLabelText(lblPhone, currentUser.getPhone(), "phone");

            String addressDisplay = "(address not already set)";
            if (!currentUser.getDeliveryAddresses().isEmpty()) {
                addressDisplay = currentUser.getDeliveryAddresses().get(0).toString();
                lblAddress.setText(addressDisplay);
                lblAddress.setStyle("-fx-fill: #333333; -fx-font-style: normal;");
            } else {
                lblAddress.setText(addressDisplay);
                lblAddress.setStyle("-fx-fill: #999999; -fx-font-style: italic;");
            }
        }
    }

    private void setLabelText(Text label, String value, String type) {
        if (value != null && !value.isEmpty()) {
            label.setText(value);
            label.setStyle("-fx-fill: #333333; -fx-font-style: normal;");
        } else {
            label.setText("(" + type + " not already set)");
            label.setStyle("-fx-fill: #999999; -fx-font-style: italic;");
        }
    }

    @FXML
    private void handleEditUsername() {
        TextInputDialog dialog = new TextInputDialog(currentUser.getUsername());
        dialog.setTitle("Edit Username");
        dialog.setHeaderText("Enter new username:");
        dialog.setContentText("Username:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newUsername -> {
            if (!newUsername.equals(currentUser.getUsername())) {
                boolean success = userService.update(
                        currentUser.getId(),
                        currentUser.getFullName(),
                        newUsername,
                        currentUser.getEmail(),
                        currentUser.getPhone(),
                        currentUser.getFavoriteGenre());

                if (success) {
                    currentUser.setUsername(newUsername);
                    lblUsername.setText(newUsername);
                    showAlert("Success", "Username updated successfully.");
                } else {
                    showAlert("Error", "Username already taken or invalid.");
                }
            }
        });
    }

    @FXML
    private void handleEditEmail() {
        TextInputDialog dialog = new TextInputDialog(currentUser.getEmail());
        dialog.setTitle("Edit Email");
        dialog.setHeaderText("Enter new email:");
        dialog.setContentText("Email:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newEmail -> {
            boolean success = userService.update(
                    currentUser.getId(),
                    currentUser.getFullName(),
                    currentUser.getUsername(),
                    newEmail,
                    currentUser.getPhone(),
                    currentUser.getFavoriteGenre());

            if (success) {
                currentUser.setEmail(newEmail);
                setLabelText(lblEmail, newEmail, "email");
                showAlert("Success", "Email updated successfully.");
            } else {
                showAlert("Error", "Email update failed.");
            }
        });
    }

    @FXML
    private void handleEditPhone() {
        TextInputDialog dialog = new TextInputDialog(currentUser.getPhone());
        dialog.setTitle("Edit Phone");
        dialog.setHeaderText("Enter new phone number:");
        dialog.setContentText("Phone:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPhone -> {
            boolean success = userService.update(
                    currentUser.getId(),
                    currentUser.getFullName(),
                    currentUser.getUsername(),
                    currentUser.getEmail(),
                    newPhone,
                    currentUser.getFavoriteGenre());

            if (success) {
                currentUser.setPhone(newPhone);
                setLabelText(lblPhone, newPhone, "phone");
                showAlert("Success", "Phone updated successfully.");
            } else {
                showAlert("Error", "Phone update failed.");
            }
        });
    }

    @FXML
    private void handleEditAddress() {
        // For now, just show a message that addresses are managed in checkout or need a
        // specific UI
        // Or implement a simple add/edit for the first address
        showAlert("Info", "Please manage your delivery addresses during checkout.");
    }

    @FXML
    private void handleEditPassword() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Enter new password:");
        dialog.setContentText("Password:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPassword -> {
            if (!newPassword.isEmpty()) {
                boolean success = userService.updateUserCredentials(
                        currentUser.getId(),
                        currentUser.getUsername(),
                        newPassword,
                        currentUser.getRole());

                if (success) {
                    currentUser.setPassword(PasswordUtil.hashPassword(newPassword));
                    showAlert("Success", "Password updated successfully.");
                } else {
                    showAlert("Error", "Password update failed.");
                }
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
