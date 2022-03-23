package src.main.java.com.example.project3;

import javafx.scene.input.KeyEvent;
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
    @FXML Button deposit;
    @FXML Button withdraw;
    @FXML Button print;
    @FXML Button printP;
    @FXML Button printPI;
    @FXML Button updateFees;
    @FXML TextField fnametext;
    @FXML TextField lnametext;
    @FXML TextField dobpicker;
    @FXML TextField openingDeposit;
    @FXML TextField depositFname;
    @FXML TextField depositLname;
    @FXML TextField depositDOB;
    @FXML TextField depositAmount;
    @FXML ToggleGroup acctype;
    @FXML ToggleGroup campus;
    @FXML ToggleGroup depositAcctype;
    @FXML RadioButton checking;
    @FXML RadioButton collegeChecking;
    @FXML RadioButton savings;
    @FXML RadioButton moneyMarket;
    @FXML RadioButton NB;
    @FXML RadioButton Newark;
    @FXML RadioButton Camden;
    @FXML RadioButton depositCheckings;
    @FXML RadioButton depositSavings;
    @FXML RadioButton depositMM;
    @FXML RadioButton depositCC;
    @FXML CheckBox loyal;
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

        openingDeposit.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                String currentText = openingDeposit.getText();

                try{
                    int num = Integer.parseInt(currentText);
                    if(num >= 2500){
                        loyal.setSelected(true);
                    }
                } catch(NumberFormatException e){

                }
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

    public void controllerDeposit(ActionEvent e) throws IOException{
        String fname = depositFname.getText();
        String lname = depositLname.getText();
        String dob = depositDOB.getText();

        //gets selected accttype button
        RadioButton selected = (RadioButton) depositAcctype.getSelectedToggle();
        String selectedAccount = "";
        if(selected == null){
            //error handle for unselected acct type
        } else{
            selectedAccount = convertAcctTypeToCode(selected.getText());
        }

        String depositAmnt = depositAmount.getText();

        String userCommand = "D\t" + selectedAccount + "\t" + fname + " " + lname + " " + dob +
                 " " + depositAmnt;

        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));

        teller.handle(strArr);
    }

    public void controllerWithdraw(ActionEvent e) throws IOException{
        String fname = depositFname.getText();
        String lname = depositLname.getText();
        String dob = depositDOB.getText();

        //gets selected accttype button
        RadioButton selected = (RadioButton) depositAcctype.getSelectedToggle();
        String selectedAccount = "";
        if(selected == null){
            //error handle for unselected acct type
        } else{
            selectedAccount = convertAcctTypeToCode(selected.getText());
        }

        String withdrawAmnt = depositAmount.getText();

        String userCommand = "W\t" + selectedAccount + "\t" + fname + " " + lname + " " + dob +
                " " + withdrawAmnt;

        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));

        teller.handle(strArr);
    }

    public void controllerPrintAll(ActionEvent e) throws IOException{
        String userCommand = "P";
        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));
        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    public void controllerPrintByType(ActionEvent e) throws IOException{
        String userCommand = "PT";
        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));
        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    public void controllerPrintWithFeesInterest(ActionEvent e) throws IOException{
        String userCommand = "PI";
        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));
        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    public void controllerUpdateBalancesAndDisplay(ActionEvent e) throws IOException{
        String userCommand = "UB";
        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));
        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    private String convertAcctTypeToCode(String acctType){
        switch(acctType){
            case "Checking": return "C";
            case "Savings": return "S";
            case "Money Market": return "MM";
            case "College Checking": return "CC";
        }

        return "";
    }

    private String convertBooleanToLoyalty(boolean b){
        if(b) return "1";
        return "0";
    }

    private String convertCampusCode(String campusC){
        String str = campusC.trim();
        if(str.equals("NB")) return "0";
        else if(str.equals("Newark")) return "1";
        else return "2";
    }
}