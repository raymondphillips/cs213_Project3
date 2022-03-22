package src.main.java.com.example.project3;

import src.bank.AccountDatabase;
import src.bank.BankTeller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;

import java.io.IOException;

public class HelloController {
    @FXML private Label welcomeText;
    @FXML protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML Button open;
    @FXML Button close;
    @FXML TextField fnametext;
    @FXML TextField lnametext;
    @FXML TextField dobpicker;
    @FXML ToggleGroup acctype;
    @FXML RadioButton checking;
    @FXML RadioButton collegeChecking;
    @FXML RadioButton savings;
    @FXML RadioButton moneyMarket;
    @FXML RadioButton NB;
    @FXML RadioButton Newark;
    @FXML RadioButton Camden;
    @FXML CheckBox loyal;
    @FXML ToggleGroup campus;

    BankTeller teller;
    public static final int IN_DATABASE_AND_OPEN = -2;
    public static final int NOT_FOUND = -1;

    public void initialize(Stage primaryStage) throws Exception{
        teller = new BankTeller();
        teller.accountDatabase = new AccountDatabase();
        NB.setDisable(true);
        Newark.setDisable(true);
        Camden.setDisable(true);
        loyal.setDisable(true);
        checking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NB.setDisable(true);
                Newark.setDisable(true);
                Camden.setDisable(true);
                loyal.setDisable(true);
            }
        });

        collegeChecking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NB.setDisable(false);
                Newark.setDisable(false);
                Camden.setDisable(false);
                loyal.setDisable(true);
            }
        });

        savings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NB.setDisable(true);
                Newark.setDisable(true);
                Camden.setDisable(true);
                loyal.setDisable(false);
            }
        });

        moneyMarket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NB.setDisable(true);
                Newark.setDisable(true);
                Camden.setDisable(true);
                loyal.setDisable(true);
            }
        });
    }

    public void controllerOpen(ActionEvent e) throws IOException {
        //fname lname dob from text fields
        String fname = fnametext.getText();
        String lname = lnametext.getText();
        String dob = dobpicker.getText();

        //gets selected accttype button
        RadioButton selected = (RadioButton) acctype.getSelectedToggle();
        String selectedAccount = "";
        if(selected == null){
            //error handle
        } else{
            selectedAccount = selected.getText();
        }

        //selected campus code
        RadioButton selectedCampus = (RadioButton) campus.getSelectedToggle();
        String selectedValCampus = "";
        if(selectedCampus == null){
            //error handle
        } else{
            selectedAccount = convertAcctTypeToCode(selected.getText());
        }

        //loyal or not
        boolean loyaltyCheck = loyal.isSelected();

    }

    public void controllerClose(ActionEvent e) throws IOException{
        //fname lname dob from text fields
        String fname = fnametext.getText();
        String lname = lnametext.getText();
        String dob = dobpicker.getText();

        //gets selected accttype button
        RadioButton selected = (RadioButton) acctype.getSelectedToggle();
        String selectedAccount = "";
        if(selected == null){
            //error handle
        } else{
            selectedAccount = convertAcctTypeToCode(selected.getText());
        }

        String userCommand = "C\t" + selectedAccount + "\t" + fname + " " + lname + " " + dob;

        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));

        teller.handle(strArr);
    }

    public String convertAcctTypeToCode(String acctType){
        switch(acctType){
            case "Checking": return "C";
            case "Savings": return "S";
            case "Money Market": return "MM";
            case "College Checking": return "CC";
        }

        return "";
    }
}