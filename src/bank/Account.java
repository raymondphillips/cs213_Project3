package src.bank;

import java.text.DecimalFormat;

/**
 * an abstract class that defines the common data and operations for all
 * account type;
 * each account has a profile that uniquely identifies the account holder.
 * This is the superclass of all account types, and it is an abstract class
 * with 3
 * abstract methods.
 *
 * @author Raymond Phillips, Xiaoxuan Chen
 */
public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    /**
     * a method to compare two account objects
     *
     * @param obj, a generic object.
     * @return true if the passed in object is equal to the compared object.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account acc)) {
            return false;
        }
        if (this.holder.getName().equalsIgnoreCase(acc.holder.getName())) {
            if (this.holder.getDOB().compareTo(acc.holder.getDOB()) == 0) {
                return (this.getType().equals(acc.getType()) ||
                        (acc.getType().equals("Checking") && this.getType().equals("College Checking")) ||
                        (acc.getType().equals("College Checking") && this.getType().equals("Checking")));
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * a method to check equality between accounts for depositing money
     * @param obj an account object
     * @return true if accounts are the same false otherwise
     */
    public boolean depositEquals(Object obj){
        if (!(obj instanceof Account acc)) {
            return false;
        }
        if (this.holder.getName().equalsIgnoreCase(acc.holder.getName())) {
            if (this.holder.getDOB().compareTo(acc.holder.getDOB()) == 0) {
                return this.getType().equals(acc.getType());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * a method to get the string literal of an account
     *
     * @return returns a string literal representation of this account object.
     */
    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        if (this.closed) {
            return this.getType() + "::" + this.holder.toString() +
                    "::Balance $" + formatter.format(this.balance) +
                    "::CLOSED";
        } else {
            return this.getType() + "::" + this.holder.toString() +
                    "::Balance $" + formatter.format(this.balance);
        }
    }

    /**
     * a method to withdraw money into an account balance.
     * first check to see if the user has funds in their account and if
     * they do then it
     * withdraws that amount from their account.
     *
     * @param amount that is the money a user wants to withdraw from their
     *               account.
     */
    public void withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance = this.balance - amount;
        }
    }

    /**
     * a method to deposit money into an account balance.
     *
     * @param amount that is the money a user wants to add to their account.
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     * a method to update the balance of the account with the fees and monthly
     * interest.
     */
    public void updateBalanceWithFeesAndInterest() {
        if (!this.closed) {
            this.balance = this.monthlyInterest() - this.fee() + this.balance;
        }
    }

    /**
     * an abstract method, to be implemented in the subclass of the account
     * object
     *
     * @return a double with the total monthly interest
     */
    public abstract double monthlyInterest();

    /**
     * an abstract method, to be implemented in the subclass of the account
     * object
     *
     * @return a double with the total monthly interest
     */
    public abstract double fee();

    /**
     * an abstract method, to be implemented in the subclass of the account
     * object
     *
     * @return a string with the type of account
     */
    public abstract String getType();

    /**
     * a method to get the string representation of the balance in the
     * account.
     *
     * @return a string representation of the balance in the account
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * a method to update the balance of an account
     *
     * @param bal a double that is the balance of the account.
     */
    public void setBalance(Double bal) {
        this.balance = bal;
    }

    /**
     * a method to get the closed/open status of the account
     *
     * @return the closed/open status of account.
     */
    public boolean getClosedStatus() {
        return this.closed;
    }
}
