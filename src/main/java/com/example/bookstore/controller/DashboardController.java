package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.User;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.TransactionService;
import com.example.bookstore.service.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML
    private Label lblTotalBooks;
    @FXML
    private Label lblTotalUsers;
    @FXML
    private Label lblOutOfStock;
    @FXML
    private ListView<String> listRecentActivity;

    private final BookService bookService = new BookService();
    private final UserService userService = new UserService();
    private final TransactionService transactionService = new TransactionService();

    @FXML
    public void initialize() {
        refreshDashboard();
    }

    public void refreshDashboard() {
        List<Book> books = bookService.getAllBooks();
        lblTotalBooks.setText(String.valueOf(books.size()));

        List<User> users = userService.getAllUsers();
        lblTotalUsers.setText(String.valueOf(users.size()));

        long outOfStockCount = books.stream().filter(b -> b.getStock() <= 0).count();
        lblOutOfStock.setText(String.valueOf(outOfStockCount));

        List<Transaction> transactions = transactionService.getAllTransactions();
        List<String> recentActivity = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getPurchaseDate).reversed())
                .limit(10)
                .map(t -> {
                    String date = t.getPurchaseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    String bookTitle = "Unknown Book";
                    Book b = bookService.getById(t.getBookId());
                    if (b != null)
                        bookTitle = b.getTitle();

                    String username = "Unknown User";
                    User u = userService.getUserById(t.getUserId());
                    if (u != null)
                        username = u.getUsername();

                    return String.format("[%s] %s bought '%s' (Qty: %d)", date, username, bookTitle, t.getQuantity());
                })
                .collect(Collectors.toList());

        listRecentActivity.setItems(FXCollections.observableArrayList(recentActivity));
    }
}
