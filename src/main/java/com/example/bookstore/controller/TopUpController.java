package com.example.bookstore.controller;

import com.example.bookstore.App;
import com.example.bookstore.model.User;
import com.example.bookstore.service.UserService;
import com.example.bookstore.session.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TopUpController {

    @FXML
    private TextField balanceField;

    @FXML
    private TextField amountField;

    @FXML
    private Button topupButton;

    @FXML
    private Button withdrawButton;

    private User currentUser;
    private UserService userService = new UserService();

    @FXML
    private void initialize() {
        currentUser = SessionManager.getCurrentUser();

        updateBalanceField();

        topupButton.setOnAction(event -> topUp());
        withdrawButton.setOnAction(event -> withdraw());
    }

    private void topUp() {
        Integer amount = getAmount();
        if (amount == null) return;

        userService.updateBalance(currentUser.getId(), amount);
        currentUser = userService.getUserById(currentUser.getId());

        updateBalanceField();

        showAlert("Success", "Top up berhasil sebesar: Rp " + amount);
    }

    private void withdraw() {
        Integer amount = getAmount();
        if (amount == null) return;

        int currentBalance = currentUser.getBalance();

        if (amount > currentBalance) {
            showAlert("Error", "Balance tidak cukup!");
            return;
        }

        userService.updateBalance(currentUser.getId(), -amount);

        currentUser = userService.getUserById(currentUser.getId());

        updateBalanceField();
        showAlert("Success", "Withdraw berhasil sebesar: Rp " + amount);
    }


    private Integer getAmount() {
        String text = amountField.getText();

        if (text == null || text.isEmpty()) {
            showAlert("Error", "Masukkan jumlah terlebih dahulu.");
            return null;
        }

        try {
            int val = Integer.parseInt(text);
            if (val <= 0) {
                showAlert("Error", "Jumlah harus lebih dari 0.");
                return null;
            }
            return val;
        } catch (NumberFormatException e) {
            showAlert("Error", "Format jumlah tidak valid.");
            return null;
        }
    }

    private void updateBalanceField() {
        balanceField.setText(Integer.toString(currentUser.getBalance()));;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
