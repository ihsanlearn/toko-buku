package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import com.example.bookstore.session.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
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
    @FXML private Label pagesLabel;
    @FXML private Label languageLabel;
    @FXML private Button addCartBtn;
    @FXML private Button buyNowBtn;
    @FXML private Button closeBtn;

    public void setBookData(Book book) {
        titleLabel.setText(book.getTitle());
        authorLabel.setText("Penulis: " + book.getAuthor());
        // publisherLabel.setText("Penerbit: " + book.getPublisher());
        priceLabel.setText("Rp " + String.format("%,d", book.getPrice()).replace(',', '.'));
        stockLabel.setText(book.getStock() > 0 ? "Stok: " + book.getStock() : "Habis");
        descriptionArea.setText("Lorem ipsum dolor sit amet consectetur adipisicing elit. Ut amet maiores iure qui quaerat ab eum architecto reiciendis, quas odit libero? Debitis expedita possimus tempore esse, ut, sequi quia vitae maiores facilis corrupti ipsa atque dolores laudantium, veniam nulla aut voluptatem sit nemo aspernatur nesciunt minima rem officiis! Soluta ipsum nam excepturi suscipit quas dolor ducimus omnis, quidem hic ea quae fugit laboriosam officia aspernatur asperiores. Sunt quam itaque veniam laudantium, alias dignissimos maxime eius cumque, numquam quae molestias. Sunt alias fugit explicabo quaerat natus dicta saepe expedita ad ipsam quidem repellat corporis, vero eius laborum obcaecati magni impedit veritatis?");
        isbnLabel.setText("ISBN: " + book.getId());
        // pagesLabel.setText(book.getPages() + " halaman");
        // languageLabel.setText("Bahasa: " + book.getLanguage());

        try {
            Image img = book.getImgPath() != null
                    ? new Image(getClass().getResourceAsStream(book.getImgPath()))
                    : new Image(getClass().getResourceAsStream("/com/example/bookstore/images/sample.jpeg"));
            bookImage.setImage(img);
        } catch (Exception e) {
            bookImage.setImage(new Image(getClass().getResourceAsStream("/com/example/bookstore/images/sample.jpeg")));
        }

        buyNowBtn.setOnAction(ev -> handleBuyNow(book));
        closeBtn.setOnAction(ev -> closeWindow());
    }


    
    private void handleBuyNow(Book book) {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Konfirmasi Pembelian");
            dialog.setHeaderText("Masukkan kata sandi untuk melanjutkan pembelian");

            ButtonType confirmBtn = new ButtonType("Konfirmasi", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmBtn, ButtonType.CANCEL);

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Kata sandi");

            dialog.getDialogPane().setContent(passwordField);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmBtn) {
                    return passwordField.getText();
                }
                return null;
            });

            String input = dialog.showAndWait().orElse(null);

            if (input == null) { 
                return;
            }

            String correctPassword = currentUser.getPassword();

            if (input.equals(correctPassword)) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText(null);
                success.setContentText("Pembelian berhasil: " + book.getTitle());
                success.show();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText(null);
                error.setContentText("Kata sandi salah!");
                error.show();
            }    
        }else{
            Alert notLog = new Alert(Alert.AlertType.INFORMATION);
            notLog.setHeaderText(null);
            notLog.setContentText("Anda harus login untuk melakukan pembelian");
            notLog.show();
        }
        
    }

    private void closeWindow() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
