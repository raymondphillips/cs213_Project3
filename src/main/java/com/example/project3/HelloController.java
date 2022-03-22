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
    @FXML TextField openingDeposit;
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
    @FXML TextArea consoleOutput;

    BankTeller teller;
    public static final int IN_DATABASE_AND_OPEN = -2;
    public static final int NOT_FOUND = -1;

    public void initialize(Stage primaryStage) throws Exception{
        //disable editing for textarea
        consoleOutput.setEditable(false);
        //initialize bankteller
        teller = new BankTeller();
        teller.accountDatabase = new AccountDatabase();
        //initialize buttons
        NB.setDisable(true);
        Newark.setDisable(true);
        Camden.setDisable(true);
        loyal.setDisable(true);
        checking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NB.setDisable(true);
                NB.setSelected(false);
                Newark.setDisable(true);
                Newark.setSelected(false);
                Camden.setDisable(true);
                Camden.setSelected(false);
                loyal.setDisable(true);
                loyal.setSelected(false);
            }
        });

        collegeChecking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NB.setDisable(false);
                Newark.setDisable(false);
                Camden.setDisable(false);
                loyal.setDisable(true);
                loyal.setSelected(false);
            }
        });

        savings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NB.setDisable(true);
                NB.setSelected(false);
                Newark.setDisable(true);
                Newark.setSelected(false);
                Camden.setDisable(true);
                Camden.setSelected(false);
                loyal.setDisable(false);
            }
        });

        moneyMarket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NB.setDisable(true);
                NB.setSelected(false);
                Newark.setDisable(true);
                Newark.setSelected(false);
                Camden.setDisable(true);
                Camden.setSelected(false);
                loyal.setDisable(true);
                loyal.setSelected(false);
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
            selectedAccount = convertAcctTypeToCode(selected.getText());
        }

        //selected campus code
        RadioButton selectedCampus = (RadioButton) campus.getSelectedToggle();
        String selectedValCampus = "";
        if(selectedCampus == null){
            //error handle
        } else{
            selectedValCampus = convertCampusCode(selectedCampus.getText());
        }

        //opening deposit
        String openingDep = openingDeposit.getText();

        //loyal or not
        String loyaltyCheck = convertBooleanToLoyalty(loyal.isSelected());

        String userCommand = "O\t" + selectedAccount + "\t" + fname + " " + lname + " " + dob +
                " " + openingDep;

        if(selectedAccount.equals("S")) userCommand += " " + loyaltyCheck;
        if(selectedAccount.equals("CC")) userCommand += " " + selectedValCampus;

        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));

        teller.handle(strArr);
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

    public String convertBooleanToLoyalty(boolean b){
        if(b) return "1";
        return "0";
    }

    public String convertCampusCode(String campusC){
        String str = campusC.trim();
        if(str.equals("NB")) return "0";
        else if(str.equals("Newark")) return "1";
        else return "2";
    }
}