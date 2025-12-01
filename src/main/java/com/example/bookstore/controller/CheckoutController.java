package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.TransactionService;
import com.example.bookstore.service.UserService;
import com.example.bookstore.session.SessionManager;
import com.example.bookstore.util.PasswordUtil;

import com.example.bookstore.model.DeliveryAddress;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class CheckoutController {

    @FXML
    private ComboBox<DeliveryAddress> addressCombo;
    @FXML
    private CheckBox chkSaveAddress;
    @FXML
    private GridPane newAddressPane;
    @FXML
    private TextField txtLabel;
    @FXML
    private TextArea txtAddress;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtPostalCode;

    @FXML
    private ComboBox<String> courierCombo;

    @FXML
    private Label summaryLabel;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button confirmBtn;

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
        double pricePerUnit = book.getPrice();
        if (book.getDiscount() > 0) {
            pricePerUnit = book.getPrice() * (100 - book.getDiscount()) / 100.0;
        }
        this.totalPrice = (int) (pricePerUnit * qty);
        this.currentUser = SessionManager.getCurrentUser();

        String priceDetails = "Harga Satuan: Rp " + String.format("%,d", book.getPrice()).replace(',', '.');
        if (book.getDiscount() > 0) {
            priceDetails += " (Disc " + book.getDiscount() + "%) -> Rp "
                    + String.format("%,d", (int) pricePerUnit).replace(',', '.');
        }

        summaryLabel.setText(
                "Ringkasan Pesanan\n\n" +
                        "Judul Buku: " + book.getTitle() + "\n" +
                        "Jumlah: " + qty + "\n" +
                        priceDetails + "\n" +
                        "Total: Rp " + String.format("%,d", totalPrice).replace(',', '.'));

        courierCombo.getItems().addAll("JNE", "TIKI", "POS", "SiCepat", "AnterAja");

        addressCombo.getItems().clear();
        if (currentUser.getDeliveryAddresses() != null) {
            addressCombo.getItems().addAll(currentUser.getDeliveryAddresses());
        }

        DeliveryAddress newAddressOption = new DeliveryAddress("Tambah Alamat Baru", "", "", "");
        addressCombo.getItems().add(newAddressOption);

        addressCombo.setOnAction(e -> {
            DeliveryAddress selected = addressCombo.getValue();
            if (selected == newAddressOption) {
                newAddressPane.setVisible(true);
                newAddressPane.setManaged(true);
                chkSaveAddress.setVisible(true);
                chkSaveAddress.setManaged(true);
                clearNewAddressFields();
            } else if (selected != null) {
                newAddressPane.setVisible(false);
                newAddressPane.setManaged(false);
                chkSaveAddress.setVisible(false);
                chkSaveAddress.setManaged(false);
            }
        });

        if (!currentUser.getDeliveryAddresses().isEmpty()) {
            addressCombo.getSelectionModel().selectFirst();
        }

        confirmBtn.setOnAction(e -> handleConfirm());
        cancelBtn.setOnAction(e -> closeCheckoutPage());
    }

    private void clearNewAddressFields() {
        txtLabel.clear();
        txtAddress.clear();
        txtCity.clear();
        txtPostalCode.clear();
    }

    private void handleConfirm() {
        DeliveryAddress selectedAddress = addressCombo.getValue();
        String courier = courierCombo.getValue();
        String finalAddressString = "";

        if (selectedAddress == null || courier == null) {
            showAlert(Alert.AlertType.ERROR, "Data belum lengkap", "Silakan pilih alamat dan jasa pengiriman.");
            return;
        }

        if (selectedAddress.getLabel().equals("Tambah Alamat Baru")) {
            if (txtAddress.getText().trim().isEmpty() || txtCity.getText().trim().isEmpty()
                    || txtPostalCode.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Data belum lengkap", "Alamat, Kota, dan Kode Pos wajib diisi.");
                return;
            }

            DeliveryAddress newAddress = new DeliveryAddress(
                    txtLabel.getText().trim(),
                    txtAddress.getText().trim(),
                    txtCity.getText().trim(),
                    txtPostalCode.getText().trim());

            finalAddressString = newAddress.toString();

            if (chkSaveAddress.isSelected()) {
                currentUser.addDeliveryAddress(newAddress);
                // Save user with new address
                userService.saveUser(currentUser);
            }
        } else {
            finalAddressString = selectedAddress.toString();
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
        if (inputPassword == null)
            return;

        if (!PasswordUtil.verifyPassword(inputPassword, currentUser.getPassword())) {
            showAlert(Alert.AlertType.ERROR, "Password Salah", "Password tidak sesuai");
            return;
        }

        try {
            transactionService.buyBook(currentUser.getId(), selectedBook.getId(), quantity, finalAddressString,
                    courier);

            selectedBook.setStock(selectedBook.getStock() - quantity);
            selectedBook.setSoldCount(selectedBook.getSoldCount() + quantity);
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
        MainController.getInstance().refreshView();
        MainController.getInstance().showDashboard();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
