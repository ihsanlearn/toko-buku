package com.example.bookstore.controller.user;

import java.util.List;

import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.User;
import com.example.bookstore.service.TransactionService;
import com.example.bookstore.session.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDateTime;

public class OrdersController {

    @FXML
    private TableView<Transaction> tableOrders;
    @FXML
    private TableColumn<Transaction, Integer> colOrderId;
    @FXML
    private TableColumn<Transaction, Integer> colBook;
    @FXML
    private TableColumn<Transaction, LocalDateTime> colDate;
    @FXML
    private TableColumn<Transaction, String> colStatus;

    private TransactionService transactionService = new TransactionService();

    private List<Transaction> currentUserTransaction;
    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = SessionManager.getCurrentUser();
        currentUserTransaction = transactionService.getTransactionsByUserId(currentUser.getId());

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colBook.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableOrders.getItems().setAll(currentUserTransaction);
    }
}
