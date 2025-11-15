package com.example.bookstore;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        App.startApp(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
