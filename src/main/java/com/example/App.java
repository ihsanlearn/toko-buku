package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource("/com/example/bookstore/main.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        scene.getStylesheets().add(App.class.getResource("/com/example/bookstore/style.css").toExternalForm());
        stage.setTitle("Toko Buku Online");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
