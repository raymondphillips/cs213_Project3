package bank;

/**
 * An Account Database class that holds a list of information about the
 * account.
 * This class has a list of accounts and the size of them, and can access
 * any information about a specific account.
 *
 * @author Raymond Phillips, Xiaoxuan Chen
 */
public class AccountDatabase {
    private static final int EMPTY = 0;
    private Account[] accounts;
    private int numAcct;

    /**
     * constructor for the account database class
     */
    public AccountDatabase() {
        accounts = new Account[4];
        numAcct = 0;
    }

    /**
     * a private method to find the location of an account in the Database
     *
     * @param account an Account object
     * @return an int the location of the account in the database or -1 if
     * not found
     */
    private int find(Account account) {
        int i;
        int NOT_FOUND = -1;
        if (this.accounts == null) {
            return NOT_FOUND;
        }
        for (i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                return NOT_FOUND;
            }
            if (account.equals(this.accounts[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * a private method to increase the size of the database.
     */
    private void grow() {
        Account[] newArray = new Account[this.accounts.length + 4];
        int i;
        for (i = 0; i < this.accounts.length; i++) {
            newArray[i] = this.accounts[i];
        }
        this.accounts = newArray;
    }

    /**
     * a method to open a new account
     *
     * @param account, an Account object, to add to the database
     * @return true if the account was successfully added to the database
     */
    public boolean open(Account account) {
        if (numAcct == accounts.length - 1) {
            this.grow();
        }
        int accountLocation = this.find(account);
        if (accountLocation < 0) {
            accounts[numAcct++] = account;
            return true;
        } else {
            if (accounts[accountLocation].getAccountStatus() == true) {
                accounts[accountLocation] = account;
                return true;
            }
            if (accounts[accountLocation].equals(account)) {
                return false;
            } else {
                return false;
            }
        }
    }

    /**
     * a method to close an existing account
     *
     * @param account, an Account object
     * @return true if the account was closed, false otherwise.
     */
    public boolean close(Account account) {
        int x = this.find(account);
        if (x == -1) {
            return false;
        } else {
            if (account.getType().equals("Savings")) {
                Savings acc = new Savings(account.holder, 0.0, false);
                accounts[x] = acc;
            } else if (account.getType().equals("Money Market Savings")) {
                MoneyMarket acc = new MoneyMarket(account.holder, 0.0);
                acc.setLoyalty(false);
                accounts[x] = acc;
            }
            accounts[x].balance = 0;
            accounts[x].closed = true;
            return true;
        }
    }

    /**
     * a method to deposit money into an account
     *
     * @param account, an Account obj in which the money needs to go into
     */
    public void deposit(Account account) {
        int acc = this.find(account);
        if (acc > -1) {
            accounts[acc].deposit(account.balance);
        }
    }

    /**
     * a method to withdraw money from an account
     *
     * @param account, an Account obj in which the money needs to be
     *                 removed from.
     * @return false if there are not enough funds in the account.
     */
    public boolean withdraw(Account account) {
        int acc = this.find(account);
        if (acc > -1) {
            if (accounts[acc].balance < account.getBalance()) {
                return false;
            } else {
                accounts[acc].withdraw(account.getBalance());
                return true;
            }
        }
        return false;
    }

    /**
     * prints out the current account in the database
     */
    public void print() {
        if (accounts[0] == null) {
            System.out.println("Account Database is empty!");
            return;
        }

        System.out.println("\n*list of accounts in the database*");
        int i;
        for (i = 0; i < accounts.length; i++) {
            if (this.accounts[i] == null) {
                break;
            }
            System.out.println(accounts[i].toString());
        }
        System.out.println("*end of list*\n");
    }

    /**
     * prints out accounts in the database based on type
     */
    public void printByAccountType() {
        int i, x;
        if (accounts[0] == null) {
            System.out.println("Account Database is empty!");
            return;
        }
        for (x = 0; x < 4; x++) {
            for (i = 0; i < accounts.length; i++) {

                if (this.accounts[i] == null) {
                    break;
                } else if (accounts[i].getType().equals("College Checking") &&
                        x == 1) {
                    System.out.println(
                            ((CollegeChecking) accounts[i]).toString());
                } else if (accounts[i].getType().equals("Checking") &&
                        x == 0) {
                    System.out.println(accounts[i].toString());
                } else if (accounts[i].getType().equals("Savings") &&
                        x == 3) {
                    System.out.println(((Savings) accounts[i]).toString());
                } else if (accounts[i].getType()
                                      .equals("Money Market Savings") &&
                        x == 2) {
                    System.out.println(
                            ((MoneyMarket) accounts[i]).toString());
                }
            }
        }
        System.out.println("*end of list.\n");
    }

    /**
     * prints out the fee and interest rates of the accounts in the database
     */
    public void printFeeAndInterest() {
        if (accounts[0] == null) {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println(
                "\n*list of accounts with fee and " + "monthly interest");
        int i, x = 0;
        for (x = 0; x < 4; x++) {
            for (i = 0; i < accounts.length; i++) {

                if (this.accounts[i] == null) {
                    break;
                } else if (accounts[i].getType().equals("College Checking") &&
                        x == 1) {
                    System.out.println(
                            ((CollegeChecking) accounts[i]).toStringFeesAndInterest());
                } else if (accounts[i].getType().equals("Checking") &&
                        x == 0) {
                    System.out.println(
                            ((Checking) accounts[i]).toStringFeesAndInterest());
                } else if (accounts[i].getType().equals("Savings") &&
                        x == 3) {
                    System.out.println(
                            ((Savings) accounts[i]).toStringFeesAndInterest());
                } else if (accounts[i].getType()
                                      .equals("Money Market " + "Savings") &&
                        x == 2) {
                    System.out.println(
                            ((MoneyMarket) accounts[i]).toStringFeesAndInterest());
                }
            }
        }
        System.out.println("*end of list.\n");
    }

    /**
     * prints out the fee and interest rates of the accounts in the database
     */
    public void updatePrintFeeAndInterest() {
        if (accounts != null) {
            if (this.accounts[0] == null) {
                this.printByAccountType();
                return;
            }

            for (int i = 0; i < accounts.length; i++) {
                if (this.accounts[i] == null) {
                    break;
                } else {
                    accounts[i].updateBalanceWithFeesAndInterest();
                }
            }
        }
        this.printByAccountType();
    }

    /**
     * a public method to search the database
     *
     * @param account an account object
     * @return -1 if not found, otherwise the position of the account in
     * the database
     */
    public int searchDatabase(Account account) {
        int NOT_FOUND = -1;
        int loc = this.find(account);
        if (loc > -1) {
            if (this.accounts[loc].getAccountStatus()) {
                return NOT_FOUND;
            }
        }
        return loc;
    }

    /**
     * a method to get the account at a certain location in the database
     *
     * @param account a acc objeect
     * @return a similar acc object in the database
     */
    public Account getAccount(Account account) {
        int NOT_FOUND = -1;
        int result = searchDatabase(account);
        if(result == NOT_FOUND) return null;
        return accounts[result];
    }

    public boolean getAccStatus(Account acc) {
        int loc = this.find(acc);
        if (loc > -1){
            return accounts[loc].getAccountStatus();
        }
        return false;
    }

    /**
     * a method to see if the db is empty
     *
     * @return true if not empty otherwise not
     */
    public boolean isNotNull() {
        if (accounts[0] == null) {
            return false;
        }
        return true;
    }

    /**
     * a method the check whether the database is empty or not
     *
     * @return ture if the database is empty other wise false.
     */
    public boolean isEmpty() {

        if (this.accounts == null) {
            return true;
        }
        int i;
        for (i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                break;
            }
            if (this.accounts[i].getAccountStatus() == false) {
                return false;
            }
        }
        return true;
    }
}
