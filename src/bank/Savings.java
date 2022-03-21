package bank;

/**
 * class extends the Account class and includes specific data and
 * operations to a
 * savings account.
 *
 * @author Raymond Phillips, Xiaoxuan Chen
 * @extends Account
 */
public class Savings extends Account {
    public static final double MONTHLYFEE = 6.0;
    public static final double MONTHLYINTEREST = 0.003 / 12;
    public static final double MIN_BALANCE = 300.0;
    public double loyalInterest = 0.0015 / 12;
    public boolean isLoyal;

    /**
     * constructor method for the Savings class
     *
     * @param profile, a profile object of the user of the savings account
     * @param balance, a double which is a balance of the account
     * @param loyalty, boolean whether the account holder is a loyal member
     *                or not.
     */
    public Savings(Profile profile, double balance, boolean loyalty) {
        super();
        super.holder = profile;
        super.balance = balance;
        super.closed = false;
        this.isLoyal = loyalty;
    }

    /**
     * a method to get the monthly interest of the account
     *
     * @return a double with the interest of the account
     */
    @Override
    public double monthlyInterest() {
        if (isLoyal) {
            return (MONTHLYINTEREST + loyalInterest) * this.balance;
        }
        return MONTHLYINTEREST * this.balance;
    }

    /**
     * a method to get the monthly fee of the account
     *
     * @return a double with the fee of the account
     */
    @Override
    public double fee() {
        int waivedFee = 0;
        if (this.balance >= MIN_BALANCE) {
            return waivedFee;
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
        return "Savings";
    }


    /**
     * a method to get the string literal of a college checking account
     */
    @Override
    public String toString() {
        if (this.isLoyal == true) {
            return super.toString() + "::Loyal";
        } else {
            return super.toString();
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
