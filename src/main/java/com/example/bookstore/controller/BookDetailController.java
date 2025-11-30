package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import com.example.bookstore.session.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class BookDetailController {

    @FXML
    private ImageView bookImage;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label publisherLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Text originalPriceLabel;
    @FXML
    private Label stockLabel;
    @FXML
    private TextFlow descriptionFlow;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Label isbnLabel;
    @FXML
    private Button addCartBtn;
    @FXML
    private Button buyNowBtn;
    @FXML
    private Button closeBtn;

    private User currentUser;
    private Book selectedBook;

    public void setBookData(Book book) {

        this.selectedBook = book;
        currentUser = SessionManager.getCurrentUser();

        titleLabel.setText(book.getTitle());
        authorLabel.setText("Penulis: " + book.getAuthor());

        if (book.getDiscount() > 0) {
            double discountedPrice = book.getPrice() * (100 - book.getDiscount()) / 100.0;
            priceLabel.setText("Rp " + String.format("%,d", (int) discountedPrice).replace(',', '.'));

            originalPriceLabel.setText("Rp " + String.format("%,d", book.getPrice()).replace(',', '.'));
            originalPriceLabel.setVisible(true);
            originalPriceLabel.setManaged(true);
            originalPriceLabel.getStyleClass().add("original-price");
        } else {
            priceLabel.setText("Rp " + String.format("%,d", book.getPrice()).replace(',', '.'));
            originalPriceLabel.setVisible(false);
            originalPriceLabel.setManaged(false);
        }

        String desc = book.getDescription();
        if (desc == null || desc.isEmpty()) {
            desc = "Tidak ada deskripsi untuk buku ini.";
        }
        descriptionArea.setText(desc);

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
        if (qtyText == null)
            return;

        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
            if (quantity <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Tidak Valid", "Jumlah harus berupa angka positif");
            return;
        }

        redirectToCheckout(selectedBook, quantity);
    }

    private void redirectToCheckout(Book book, int qty) {
        try {
            MainController.getInstance().showCheckoutPage(book, qty);
            closeWindow();
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
