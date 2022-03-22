package src.main.java.com.example.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import src.main.java.com.example.project3.HelloController;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("project3.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);

        //controller
        HelloController control = fxmlLoader.getController();
        control.initialize(stage);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}