package com.example.bookstore.controller;

import com.example.bookstore.model.Product;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ProductCardController {

    @FXML
    private VBox cardRoot;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    private Product product;

    public void setData(Product product) {
        this.product = product;

        productName.setText(product.getName());
        productPrice.setText(product.getPrice());

        try {
            productImage.setImage(new Image(product.getImagePath()));
        } catch (Exception e) {
            System.out.println("Image not found.");
        }

        cardRoot.setOnMouseClicked(this::onClick);
    }

    private void onClick(MouseEvent event) {
        System.out.println("Clicked: " + product.getName());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(product.getName());
        alert.setContentText("Harga: " + product.getPrice());
        alert.show();
    }
}
