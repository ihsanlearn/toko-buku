package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String> titleCol;
    @FXML private TableColumn<Book, String> authorCol;
    @FXML private TableColumn<Book, Double> priceCol;

    private final BookService bookService = new BookService();

    @FXML
    public void initialize() {
        titleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        authorCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAuthor()));
        priceCol.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPrice()));
        refreshTable();
    }

    private void refreshTable() {
        ObservableList<Book> list = FXCollections.observableArrayList(bookService.getBooks());
        bookTable.setItems(list);
    }

    @FXML
    private void onAddBook() {
        // nanti diarahkan ke form tambah buku (add_book.fxml)
        System.out.println("Tambah buku diklik!");
    }
}
