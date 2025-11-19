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

    /** Pindah scene tanpa perlu controller */
    public static void setRoot(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                App.class.getResource("/com/example/bookstore/" + fxml + ".fxml")
        );
        Scene scene = new Scene(loader.load(), 1500, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Pindah scene & ambil controller */
    public static <T> T setRootWithController(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                App.class.getResource("/com/example/bookstore/" + fxml + ".fxml")
        );

        Scene scene = new Scene(loader.load(), 1500, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
        return loader.getController();
    }

    public static void switchTo(String fxml) throws Exception {
        setRoot(fxml);
    }
}
