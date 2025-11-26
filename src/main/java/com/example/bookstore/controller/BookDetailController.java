package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import com.example.bookstore.session.SessionManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class BookDetailController {

    @FXML private ImageView bookImage;
    @FXML private Label titleLabel;
    @FXML private Label authorLabel;
    @FXML private Label publisherLabel;
    @FXML private Label priceLabel;
    @FXML private Label stockLabel;
    @FXML private TextFlow descriptionFlow;
    @FXML private TextArea descriptionArea;
    @FXML private Label isbnLabel;
    @FXML private Button addCartBtn;
    @FXML private Button buyNowBtn;
    @FXML private Button closeBtn;

    private User currentUser;
    private Book selectedBook;

    public void setBookData(Book book) {

        this.selectedBook = book;
        currentUser = SessionManager.getCurrentUser();

        titleLabel.setText(book.getTitle());
        authorLabel.setText("Penulis: " + book.getAuthor());
        priceLabel.setText("Rp " + String.format("%,d", book.getPrice()).replace(',', '.'));
        stockLabel.setText(book.getStock() > 0 ? "Stok: " + book.getStock() : "Habis");
        isbnLabel.setText("ISBN: " + book.getId());

        try {
            Image img = book.getImgPath() != null
                    ? new Image(getClass().getResourceAsStream("/" + book.getImgPath()))
                    : new Image(getClass().getResourceAsStream("/com/example/bookstore/images/sample.jpeg"));
            bookImage.setImage(img);
        } catch (Exception e) {
            bookImage.setImage(new Image(getClass().getResourceAsStream("/com/example/bookstore/images/sample.jpeg")));
        }

        buyNowBtn.setOnAction(ev -> handleBuyNow());
        closeBtn.setOnAction(ev -> closeWindow());
    }

    private void handleBuyNow() {
        currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.WARNING, "Auth Required", "Anda harus login untuk melakukan pembelian");
            return;
        }

        if (selectedBook.getStock() <= 0) {
            showAlert(Alert.AlertType.WARNING, "Stok Habis", "Stok buku tidak tersedia");
            return;
        }

        TextInputDialog quantityDialog = new TextInputDialog();
        quantityDialog.setTitle("Jumlah Pembelian");
        quantityDialog.setHeaderText("Masukkan jumlah buku");
        quantityDialog.setContentText("Jumlah:");

        String qtyText = quantityDialog.showAndWait().orElse(null);
        if (qtyText == null) return;

        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Tidak Valid", "Jumlah harus berupa angka positif");
            return;
        }

        redirectToCheckout(selectedBook, quantity);
    }

    private void redirectToCheckout(Book book, int qty) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookstore/CheckoutView.fxml"));
            Parent root = loader.load();

            CheckoutController controller = loader.getController();
            controller.setOrderData(book, qty);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Checkout");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Gagal membuka halaman Checkout", e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
