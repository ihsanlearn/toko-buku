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

    @FXML
    private HBox headerBar;
    @FXML
    private FlowPane bookContainer;
    @FXML
    private HBox specialOffersContainer;
    @FXML
    private TextField searchField;
    @FXML
    private Button btnSearch;
    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private Button btnLogin;
    @FXML
    private MenuButton accountMenu;
    @FXML
    private MenuItem menuLogout;
    @FXML
    private MenuItem menuTopup;
    @FXML
    private MenuItem menuProfile;

    private final BookService bookService = new BookService();
    private List<Book> books;

    private static MainController instance;

    public static MainController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        instance = this;
        // Capture original center after layout pass or just assume it's set?
        // It's safer to capture it later or just assume the FXML structure.
        // But we can't easily get "center" property here if it's not injected.
        // Actually, we can't inject the root BorderPane easily if it doesn't have an
        // ID.
        // Let's add an ID to BorderPane in FXML or just use a workaround.

        // Workaround: We'll capture it when we first switch away.

        User currentUser = SessionManager.getCurrentUser();

        if (currentUser == null) {
            btnLogin.setVisible(true);
            btnLogin.setManaged(true);

            accountMenu.setVisible(false);
            accountMenu.setManaged(false);
        } else {
            btnLogin.setVisible(false);
            btnLogin.setManaged(false);

            accountMenu.setText(currentUser.getUsername() + " | Balance Rp" + currentUser.getBalance());
            accountMenu.setVisible(true);
            accountMenu.setManaged(true);
        }

        menuTopup.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookstore/TopUp.fxml"));
                Parent topUpRoot = loader.load();

                TopUpController ctrl = loader.getController();
                ctrl.setOnTopUpSuccess(() -> {
                    refreshView();
                });

                ctrl.setOnWithdrawSuccess(() -> {
                    refreshView();
                });

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
                goTo(e, "UserProfile");
                ;
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

        boolean specialDay = true;
        if (specialDay) {
            for (Book b : books) {
                if (b.getDiscount() == 0) {
                    if (Math.random() < 0.3) {
                        int discount = (int) (Math.random() * 4 + 1) * 10;
                        b.setDiscount(discount);
                    }
                }
            }
        }

        categoryBox.getItems().addAll("Semua", "Novel", "Komik", "Teknologi", "Pelajaran");
        categoryBox.getSelectionModel().selectFirst();
        categoryBox.setOnAction(e -> filterByCategory());

        loadBooks(books);
        loadSpecialOffers(books);

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

    private void filterByCategory() {
        String selectedCategory = categoryBox.getValue();
        if (selectedCategory == null || selectedCategory.equals("Semua")) {
            loadBooks(books);
            return;
        }

        List<Book> filtered = new ArrayList<>();
        for (Book b : books) {
            String cat = b.getCategory();
            if (cat != null && cat.equalsIgnoreCase(selectedCategory)) {
                filtered.add(b);
            }
        }

        loadBooks(filtered);
    }

    private void goTo(ActionEvent event, String fxml) throws Exception {
        try {
            App.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshView() {
        User currentUser = SessionManager.getCurrentUser();
        accountMenu.setText(currentUser.getUsername() + " | Saldo: " + currentUser.getBalance());

        books = bookService.getAllBooks();
        loadBooks(books);
    }

    private void loadSpecialOffers(List<Book> books) {
        specialOffersContainer.getChildren().clear();

        for (Book b : books) {
            if (b.getDiscount() > 0) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookstore/BookCard.fxml"));
                    VBox card = loader.load();

                    BookCardController controller = loader.getController();
                    controller.setData(b);

                    specialOffersContainer.getChildren().add(card);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showCheckoutPage(Book book, int quantity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bookstore/CheckoutView.fxml"));
            Parent checkoutView = loader.load();

            CheckoutController controller = loader.getController();
            controller.setOrderData(book, quantity);

            // Replace the center content with checkout view
            // We need to access the BorderPane. Since MainController is the controller for
            // the BorderPane,
            // we can get the scene's root, but better if we had a reference to the root.
            // However, MainController is set on the BorderPane in FXML.
            // Let's assume the root of the scene is the BorderPane.

            if (headerBar.getScene().getRoot() instanceof javafx.scene.layout.BorderPane) {
                javafx.scene.layout.BorderPane root = (javafx.scene.layout.BorderPane) headerBar.getScene().getRoot();
                if (originalCenter == null) {
                    originalCenter = root.getCenter();
                }
                root.setCenter(checkoutView);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDashboard() {
        try {
            // Re-load the dashboard content (ScrollPane with VBox)
            // Since we don't have it saved, we might need to reload it or keep a reference.
            // For simplicity, let's reload the MainView's center part or just reset the
            // root?
            // Resetting root is easier but might lose state.
            // Better: Keep a reference to the original center.

            // Actually, let's just reload the MainView completely for now to be safe,
            // OR better: Extract the dashboard content into a separate FXML
            // (Dashboard.fxml)
            // but that's a bigger refactor.

            // Let's try to restore the original center.
            // We can store the original center in initialize.

            if (originalCenter != null && headerBar.getScene().getRoot() instanceof javafx.scene.layout.BorderPane) {
                javafx.scene.layout.BorderPane root = (javafx.scene.layout.BorderPane) headerBar.getScene().getRoot();
                root.setCenter(originalCenter);
                refreshView(); // Refresh data
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private javafx.scene.Node originalCenter;
}
