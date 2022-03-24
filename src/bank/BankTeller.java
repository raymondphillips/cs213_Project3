package src.bank;

import java.util.Scanner;

/**
 * This class is the user interface class to process the transactions entered
 * through the console and output the results to the console.
 * An instance of Bank Teller class can process a single transaction, as
 * well as a
 * batch of transactions.
 *
 * @author Raymond Phillips, Xiaoxuan Chen
 */
public class BankTeller {
    public static final int IN_DATABASE_AND_OPEN = -2;
    public static final int NOT_FOUND = -1;
    public AccountDatabase accountDatabase;

    /**
     * a method to constantly check for user input\commands and execute the
     * commands if
     * it is valid.
     */
    public void run() {
        this.accountDatabase = new AccountDatabase();
        System.out.println(
                "Bank Teller running. Ready to process transactions.");

        boolean running = true;
        while (running) {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                String userCommand = sc.nextLine();
                if (userCommand.equals("Q")) {
                    System.out.println("Bank Teller is terminated.");
                    running = false;
                    break;
                }
                String[] strArr =
                        (userCommand.replaceAll("\\s+", " ").split(" "));
                if (userCommand.equals("\n") || userCommand.length() == 0) {
                    System.out.println("");
                } else {
                    handle(strArr);
                }
            }
        }
    }

    /**
     * a handler method to deal with incoming commands from the user.
     *
     * @param strArr a string that starts with a command and executes it
     */
    public String handle(String[] strArr) {
        int NOT_FOUND = -1;
        String toReturn = "";
        switch (strArr[0]) {
            case "O":
                if (isInformationValid(strArr).equals("Good")) {
                    toReturn = addAccount(strArr);
                } else{
                    toReturn = isInformationValid(strArr);
                }
                break;
            case "C":
                if (isInformationValid(strArr).equals("Good")) {
                    toReturn = removeAccount(strArr);
                } else{
                    toReturn = isInformationValid(strArr);
                }
                break;
            case "D":
                if (isInformationValid(strArr).equals("Good")) {
                    toReturn = depositMoney(strArr);
                } else{
                    toReturn = isInformationValid(strArr);
                }
                break;
            case "W":
                if (isInformationValid(strArr).equals("Good")) {
                    toReturn = withdrawMoney(strArr);
                }else{
                    toReturn = isInformationValid(strArr);
                }
                break;
            case "P":
                if (isInformationValid(strArr).equals("Good")) {
                    toReturn = this.accountDatabase.print();
                }
                break;
            case "PT":
                if (isInformationValid(strArr).equals("Good")) {
                    if (!accountDatabase.isEmpty()) {
                        //System.out.println("\n*list of accounts by account type.");
                        toReturn += "*list of accounts by account type.\n";
                    }
                    toReturn += this.accountDatabase.printByAccountType();
                }
                break;
            case "PI":
                if (isInformationValid(strArr).equals("Good")) {
                    toReturn += this.accountDatabase.printFeeAndInterest();
                }
                break;
            case "UB":
                if (isInformationValid(strArr).equals("Good")) {
                    if (!accountDatabase.isEmpty()) {
//                        System.out.println(
//                                "\n*list of accounts with updated " +
//                                        "balance");
                        toReturn += "*list of accounts with updated balance\n";
                    }
                    toReturn += this.accountDatabase.updatePrintFeeAndInterest();
                }
                break;
            default:
                //System.out.println("Invalid command!");
                toReturn = "Invalid command!";
                break;
        }
        return toReturn;
    }

    /**
     * a method to remove a specific type of account
     *
     * @param strArr array that has the information to remove an account.
     */
    private String removeAccount(String[] strArr) {
        String toReturn = "";
        Account acc = createGenericAccount(strArr);
        if (accountDatabase.getAccStatus(acc)) {
//            System.out.println("Account is closed already.");
            return "Account is closed already.";
        }
        if (!this.accountDatabase.close(acc)) {
//            System.out.println("Account not found!");
            return "Account not found!";
        } else {
            //System.out.println("Account closed.");
            return "Account closed.";
        }
    }

    /**
     * a method to open a specific type of account
     *
     * @param strArr an array that has the information to open an account.
     */
    private String addAccount(String[] strArr) {
        String toReturn = "";
        Account acc = createGenericAccount(strArr);
        double depositAmount = Double.parseDouble(strArr[5]);
        acc.setBalance(depositAmount);
        int alreadyInDatabase = accountDatabase.searchDatabase(acc);
        if(alreadyInDatabase > NOT_FOUND && (acc.getType().equals("Checking") || acc.getType().equals("College Checking"))){
            boolean crossExists = accountDatabase.checkCOrCCCrossExists(acc);
            if(crossExists){
//                System.out.println(acc.holder.toString() + " same account(type)" +
//                        " is in the database.");
                return acc.holder.toString() + " same account(type) is in the database.";
            }
        }
        if (alreadyInDatabase > NOT_FOUND) {
            switch (strArr[1]) {
                case "CC" -> {
                    int campusCode = Integer.parseInt(strArr[6]);
                    if (isCampusCodeValid(campusCode)) {
                        ((CollegeChecking) acc).setCampusCode(campusCode);
                    } else return toReturn;
                }
                case "S" -> {
                    boolean loyalty = false;
                    if (strArr[6].equals("1")) {
                        loyalty = true;
                    } else if (strArr[6].equals("0")) {
                        loyalty = false;
                    }

                    ((Savings) acc).setLoyalty(loyalty);
                }
                case "MM" -> ((MoneyMarket) acc).setLoyalty(false);
            }
            this.accountDatabase.open(acc);
            //System.out.println("Account reopened.");
            return "Account reopened.";
        } else if (alreadyInDatabase == IN_DATABASE_AND_OPEN) {
//            System.out.println(acc.holder.toString() + " same account(type)" +
//                                       " is in the database.");
            return acc.holder.toString() + " same account(type)" + " is in the database.";
        } else {
            switch (strArr[1]) {
                case "CC" -> {
                    int campusCode = Integer.parseInt(strArr[6]);
                    if (isCampusCodeValid(campusCode)) {
                        ((CollegeChecking) acc).setCampusCode(campusCode);
                    } else return toReturn;
                }
                case "S" -> {
                    boolean loyalty = false;
                    if (strArr[6].equals("1")) {
                        loyalty = true;
                    } else if (strArr[6].equals("0")) {
                        loyalty = false;
                    }
                    ((Savings) acc).setLoyalty(loyalty);
                }
                case "MM" -> ((MoneyMarket) acc).setLoyalty(true);
            }
            this.accountDatabase.open(acc);
//            System.out.println("Account opened.");
            return "Account opened.";
        }
    }

    /**
     * a method to check if the valid amount of arguments have been passed
     * to create an
     * account.
     *
     * @param strArr in a String array with the information to create an
     *               account.
     * @return true if there is enough information to create a certain type of
     * account, otherwise returns false
     */
    private boolean isThereEnoughInformation(String[] strArr) {
        if (strArr[0].equals("O")) {
            if ((strArr[1].equals("C") && strArr.length < 5) ||
                    ((strArr[1].equals("CC") || strArr[1].equals("S")) &&
                            strArr.length != 7) ||
                    ((strArr[1].equals("MM") || strArr[1].equals("C")) &&
                            strArr.length < 6)) {
                //System.out.println("Missing data for opening an account.");
                return false;
            }
            return true;
        } else if (strArr[0].equals("C") && strArr.length != 5) {
            //System.out.println("Missing data for opening an account.");
            return false;
        } else if ((strArr[0].equals("W") || strArr[0].equals("D")) &&
                strArr.length != 6) {
            //System.out.println("Missing data for opening an account.");
            return false;
        } else {
            if (strArr.length != 1 &&
                    (strArr[0].equals("P") || strArr[0].equals("PT") ||
                            strArr[0].equals("PI") ||
                            strArr[0].equals("UB"))) {
                //System.out.println("Missing data for opening an account.");
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * a method to check if the information provided is valid
     *
     * @param strArr in a String array with the information to create an
     *               account.
     * @return true if there is the information is valid, false otherwise
     */
    private String isInformationValid(String[] strArr) {
        if (!isThereEnoughInformation(strArr)) {
            return "Missing data for opening an account.";
        }
        if (strArr.length > 4) {
            Date dob = new Date(strArr[4]);
            double depositAmount;
            if (strArr[0].equals("O")) {
                try {
                    depositAmount = Double.parseDouble(strArr[5]);
                } catch (NumberFormatException ex) {
//                    System.out.println("Not a valid amount.");
//                    return false;
                    return "Not a valid amount.";
                }
                if (depositAmount <= 0) {
//                    System.out.println(
//                            "Initial deposit cannot be 0 or negative.");
//                    return false;
                    return "Initial deposit cannot be 0 or negative.";
                } else if (strArr[1].equals("MM") && depositAmount < 2500) {
//                    System.out.println(
//                            "Minimum of $2500 to open a MoneyMarket account" +
//                                    ".");
//                    return false;
                    return "Minimum of $2500 to open a MoneyMarket account.";
                }
            }


            if (new Profile("", "", dob).isValidDOB()) {
//                System.out.println("Date of birth invalid.");
//                return false;
                return "Date of birth invalid.";
            }
        }
//        return true;
        return "Good";
    }

    /**
     * checks to see if campus code is valid
     *
     * @param campusCode in a string that has the campus code
     * @return true if the campus code is valid otherwise false.
     */
    private boolean isCampusCodeValid(int campusCode) {
        if (campusCode == 0 || campusCode == 1 || campusCode == 2) {
            return true;
        } else {
            System.out.println("Invalid campus code.");
            return false;
        }
    }

    /**
     * a method to simplify account creation
     *
     * @param strArr a string with information to create an account
     * @return an account object
     */
    public Account createGenericAccount(String[] strArr) {
        Date dob = new Date(strArr[4]);
        Profile profile = new Profile(strArr[2], strArr[3], dob);

        switch (strArr[1]) {
            case "C": {
                return new Checking(profile, 0);
            }
            case "CC": {
                return new CollegeChecking(profile, 0.0, 0);
            }
            case "S": {
                return new Savings(profile, 0.0, false);
            }
            case "MM": {
                return new MoneyMarket(profile, 0.0);
            }
            default:
                return null;
        }
    }

    /**
     * a method to deposit money into an account
     *
     * @param strArr a string with the information to deposit money into an
     *               account
     */
    public String depositMoney(String[] strArr) {
        Account acc = createGenericAccount(strArr);
        double depositAmount;
        try {
            depositAmount = Double.parseDouble(strArr[5]);
            if (depositAmount <= 0) {
//                System.out.println(
//                        "Deposit - amount cannot be 0 or negative.");
                return "Deposit - amount cannot be 0 or negative.";
            } else {
                int loc = accountDatabase.depositSearchDatabase(acc);
                if (loc == IN_DATABASE_AND_OPEN) {
                    acc.setBalance(depositAmount);
                    accountDatabase.deposit(acc);
//                    System.out.println("Deposit - balance updated.");
                    return "Deposit - balance updated.";
                } else {
//                    System.out.println(
//                            acc.holder.toString() + " " + acc.getType() +
//                                    " is not in the database.");
                    return acc.holder.toString() + " " + acc.getType() + " is not in the database.";
                }

            }
        } catch (NumberFormatException ex) {
//            System.out.println("Not a valid amount.");
            return "Not a valid amount.";
        }
    }

    /**
     * a method to withdraw money from an account
     *
     * @param strArr a string with the information to withdraw money from
     *               an account
     */
    public String withdrawMoney(String[] strArr) {
        Account acc = createGenericAccount(strArr);
        try {
            double depositAmount = Double.parseDouble(strArr[5]);

            if (depositAmount <= 0) {
//                System.out.println(
//                        "Withdraw - amount cannot be 0 or negative.");
                return "Withdraw - amount cannot be 0 or negative.";
            }
            acc.setBalance(depositAmount);
            if (!accountDatabase.withdraw(acc)) {
                Account accCheck = accountDatabase.getAccount(acc);
                if (accCheck == null) {
//                    System.out.println(
//                            acc.holder.toString() + " " + acc.getType() +
//                                    " is not in the database.");
                    return acc.holder.toString() + " " + acc.getType() + " is not in the database.";
                }
                if (accountDatabase.getAccount(acc) != null &&
                        accountDatabase.getAccount(acc).getBalance() <
                                depositAmount) {
//                    System.out.println("Withdraw - insufficient fund.");
                    return "Withdraw - insufficient fund.";
                }
            } else {
//                System.out.println("Withdraw - balance updated.");
                return "Withdraw - balance updated.";
            }
        } catch (NumberFormatException ex) {
            //System.out.println("Not a valid amount.");
            return "Not a valid amount.";
        }
        return "";
    }
}



