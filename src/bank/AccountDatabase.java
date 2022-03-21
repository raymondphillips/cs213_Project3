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
    public static final int IN_DATABASE_AND_OPEN = -2;
    public static final int NOT_FOUND = -1;
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
     * @return an int the location of the account in the database or
     * NOT_FOUND if
     * not found
     */
    private int find(Account account) {
        int i;
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
     * a find method for the deposit case
     * @param account an Account object
     * @return an int of the location of the acc in the database
     */
    public int depositFind(Account account){
        int i;
        if (this.accounts == null) {
            return NOT_FOUND;
        }
        for (i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                return NOT_FOUND;
            }
            if (account.depositEquals(this.accounts[i])) {
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
            if (accounts[accountLocation].getClosedStatus()) {
                if (accounts[accountLocation].getType()
                                             .equals(account.getType())) {
                    accounts[accountLocation] = account;
                    return true;
                }
            } else {
                if (accounts[accountLocation].equals(account)) {
                    accounts[numAcct++] = account;
                    return true;
                }
            }
            return false;
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
        if (x == NOT_FOUND) {
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
        int acc = this.depositFind(account);
        if (acc > NOT_FOUND) {
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
        int acc = this.depositFind(account);
        if (acc > NOT_FOUND) {
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
        int i, x, a = 0, w = 0, d = 0, f = 0;
        if (accounts[0] == null) {
            System.out.println("Account Database is empty!");
            return;
        }
        Account[] cc = new Account[accounts.length], c =
                new Account[accounts.length], s =
                new Account[accounts.length], mm =
                new Account[accounts.length];
        for (i = 0; i < accounts.length; i++) {
            if (this.accounts[i] == null) {
                break;
            } else if (accounts[i].getType().equals("College Checking")) {
                cc[a++] = accounts[i];
            } else if (accounts[i].getType().equals("Checking")) {
                c[w++] = accounts[i];
            } else if (accounts[i].getType().equals("Savings")) {
                s[d++] = accounts[i];
            } else if (accounts[i].getType().equals("Money Market Savings")) {
                mm[f++] = accounts[i];
            }
        }
        for (x = 0; x < 4; x++) {
            if (x == 0) {
                for (i = 0; i < w; i++) {
                    accounts[i] = c[i];
                    System.out.println(c[i].toString());
                }
            } else if (x == 1) {
                for (i = 0; i < a; i++) {
                    accounts[i + w] = cc[i];
                    System.out.println(cc[i].toString());
                }
            } else if (x == 2) {
                for (i = 0; i < f; i++) {
                    accounts[i + w + a] = mm[i];
                    System.out.println(mm[i].toString());
                }
            } else if (x == 3) {
                for (i = 0; i < d; i++) {
                    accounts[i + w + a + f] = s[i];
                    System.out.println(s[i].toString());
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
        int i, x;
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

            for (Account account : accounts) {
                if (account == null) {
                    break;
                } else {
                    account.updateBalanceWithFeesAndInterest();
                }
            }
        }
        this.printByAccountType();
    }

    /**
     * a public method to search the database
     *
     * @param account an account object
     * @return NOT_FOUND if not found, otherwise the position of the
     * account in
     * the database
     */
    public int searchDatabase(Account account) {
        int loc = this.find(account);
        if (loc > NOT_FOUND) {
            if (!this.accounts[loc].getClosedStatus()) {
                return -2;
            } else {
                return loc;
            }
        } else {
            return NOT_FOUND;
        }
    }

    /**
     * a public method to search the database
     *
     * @param account an account object
     * @return NOT_FOUND if not found, otherwise the position of the
     * account in
     * the database
     */
    public int depositSearchDatabase(Account account){
        int loc = this.depositFind(account);
        if (loc > NOT_FOUND) {
            if (!this.accounts[loc].getClosedStatus()) {
                return -2;
            } else {
                return loc;
            }
        } else {
            return NOT_FOUND;
        }
    }

    /**
     * a method to get the account at a certain location in the database
     *
     * @param account a acc object
     * @return a similar acc object in the database
     */
    public Account getAccount(Account account) {
        int result = depositSearchDatabase(account);
        if (result == NOT_FOUND) {
            return null;
        }
        if (result == IN_DATABASE_AND_OPEN) {
            return accounts[this.depositFind(account)];
        } else {
            return accounts[result];
        }
    }

    /**
     * get the account status of an account
     * @param acc
     * @return
     */
    public boolean getAccStatus(Account acc) {
        int loc = this.find(acc);
        if (loc > NOT_FOUND) {
            return accounts[loc].getClosedStatus();
        }
        return false;
    }

    /**
     * a method the check whether the database is empty or not
     *
     * @return ture if the database is empty otherwise false.
     */
    public boolean isEmpty() {
        if (this.numAcct == 0) {
            return true;
        }
        return false;
    }

    /**
     * check to see if acc is college checking or regular checking
     * @param acc an account object
     * @return true if it exists, false otherwise
     */
    public boolean checkCOrCCCrossExists(Account acc){
        if(this.accounts == null) return false;

        for(int i = 0; i < this.accounts.length; i++){
            if(this.accounts[i] == null) break;
            if((acc.getType().equals("Checking") && this.accounts[i].getType().equals("College Checking")) ||
                    (acc.getType().equals("College Checking")) && this.accounts[i].getType().equals("Checking")){
                if(acc.equals(this.accounts[i]) && !this.accounts[i].closed) return true;
            }
        }

        return false;
    }
}
