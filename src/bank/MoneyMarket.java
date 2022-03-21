package bank;

/**
 * class extends the Savings class and includes specific data and
 * operations to a money
 * market account.
 *
 * @author Raymond Phillips, Xiaoxuan Chen
 */
public class MoneyMarket extends Account {
    public static final double MONTHLYFEE = 10;
    public static final double MONTHLYINTEREST = 0.008 / 12;
    public static final double LOYALINTEREST = 0.0015 / 12;
    public static final double MIN_BALANCE = 2500.0;
    public int count;
    public boolean isLoyal;

    /**
     * constructor method for the Savings class
     *
     * @param profile  a profile object of the user of the money market
     *                 account
     * @param balance, a double which is a balance of the account
     */
    public MoneyMarket(Profile profile, double balance) {
        super();
        super.holder = profile;
        super.balance = balance;
        super.closed = false;
        this.isLoyal = true;
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
    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
        if (this.balance < MIN_BALANCE) {
            this.isLoyal = false;
        }
        count += 1;
    }

    /**
     * a method to get the monthly interest of the account
     *
     * @return a double with the interest of the account
     */
    @Override
    public double monthlyInterest() {
        if (this.balance < MIN_BALANCE) {
            this.isLoyal = false;
        }
        if (isLoyal) {
            return this.balance * (MONTHLYINTEREST + LOYALINTEREST);
        }
        return this.balance * MONTHLYINTEREST;
    }

    /**
     * a method to get the monthly fee of the account
     *
     * @return a double with the fee of the account
     */
    @Override
    public double fee() {
        int waivedFee = 0;
        if (count >= 3) {
            return MONTHLYFEE;
        }
        if (this.balance >= MIN_BALANCE) {
            return waivedFee;
        } else if (this.balance < MIN_BALANCE) {
            return MONTHLYFEE;
        }
        return MONTHLYFEE;
    }

    /**
     * a method to get the name of the account
     *
     * @return a string with the type of account
     */
    @Override
    public String getType() {
        return "Money Market Savings";
    }

    /**
     * a method to get the string literal of a money market account
     */
    @Override
    public String toString() {
        if (this.isLoyal == true) {
            return super.toString() + "::Loyal::withdrawl: " + this.count;
        } else {
            return super.toString() + "::withdrawl: " + this.count;
        }
    }

    /**
     * a method to get the string literal of a checking account with fees
     * and interest.
     */
    public String toStringFeesAndInterest() {
        return this.toString() + "::fee $" +
                String.format("%.2f", this.fee()) + "::monthly interest $" +
                String.format("%.2f", this.monthlyInterest());
    }

    /**
     * a method to set the loyalty of an account
     *
     * @param loyalty the loyalty of an account
     */
    public void setLoyalty(Boolean loyalty) {
        this.isLoyal = loyalty;
    }
}
