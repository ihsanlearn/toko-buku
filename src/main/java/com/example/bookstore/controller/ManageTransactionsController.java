package com.example.bookstore.controller;

import com.example.bookstore.model.Transaction;
import com.example.bookstore.service.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class ManageTransactionsController {

    @FXML
    private TableView<Transaction> tableTransactions;
    @FXML
    private TableColumn<Transaction, Integer> colId;
    @FXML
    private TableColumn<Transaction, Integer> colUserId;
    @FXML
    private TableColumn<Transaction, Integer> colBookId;
    @FXML
    private TableColumn<Transaction, Integer> colQty;
    @FXML
    private TableColumn<Transaction, Double> colTotal;
    @FXML
    private TableColumn<Transaction, String> colStatus;
    @FXML
    private TableColumn<Transaction, LocalDateTime> colDate;

    @FXML
    private ComboBox<String> comboStatus;
    @FXML
    private Button btnUpdateStatus;

    private final TransactionService transactionService = new TransactionService();
    private ObservableList<Transaction> transactionList;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));

        loadTransactions();

        btnUpdateStatus.setOnAction(e -> handleUpdateStatus());
    }

    private void loadTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        transactionList = FXCollections.observableArrayList(transactions);
        tableTransactions.setItems(transactionList);
    }

    private void handleUpdateStatus() {
        Transaction selected = tableTransactions.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Please select a transaction to update.");
            return;
        }

        String newStatus = comboStatus.getValue();
        if (newStatus == null) {
            showAlert("Warning", "Please select a new status.");
            return;
        }

        if (transactionService.updateTransactionStatus(selected.getTransactionId(), newStatus)) {
            selected.setStatus(newStatus);
            tableTransactions.refresh();
            showAlert("Success", "Transaction status updated.");
        } else {
            showAlert("Error", "Failed to update status.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
