package src.main.java.com.example.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class serves as the launcher for our BankTeller UI program.
 * @author Raymond Phillips, Xiaoxuan Chen
 */
public class BankTellerMain extends Application {

    /**
     * This method sets up the stage and launches our application UI.
     * @param stage The stage the UI is being set in.
     * @throws Exception Any exceptions in the UI being launched
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                BankTellerMain.class.getResource("BankTellerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);

        //controller
        BankTellerController control = fxmlLoader.getController();
        control.initialize(stage);

        stage.setTitle("Bank Teller Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method used to launch the starter method for the UI
     */
    public static void main(String[] args) {
        launch(args);
    }
}