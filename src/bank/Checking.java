package bank;

/**
 * class extends the Account class and includes specific data and
 * operations to a
 * regular checking account.
 *
 * @author Raymond Phillips, Xiaoxuan Chen
 */
public class Checking extends Account {
    public static final double MONTHLYFEE = 25;
    public static final double MONTHLYINTEREST = 0.001 / 12;
    public static final double MIN_BALANCE = 1000.0;

    /**
     * constructor method for the Savings class
     *
     * @param profile  a profile object of the user of the checking account
     * @param balance, a double which is a balance of the account
     */
    public Checking(Profile profile, double balance) {
        super();
        super.holder = profile;
        super.balance = balance;
        super.closed = false;
    }

    /**
     * main method for the Checking.java class
     *
     * @param args takes in a string with the arguments
     */
    public static void main(String[] args) {
        Date da = new Date();
        Profile p = new Profile("Joe", "Mama", da);
        Checking acc = new Checking(p, 5000);
        System.out.println(acc.toString());
    }

    /**
     * a method to get the monthly interest of the account
     *
     * @return a double with the interest of the account
     */
    @Override
    public double monthlyInterest() {
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
        return "Checking";
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
}
