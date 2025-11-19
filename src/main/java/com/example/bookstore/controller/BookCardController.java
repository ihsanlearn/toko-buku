package com.example.bookstore.controller;

import com.example.bookstore.App;
import com.example.bookstore.model.Book;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookCardController {
    @FXML private VBox cardRoot;
    @FXML private ImageView bookImage;
    @FXML private Label bookTitle;
    @FXML private Label bookPrice;

    private Book book;

    public void setData(Book book) {
        this.book = book;

        bookTitle.setText(book.getTitle());
        bookPrice.setText(String.valueOf(book.getPrice()));

        try {
            if (book.getImgPath() == null || book.getImgPath().isEmpty()) {
                bookImage.setImage(new Image("com/example/bookstore/images/sample.jpeg"));
            } else {
                bookImage.setImage(new Image(book.getImgPath()));
            }
        } catch (Exception e) {
            System.out.println("Image not found.");
        }

        cardRoot.setOnMouseClicked(arg0 -> {
            try {
                onClick(arg0);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    private void onClick(MouseEvent event) throws Exception {
        // Load FXML BookDetail
        FXMLLoader loader = App.loadFXML("BookDetail");
        Parent root = loader.load();

        // Ambil controller detail
        BookDetailController controller = loader.getController();

        // Kirim data Book sebelum window ditampilkan
        controller.setBookData(book);

        // Tampilkan window baru
        Stage stage = new Stage();
        stage.setTitle(book.getTitle());
        stage.setScene(new Scene(root));
        stage.initOwner(cardRoot.getScene().getWindow()); // parent window
        stage.show();
    }
}
