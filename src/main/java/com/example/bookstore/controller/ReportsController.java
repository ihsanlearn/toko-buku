package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Transaction;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.TransactionService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportsController {

    @FXML
    private ComboBox<String> comboReportType;
    @FXML
    private Button btnGenerate;
    @FXML
    private BarChart<String, Number> chartReport;

    private final TransactionService transactionService = new TransactionService();
    private final BookService bookService = new BookService();

    @FXML
    public void initialize() {
        btnGenerate.setOnAction(e -> generateReport());
    }

    private void generateReport() {
        String type = comboReportType.getValue();
        if (type == null)
            return;

        chartReport.getData().clear();

        switch (type) {
            case "Top Selling Books":
                generateTopSellingReport();
                break;
            case "Low Stock Books":
                generateLowStockReport();
                break;
            default:
                break;
        }
    }

    private void generateTopSellingReport() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        Map<Integer, Integer> bookSales = new HashMap<>();

        for (Transaction t : transactions) {
            bookSales.put(t.getBookId(), bookSales.getOrDefault(t.getBookId(), 0) + t.getQuantity());
        }

        List<Map.Entry<Integer, Integer>> topSelling = bookSales.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .collect(Collectors.toList());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top Selling Books");

        for (Map.Entry<Integer, Integer> entry : topSelling) {
            Book book = bookService.getById(entry.getKey());
            if (book != null) {
                series.getData().add(new XYChart.Data<>(book.getTitle(), entry.getValue()));
            }
        }

        chartReport.getData().add(series);
    }

    private void generateLowStockReport() {
        List<Book> books = bookService.getAllBooks();
        List<Book> lowStockBooks = books.stream()
                .filter(b -> b.getStock() < 5)
                .collect(Collectors.toList());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Low Stock Books");

        for (Book b : lowStockBooks) {
            series.getData().add(new XYChart.Data<>(b.getTitle(), b.getStock()));
        }

        chartReport.getData().add(series);
    }
}
