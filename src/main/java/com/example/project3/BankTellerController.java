package src.main.java.com.example.project3;

import javafx.scene.input.KeyEvent;
import src.bank.AccountDatabase;
import src.bank.BankUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class serves as the link between the UI and our code from project 2.
 * It allows us to interact with all of the code of the previous project through buttons and text fields in the UI.
 * @author Raymond Phillips, Xiaoxuan Chen
 */
public class BankTellerController {
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


    BankUtil teller;
    public static final int IN_DATABASE_AND_OPEN = -2;
    public static final int NOT_FOUND = -1;

    /**
     * A method that initializes all of the buttons and variables
     * @param primaryStage The stage of the UI
     * @throws Exception library default
     */
    public void initialize(Stage primaryStage) throws Exception{
        //disable editing for textarea
        consoleOutput.setEditable(false);
        //initialize bankteller
        teller = new BankUtil();
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
                    if(num >= 2500 && savings.isSelected()){
                        loyal.setSelected(true);
                    }
                } catch(NumberFormatException e){

                }
            }
        });
    }

    /**
     * Method for the controller to handle the open account button being pressed
     * @param e Exception for actions
     * @throws IOException Default for library
     */
    public void controllerOpen(ActionEvent e) throws IOException {
        //fname lname dob from text fields
        String fname = fnametext.getText();
        String lname = lnametext.getText();
        String dob = dobpicker.getText();

        //gets selected accttype button
        RadioButton selected = (RadioButton) acctype.getSelectedToggle();
        String selectedAccount = "";

        if(selected != null){
            selectedAccount = convertAcctTypeToCode(selected.getText());
        }

        //selected campus code
        RadioButton selectedCampus = (RadioButton) campus.getSelectedToggle();
        String selectedValCampus = "";

        if(selectedCampus != null){
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

        //System.out.println(userCommand);
        if(strArr.length <5 || isAcctTypeNotSelected()){
            consoleOutput.setText("Missing data for opening an account.");
            return;
        }
        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    /**
     * Method for the controller to handle the close account button being pressed
     * @param e Error for any UI errors
     * @throws IOException exceptions for any i/o errors in ui fields
     */
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

        //System.out.println(userCommand);
        if(strArr.length < 5 || isAcctTypeNotSelected()){
            consoleOutput.setText("Missing data for opening an account.");
            return;
        }
        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    /**
     * Method for controller handling the deposit money button being pressed
     * @param e Error for any UI errors
     * @throws IOException exceptions for any i/o errors in ui fields
     */
    public void controllerDeposit(ActionEvent e) throws IOException{
        String fname = depositFname.getText();
        String lname = depositLname.getText();
        String dob = depositDOB.getText();

        //gets selected accttype button
        RadioButton selected = (RadioButton) depositAcctype.getSelectedToggle();
        String selectedAccount = "";

        if(selected != null){
            selectedAccount = convertAcctTypeToCode(selected.getText());
        }

        String depositAmnt = depositAmount.getText();

        String userCommand = "D\t" + selectedAccount + "\t" + fname + " " + lname + " " + dob +
                 " " + depositAmnt;

        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));

        if(strArr.length < 6 || isDepAcctTypeNotSelected()){
            consoleOutput.setText("Missing data for opening an account.");
            return;
        }

        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    /**
     * Method for withdraw money button being pressed in UI
     * @param e Error for any UI errors
     * @throws IOException exceptions for any i/o errors in ui fields
     */
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

        if(strArr.length < 6 || isDepAcctTypeNotSelected()){
            consoleOutput.setText("Missing data for opening an account.");
            return;
        }

        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    /**
     * Method for print all accounts being button pressed in UI
     * @param e Error for any UI errors
     * @throws IOException exceptions for any i/o errors in ui fields
     */
    public void controllerPrintAll(ActionEvent e) throws IOException{
        String userCommand = "P";
        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));
        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    /**
     * Method for print all accounts by account type being pressed in UI
     * @param e Error for any UI errors
     * @throws IOException exceptions for any i/o errors in ui fields
     */
    public void controllerPrintByType(ActionEvent e) throws IOException{
        String userCommand = "PT";
        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));
        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    /** Method for print all accounts along with fees and interest being pressed in ui
     *
     * @param e Error for any UI errors
     * @throws IOException exceptions for any i/o errors in ui fields
     */
    public void controllerPrintWithFeesInterest(ActionEvent e) throws IOException{
        String userCommand = "PI";
        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));
        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    /**
     * Method for update balance and display new balances button being pressed in ui
     * @param e Error for any UI errors
     * @throws IOException exceptions for any i/o errors in ui fields
     */
    public void controllerUpdateBalancesAndDisplay(ActionEvent e) throws IOException{
        String userCommand = "UB";
        String[] strArr =
                (userCommand.replaceAll("\\s+", " ").split(" "));
        String output = teller.handle(strArr);
        consoleOutput.setText(output);
    }

    /**
     * Private method that lets account type be converted to the appropriate argument
     * @param acctType The type of account
     * @return a String for the type of account
     */
    private String convertAcctTypeToCode(String acctType){
        switch(acctType){
            case "Checking": return "C";
            case "Savings": return "S";
            case "Money Market": return "MM";
            case "College Checking": return "CC";
        }

        return "";
    }

    /**
     * Method to convert whether or not the loyalty checkbox was ticked to an argument
     * @param b Whether or not the checkbox was ticked
     * @return A string for the argument for the loyalty
     */
    private String convertBooleanToLoyalty(boolean b){
        if(b) return "1";
        return "0";
    }

    /**
     * A method to convert a campus name to the appropriate campus code
     * @param campusC The campus name
     * @return A string for the campus code
     */
    private String convertCampusCode(String campusC){
        String str = campusC.trim();
        if(str.equals("NB")) return "0";
        else if(str.equals("Newark")) return "1";
        else return "2";
    }

    /**
     * Helper method to check whether or not none of the account type buttons were pressed
     * @return Boolean on whether or not no account type was selected
     */
    private boolean isAcctTypeNotSelected(){
        return !savings.isSelected() && !checking.isSelected() && !moneyMarket.isSelected() && !collegeChecking.isSelected();
    }

    /**
     * Helper method to check whether or not none of the account type buttons were pressed for the deposit tab
     * @return Boolean on whether or not no account type was selected
     */
    private boolean isDepAcctTypeNotSelected(){
        return !depositSavings.isSelected() && !depositCheckings.isSelected() && !depositCC.isSelected() && !depositMM.isSelected();
    }
}