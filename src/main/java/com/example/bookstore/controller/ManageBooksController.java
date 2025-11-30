package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ManageBooksController {

    @FXML
    private TableView<Book> tableBooks;
    @FXML
    private TableColumn<Book, Integer> colId;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, Double> colPrice;
    @FXML
    private TableColumn<Book, Integer> colStock;
    @FXML
    private TableColumn<Book, String> colCategory;
    @FXML
    private TableColumn<Book, String> colImgPath;

    @FXML
    private TextField inputTitle;
    @FXML
    private TextField inputAuthor;
    @FXML
    private TextField inputPrice;
    @FXML
    private TextField inputStock;
    @FXML
    private ComboBox<String> inputCategory;
    @FXML
    private TextArea inputDescription;

    @FXML
    private Label labelImgName;

    private ObservableList<Book> bookList;
    private final BookService bookService = new BookService();

    private File selectedImageFile;
    private File existingImageFile;

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colImgPath.setCellValueFactory(new PropertyValueFactory<>("imgPath"));

        inputCategory.getItems().addAll("Novel", "Komik", "Teknologi", "Pelajaran", "Lainnya");

        List<Book> books = bookService.getAllBooks();

        bookList = FXCollections.observableArrayList(books);
        tableBooks.setItems(bookList);

        tableBooks.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> fillForm(newVal));
    }

    @FXML
    private void handleAdd() {
        try {
            String title = inputTitle.getText();
            String author = inputAuthor.getText();
            int price = Integer.parseInt(inputPrice.getText());
            int stock = Integer.parseInt(inputStock.getText());
            String category = inputCategory.getValue();
            if (category == null)
                category = "Lainnya";

            String description = inputDescription.getText();
            if (description == null)
                description = "";

            String imgFileName = saveImageToFolder(title);

            Book newBook = new Book(title, author, price, stock, "com/example/bookstore/images/" + imgFileName,
                    category, description);

            bookService.addBook(newBook);
            ;
            ;
            bookList.add(newBook);

            showAlert("Success", "sipp buku dah ditambahin syg.");
        } catch (Exception e) {
            showAlert("Error", "buku gagal ditambahin syg");
        }
    }

    @FXML
    private void handleUpdate() {
        Book selected = tableBooks.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "pilih buku buat diupdate");
            return;
        }

        try {
            String oldImgPath = selected.getImgPath();
            String newTitle = inputTitle.getText();
            String newImg = oldImgPath;

            if (selectedImageFile != null) {
                String ext = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
                String newFileName = newTitle.replace(" ", "_").toLowerCase() + ext;

                if (!newFileName.equals(extractFileName(oldImgPath))) {
                    if (extractFileName(oldImgPath) != null && !extractFileName(oldImgPath).isEmpty()) {
                        deleteOldImage(extractFileName(oldImgPath));
                    }

                    newImg = saveImageToFolder(newFileName, selectedImageFile);
                }
            } else if (existingImageFile != null) {

                String ext = oldImgPath.substring(oldImgPath.lastIndexOf("."));
                String expectedNewName = newTitle.replace(" ", "_").toLowerCase() + ext;

                if (!expectedNewName.equals(extractFileName(oldImgPath))) {
                    newImg = renameImageFile(extractFileName(oldImgPath), expectedNewName);
                }
            }

            selected.setTitle(inputTitle.getText());
            selected.setAuthor(inputAuthor.getText());
            selected.setPrice(Integer.parseInt(inputPrice.getText()));
            selected.setStock(Integer.parseInt(inputStock.getText()));
            selected.setImgPath(newImg);

            String cat = inputCategory.getValue();
            if (cat != null)
                selected.setCategory(cat);

            selected.setDescription(inputDescription.getText());

            bookService.updateBook(selected);

            tableBooks.refresh();
            clearForm();

            showAlert("Success", "buku berhasil diupdate ygy");

        } catch (Exception e) {
            showAlert("Error", "yang bener ajg: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Book selected = tableBooks.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "pilih buku yang mau dihapus");
            return;
        }

        bookService.deleteBook(selected.getId());
        deleteOldImage(extractFileName(selected.getImgPath()));

        bookList.remove(selected);
        clearForm();

        showAlert("Success", "Book deleted successfully!");
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    @FXML
    private void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Book Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            selectedImageFile = file;
            labelImgName.setText(file.getName());
        } else {
            labelImgName.setText("No file selected");
        }
    }

    private String saveImageToFolder(String title) throws IOException {
        if (selectedImageFile == null)
            return null;

        String ext = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
        String newFileName = title.replace(" ", "_").toLowerCase() + ext;

        Path destPath = Paths.get("src/main/resources/com/example/bookstore/images/" + newFileName);
        Files.copy(selectedImageFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }

    private String saveImageToFolder(String newFileName, File sourceFile) {
        try {
            Path dest = Paths.get("src/main/resources/com/example/bookstore/images/" + newFileName);
            Files.copy(sourceFile.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void deleteOldImage(String fileName) {
        if (fileName == null || fileName.isEmpty())
            return;

        Path path = Paths.get("src/main/resources/com/example/bookstore/images/" + fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println("Gagal menghapus gambar lama: " + e.getMessage());
        }
    }

    private String renameImageFile(String oldName, String newName) {
        if (oldName == null || oldName.isEmpty())
            return oldName;

        Path oldPath = Paths.get("src/main/resources/com/example/bookstore/images/" + oldName);
        Path newPath = Paths.get("src/main/resources/com/example/bookstore/images/" + newName);

        try {
            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
            return newName;
        } catch (IOException e) {
            System.out.println("Gagal rename file: " + e.getMessage());
            return oldName;
        }
    }

    private void clearForm() {
        inputTitle.clear();
        inputAuthor.clear();
        inputPrice.clear();
        inputStock.clear();
        inputCategory.getSelectionModel().clearSelection();
        inputDescription.clear();
        tableBooks.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private String extractFileName(String path) {
        if (path == null || path.isBlank())
            return "";
        int idx = path.lastIndexOf('/');
        if (idx == -1)
            return path;
        return path.substring(idx + 1);
    }

    private void fillForm(Book b) {
        if (b == null)
            return;

        inputTitle.setText(b.getTitle());
        inputAuthor.setText(b.getAuthor());
        inputPrice.setText(String.valueOf(b.getPrice()));
        inputStock.setText(String.valueOf(b.getStock()));
        inputCategory.setValue(b.getCategory());
        inputDescription.setText(b.getDescription());

        if (b.getImgPath() != null && !b.getImgPath().isEmpty()) {
            labelImgName.setText(extractFileName(b.getImgPath()));

            String imgPath = "src/main/resources/" + b.getImgPath();
            existingImageFile = new File(imgPath);
        } else {
            labelImgName.setText("No file selected");
            existingImageFile = null;
        }

        selectedImageFile = null;
    }
}
