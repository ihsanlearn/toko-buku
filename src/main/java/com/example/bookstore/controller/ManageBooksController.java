package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ManageBooksController {

    @FXML private TableView<Book> tableBooks;

    @FXML private TableColumn<Book, Integer> colId;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colAuthor;
    @FXML private TableColumn<Book, Double> colPrice;
    @FXML private TableColumn<Book, Integer> colStock;
    @FXML private TableColumn<Book, String> colImgPath;

    @FXML private TextField inputTitle;
    @FXML private TextField inputAuthor;
    @FXML private TextField inputPrice;
    @FXML private TextField inputStock;
    @FXML private TextField inputImgPath;

    private ObservableList<Book> bookList;

    private final BookService bookService = new BookService();

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colImgPath.setCellValueFactory(new PropertyValueFactory<>("imgPath"));

        List<Book> books = bookService.getAllBooks();

        bookList = FXCollections.observableArrayList(books);
        tableBooks.setItems(bookList);

        tableBooks.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> fillForm(newVal));
    }

    private void fillForm(Book b) {
        if (b == null) return;
        inputTitle.setText(b.getTitle());
        inputAuthor.setText(b.getAuthor());
        inputPrice.setText(String.valueOf(b.getPrice()));
        inputStock.setText(String.valueOf(b.getStock()));
        inputImgPath.setText(b.getImgPath());
    }

    @FXML private void handleAdd() {
        try {
            String title = inputTitle.getText();
            String author = inputAuthor.getText();
            int price = Integer.parseInt(inputPrice.getText());
            int stock = Integer.parseInt(inputStock.getText());
            String imgPath = inputImgPath.getText();

            Book newBook = new Book(title, author, price, stock, imgPath);

            bookService.addBook(newBook);;
            bookList.add(newBook);

            showAlert("Success", "sipp buku dah ditambahin syg.");
        } catch (Exception e) {
            showAlert("Error", "buku gagal ditambahin syg");
        }
    }

    @FXML
    private void handleUpdate() {
        Book selected = tableBooks.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "pilih buku buat diupdate");
            return;
        }

        try {
            selected.setTitle(inputTitle.getText());
            selected.setAuthor(inputAuthor.getText());
            selected.setPrice(Integer.parseInt(inputPrice.getText()));
            selected.setStock(Integer.parseInt(inputStock.getText()));
            selected.setImgPath(inputImgPath.getText());

            bookService.updateBook(selected);

            tableBooks.refresh();
            clearForm();

            showAlert("Success", "buku berhasil diupdate ygy");

        } catch (Exception e) {
            showAlert("Error", "yang bener ajg: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Book selected = tableBooks.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "pilih buku yang mau dihapus");
            return;
        }

        bookService.deleteBook(selected.getId());

        bookList.remove(selected);
        clearForm();

        showAlert("Success", "Book deleted successfully!");
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    private void clearForm() {
        inputTitle.clear();
        inputAuthor.clear();
        inputPrice.clear();
        inputStock.clear();
        inputImgPath.clear();
        tableBooks.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }


}
