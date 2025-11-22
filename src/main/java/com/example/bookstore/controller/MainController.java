package com.example.bookstore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.bookstore.App;
import com.example.bookstore.model.User;
import com.example.bookstore.service.BookService;
import com.example.bookstore.model.Book;
import com.example.bookstore.session.SessionManager;

public class MainController {

    @FXML private HBox headerBar;
    @FXML private FlowPane bookContainer;
    @FXML private TextField searchField;
    @FXML private Button btnSearch;
    @FXML private ComboBox<String> categoryBox;
    @FXML private Button btnLogin;
    @FXML private MenuButton accountMenu;
    @FXML private MenuItem menuLogout;
    @FXML private MenuItem menuTopup;
    @FXML private MenuItem menuProfile;

    private final BookService bookService = new BookService();
    private List<Book> books;

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

        menuTopup.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookstore/TopUp.fxml"));
                Parent topUpRoot = loader.load();

                Stage topUpStage = new Stage();
                topUpStage.setTitle("Top Up Saldo");
                topUpStage.setScene(new Scene(topUpRoot));
                topUpStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        menuProfile.setOnAction(e -> {
            try {
                goTo(e, "UserProfile");;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        menuLogout.getStyleClass().add("danger-item");
        menuLogout.setOnAction(e -> {
            try {
                SessionManager.logout();
                App.setRoot("MainView");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        books = bookService.getAllBooks();

        categoryBox.getItems().addAll("Semua", "Novel", "Komik", "Teknologi", "Pelajaran");
        categoryBox.getSelectionModel().selectFirst();

        loadBooks(books);

        btnSearch.setOnAction(e -> searchProducts());
        btnLogin.setOnAction(e -> {
            try {
                goTo(e, "LoginView");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void loadBooks(List<Book> books) {
        bookContainer.getChildren().clear();

        for (Book b : books) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookstore/BookCard.fxml"));
                VBox card = loader.load();

                BookCardController controller = loader.getController();
                controller.setData(b);

                bookContainer.getChildren().add(card);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void searchProducts() {
        String keyword = searchField.getText().toLowerCase();

        List<Book> filtered = new ArrayList<>();

        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword)) {
                filtered.add(b);
            }
        }

        loadBooks(filtered);
        searchField.clear();
        searchField.requestFocus();
    }

    private void goTo(ActionEvent event, String fxml) throws Exception {
        try {
            App.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public void setAdminMode() {
    //     btnLogin.setVisible(false);
    //     btnLogin.setManaged(false);     

    //     Button addBookButton = new Button("Add Book");
    //     addBookButton.setStyle("-fx-background-color: #0051ff; -fx-text-fill: white; -fx-background-radius: 6;");
    //     addBookButton.setOnAction(e -> System.out.println("Add Book Clicked!"));

    //     headerBar.getChildren().add(addBookButton);
    // }
}
