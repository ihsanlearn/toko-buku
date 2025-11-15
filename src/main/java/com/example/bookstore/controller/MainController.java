package com.example.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.bookstore.App;
import com.example.bookstore.model.Product;

public class MainController {

    @FXML
    private HBox headerBar;

    @FXML
    private FlowPane productContainer;

    @FXML
    private TextField searchField;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> categoryBox;

    private List<Product> productList = new ArrayList<>();

    @FXML
    private Button btnLogin;


    @FXML
    public void initialize() {

        // Dummy data
        productList.add(new Product("Buku A", "Rp 50.000", "/com/example/bookstore/images/sample.jpeg"));
        productList.add(new Product("Buku B", "Rp 70.000", "/com/example/bookstore/images/sample.jpeg"));
        productList.add(new Product("Pemrograman Java", "Rp 125.000", "/com/example/bookstore/images/sample.jpeg"));
        productList.add(new Product("Algoritma", "Rp 90.000", "/com/example/bookstore/images/sample.jpeg"));
        productList.add(new Product("Database System", "Rp 105.000", "/com/example/bookstore/images/sample.jpeg"));
        productList.add(new Product("Machine Learning", "Rp 150.000", "/com/example/bookstore/images/sample.jpeg"));

        categoryBox.getItems().addAll("Semua", "Novel", "Komik", "Teknologi", "Pelajaran");
        categoryBox.getSelectionModel().selectFirst();

        loadProducts(productList);

        btnSearch.setOnAction(e -> searchProducts());
        btnLogin.setOnAction(e -> {
            try {
                goToLogin(e);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
    }

    private void loadProducts(List<Product> products) {
        productContainer.getChildren().clear();

        for (Product p : products) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookstore/ProductCard.fxml"));
                VBox card = loader.load();

                ProductCardController controller = loader.getController();
                controller.setData(p);

                productContainer.getChildren().add(card);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void searchProducts() {
        String keyword = searchField.getText().toLowerCase();

        List<Product> filtered = new ArrayList<>();

        for (Product p : productList) {
            if (p.getName().toLowerCase().contains(keyword)) {
                filtered.add(p);
            }
        }

        loadProducts(filtered);
        searchField.clear();
        searchField.requestFocus();
    }

    private void goToLogin(ActionEvent event) throws Exception {
        try {
            App.setRoot("loginView");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAdminMode() {
    btnLogin.setVisible(false);
    btnLogin.setManaged(false);     

    Button addBookButton = new Button("Add Book");
    addBookButton.setStyle("-fx-background-color: #0051ff; -fx-text-fill: white; -fx-background-radius: 6;");
    addBookButton.setOnAction(e -> System.out.println("Add Book Clicked!"));

    headerBar.getChildren().add(addBookButton);
    }


}
