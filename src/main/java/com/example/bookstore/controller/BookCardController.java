package com.example.bookstore.controller;

import com.example.bookstore.App;
import com.example.bookstore.model.Book;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookCardController {
    @FXML
    private VBox cardRoot;
    @FXML
    private ImageView bookImage;
    @FXML
    private Label bookTitle;
    @FXML
    private Label bookPrice;
    @FXML
    private Text originalPrice;
    @FXML
    private Label discountBadge;
    @FXML
    private Label soldCountLabel;

    private Book book;

    public void setData(Book book) {
        this.book = book;

        bookTitle.setText(book.getTitle());

        if (book.getDiscount() > 0) {
            double discountedPrice = book.getPrice() * (100 - book.getDiscount()) / 100.0;
            originalPrice.setText("Rp " + String.format("%,d", book.getPrice()).replace(',', '.'));
            originalPrice.setVisible(true);
            originalPrice.setManaged(true);

            bookPrice.setText("Rp " + String.format("%,d", (int) discountedPrice).replace(',', '.'));

            discountBadge.setText(book.getDiscount() + "% OFF");
            discountBadge.setVisible(true);
        } else {
            originalPrice.setVisible(false);
            originalPrice.setManaged(false);
            bookPrice.setText("Rp " + String.format("%,d", book.getPrice()).replace(',', '.'));
            discountBadge.setVisible(false);
        }

        if (soldCountLabel != null) {
            soldCountLabel.setText("Terjual: " + book.getSoldCount());
        }

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
                e.printStackTrace();
            }
        });
    }

    private void onClick(MouseEvent event) throws Exception {
        FXMLLoader loader = App.loadFXML("BookDetail");
        Parent root = loader.load();

        BookDetailController controller = loader.getController();

        controller.setBookData(book);

        Stage stage = new Stage();
        stage.setTitle(book.getTitle());
        stage.setScene(new Scene(root));
        stage.initOwner(cardRoot.getScene().getWindow());
        stage.show();
    }
}
