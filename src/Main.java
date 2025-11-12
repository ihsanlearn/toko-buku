import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    String[][] data = {
        {"berly", "berly"},
        {"ihsan", "ihsan"}
    };

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(getClass().getResource("Main.fxml"));

        // Login Form
        Parent login = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene loginScene = new Scene(login);

        // Main Menu
        Label welcome = new Label("Welcome to the Main Menu");
        Button btnLogout = new Button("Logout");

        VBox mainLayout = new VBox(10, welcome, btnLogout);

        Scene mainMenu = new Scene(mainLayout, 400, 250);

        // Action Button
        // btnLog.setOnAction(e -> {
        //     String emailUser = email.getText();
        //     String passUser = pass.getText();

        //     if(check(emailUser, passUser)){
        //         stage.setScene(mainMenu);
        //     }else{
        //         message.setText("Login Failed! Incorrect email or password.");
        //     }
        // });

        stage.setTitle("Login Form");
        stage.setScene(loginScene);
        stage.show();
    }

    boolean check(String emailUser, String passUser){
        for (String[] akun : data) {
            if(emailUser.equals(akun[0]) && passUser.equals(akun[1])){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        launch();
    }
}
