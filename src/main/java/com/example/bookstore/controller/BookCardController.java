package com.example.bookstore.controller;

import com.example.bookstore.model.Book;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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

        cardRoot.setOnMouseClicked(this::onClick);
    }

    private void onClick(MouseEvent event) {
        System.out.println("Clicked: " + book.getTitle());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(book.getTitle());
        alert.setContentText("Harga: " + book.getPrice());
        alert.show();
    }
}
