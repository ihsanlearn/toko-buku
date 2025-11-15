package com.example.bookstore;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App {

    private static Stage primaryStage;

    public static void startApp(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("BookStore App");
        setRoot("MainView");
    }

    public static void setRoot(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/bookstore/" + fxml + ".fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Untuk ambil controller setelah load */
    public static <T> T setRootWithController(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                App.class.getResource("/com/example/bookstore/" + fxml + ".fxml")
        );

        Scene scene = new Scene(loader.load(), 800, 600);
        primaryStage.setScene(scene);
        return loader.getController();
    }
}
