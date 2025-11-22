package com.example.bookstore.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OrdersController {

    @FXML private TableView<?> tableOrders;
    @FXML private TableColumn<?, ?> colOrderId;
    @FXML private TableColumn<?, ?> colBook;
    @FXML private TableColumn<?, ?> colDate;
    @FXML private TableColumn<?, ?> colStatus;

    @FXML
    public void initialize() {
        // Anda bisa isi ini nanti untuk load data pesanan user dari DB
        // Contoh:
        // colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
    }
}

