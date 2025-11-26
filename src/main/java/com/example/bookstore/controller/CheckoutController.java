package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.TransactionService;
import com.example.bookstore.service.UserService;
import com.example.bookstore.session.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CheckoutController {

    @FXML private TextArea addressField;
    @FXML private ComboBox<String> courierCombo;
    @FXML private ComboBox<String> discountCombo;
    @FXML private Label summaryLabel;
    @FXML private Button cancelBtn;
    @FXML private Button confirmBtn;

    private Book selectedBook;
    private int quantity;
    private int totalPrice;
    private User currentUser;

    private final TransactionService transactionService = new TransactionService();
    private final UserService userService = new UserService();
    private final BookService bookService = new BookService();

    public void setOrderData(Book book, int qty) {
        this.selectedBook = book;
        this.quantity = qty;
        this.totalPrice = book.getPrice() * qty;
        this.currentUser = SessionManager.getCurrentUser();

        summaryLabel.setText(
                "Ringkasan Pesanan\n\n" +
                "Judul Buku: " + book.getTitle() + "\n" +
                "Jumlah: " + qty + "\n" +
                "Harga Satuan: Rp " + String.format("%,d", book.getPrice()).replace(',', '.') + "\n" +
                "Total: Rp " + String.format("%,d", totalPrice).replace(',', '.')
        );

        courierCombo.getItems().addAll("JNE", "TIKI", "POS", "SiCepat", "AnterAja");
        discountCombo.getItems().addAll("Tidak ada diskon", "Voucher 10%", "Voucher 25%");
        discountCombo.getSelectionModel().selectFirst();

        confirmBtn.setOnAction(e -> handleConfirm());
        cancelBtn.setOnAction(e -> closeCheckoutPage());
    }

    private void handleConfirm() {
        String address = addressField.getText().trim();
        String courier = courierCombo.getValue();

        if (address.isEmpty() || courier == null) {
            showAlert(Alert.AlertType.ERROR, "Data belum lengkap",
                    "Alamat dan jasa pengiriman wajib diisi.");
            return;
        }

        if (currentUser.getBalance() < totalPrice) {
            showAlert(Alert.AlertType.ERROR, "Saldo Tidak Cukup",
                    "Saldo Anda: Rp " + String.format("%,d", currentUser.getBalance()).replace(',', '.') +
                            "\nTotal harga: Rp " + String.format("%,d", totalPrice).replace(',', '.'));
            return;
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Konfirmasi Pembayaran");
        dialog.setHeaderText("Masukkan password akun untuk melanjutkan");

        ButtonType confirmBtnType = new ButtonType("Konfirmasi", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmBtnType, ButtonType.CANCEL);

        PasswordField passwordField = new PasswordField();
        dialog.getDialogPane().setContent(passwordField);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmBtnType) {
                return passwordField.getText();
            }
            return null;
        });

        String inputPassword = dialog.showAndWait().orElse(null);
        if (inputPassword == null) return;

        if (!inputPassword.equals(currentUser.getPassword())) {
            showAlert(Alert.AlertType.ERROR, "Password Salah", "Password tidak sesuai");
            return;
        }

        try {
            transactionService.buyBook(currentUser.getId(), selectedBook.getId(), quantity, address, courier);

            selectedBook.setStock(selectedBook.getStock() - quantity);
            bookService.updateBook(selectedBook);

            int newBalance = currentUser.getBalance() - totalPrice;
            currentUser.setBalance(newBalance);
            userService.updateBalance(currentUser.getId(), -totalPrice);
            SessionManager.setCurrentUser(currentUser);

            showAlert(Alert.AlertType.INFORMATION, "Berhasil",
                    "Pembelian berhasil dilakukan!\n\n" +
                            "Buku: " + selectedBook.getTitle() + "\n" +
                            "Jumlah: " + quantity + "\n" +
                            "Total Harga: Rp " + String.format("%,d", totalPrice).replace(',', '.') + "\n" +
                            "Saldo Tersisa: Rp " + String.format("%,d", newBalance).replace(',', '.'));

            closeCheckoutPage();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Gagal Memproses Pembelian", e.getMessage());
        }
    }

    private void closeCheckoutPage() {
        Stage stage = (Stage) confirmBtn.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
