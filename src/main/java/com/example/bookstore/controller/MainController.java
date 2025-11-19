package com.example.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.bookstore.App;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Product;
import com.example.bookstore.session.SessionManager;

public class MainController {

    @FXML private HBox headerBar;
    @FXML private FlowPane productContainer;
    @FXML private TextField searchField;
    @FXML private Button btnSearch;
    @FXML private ComboBox<String> categoryBox;
    @FXML private Button btnLogin;
    @FXML private MenuButton accountMenu;
    @FXML private MenuItem menuLogout;

    private List<Product> productList = new ArrayList<>();

    @FXML public void initialize() {

    User currentUser = SessionManager.getCurrentUser();
    
    if (currentUser == null) {
        btnLogin.setVisible(true);
        btnLogin.setManaged(true);

        accountMenu.setVisible(false);
        accountMenu.setManaged(false);
    } else {
        btnLogin.setVisible(false);
        btnLogin.setManaged(false);

        accountMenu.setText(currentUser.username);
        accountMenu.setVisible(true);
        accountMenu.setManaged(true);
    }

    menuLogout.getStyleClass().add("danger-item");

    menuLogout.setOnAction(e -> {
        try {
            SessionManager.logout();
            App.setRoot("MainView");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    });

    // 3. data
     List<Book> books = new BookRepository().getAll();

    productList = new ArrayList<>();

    for (Book b : books) {
        Product p = new Product(
            b.getTitle(),                    // name
            b.getPrice(),                    // price
            null                    // imagePath default
        );

        productList.add(p);
    }

    
    // 4. Category
    categoryBox.getItems().addAll("Semua", "Novel", "Komik", "Teknologi", "Pelajaran");
    categoryBox.getSelectionModel().selectFirst();

    loadProducts(productList);

    // 5. Events
    btnSearch.setOnAction(e -> searchProducts());
    btnLogin.setOnAction(e -> {
        try {
            goToLogin(e);
        } catch (Exception ex) {
            ex.printStackTrace();
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
            App.setRoot("LoginView");
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
