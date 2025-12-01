package com.example.bookstore.controller.user;

import com.example.bookstore.model.User;
import com.example.bookstore.service.UserService;
import com.example.bookstore.session.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class UserProfileContentController {

    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtPostalCode;

    @FXML
    private Button btnSaveProfile;
    @FXML
    private ChoiceBox<String> cbFavoriteGenre;

    private UserService userService = new UserService();
    private User currentUser;

    @FXML
    public void initialize() {

        currentUser = SessionManager.getCurrentUser();

        txtFullName.setText(currentUser.getFullName());
        txtUsername.setText(currentUser.getUsername());
        txtEmail.setText(currentUser.getEmail());
        txtPhone.setText(currentUser.getPhone());

        if (!currentUser.getDeliveryAddresses().isEmpty()) {
            com.example.bookstore.model.DeliveryAddress addr = currentUser.getDeliveryAddresses().get(0);
            txtAddress.setText(addr.getAddress());
            txtCity.setText(addr.getCity());
            txtPostalCode.setText(addr.getPostalCode());
        } else {
            txtAddress.setText("");
            txtCity.setText("");
            txtPostalCode.setText("");
        }

        cbFavoriteGenre.getItems().addAll(
                "Fiction", "Romance", "Horror", "Sci-Fi",
                "Fantasy", "Mystery", "Non-fiction");

        cbFavoriteGenre.setValue(
                currentUser.getFavoriteGenre() != null ? currentUser.getFavoriteGenre() : "Fiction");

        btnSaveProfile.setOnAction(e -> saveProfile());
    }

    private void saveProfile() {
        String fullName = txtFullName.getText();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String postalCode = txtPostalCode.getText();
        String favoriteGenre = cbFavoriteGenre.getValue();

        boolean success = userService.update(
                currentUser.getId(),
                fullName,
                username,
                email,
                phone,
                favoriteGenre);

        if (!success) {
            showError("Update gagal! Username atau email mungkin sudah dipakai.");
            return;
        }

        currentUser.setFullName(fullName);
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setFavoriteGenre(favoriteGenre);

        // Update first address if fields are not empty
        if (!address.isEmpty() || !city.isEmpty() || !postalCode.isEmpty()) {
            if (currentUser.getDeliveryAddresses().isEmpty()) {
                currentUser.addDeliveryAddress(
                        new com.example.bookstore.model.DeliveryAddress("Main", address, city, postalCode));
            } else {
                com.example.bookstore.model.DeliveryAddress addr = currentUser.getDeliveryAddresses().get(0);
                addr.setAddress(address);
                addr.setCity(city);
                addr.setPostalCode(postalCode);
            }
            userService.saveUser(currentUser);
        }

        SessionManager.setCurrentUser(currentUser);

        showSuccess("Your profile has been updated successfully.");
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.show();
    }

    private void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Success");
        alert.setContentText(msg);
        alert.show();
    }
}
