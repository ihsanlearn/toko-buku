package com.example.bookstore.controller.user;

import java.util.List;

import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.User;
import com.example.bookstore.service.TransactionService;
import com.example.bookstore.session.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OrdersController {

    @FXML private TableView<?> tableOrders;
    @FXML private TableColumn<?, ?> colOrderId;
    @FXML private TableColumn<?, ?> colBook;
    @FXML private TableColumn<?, ?> colDate;
    @FXML private TableColumn<?, ?> colStatus;

    private TransactionService transactionService = new TransactionService();
    
    private List<Transaction> currentUserTransaction;
    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = SessionManager.getCurrentUser();
        currentUserTransaction = transactionService.getTransactionsByUserId(currentUser.getId());

        
    }
}

