package src.bank;

/**
 * class extends the Checking class and includes specific data and
 * operations to a
 * college checking account.
 *
 * @author Raymond Phillips, Xiaoxuan Chen
 */
public class CollegeChecking extends Account {
    public static final double MONTHLYFEE = 0.0;
    public static final double MONTHLYINTEREST = 0.0025 / 12;

    public static final int NEW_BRUINSWICK = 0;
    public static final int NEWARK = 1;
    public static final int CAMDEN = 2;

    int campusCode;

    /**
     * constructor method for the Savings class
     *
     * @param profile     a profile object of the user of the college
     *                    checking account
     * @param balance,    a double which is a balance of the account
     * @param campusCode, an int which is the campus code, 0 - New
     *                    Bruinswick, 1 -
     *                    Newark, 2 -
     *                    Camden
     */
    public CollegeChecking(Profile profile, double balance, int campusCode) {
        super();
        super.holder = profile;
        super.balance = balance;
        super.closed = false;
        this.campusCode = campusCode;
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
        return MONTHLYFEE;
    }

    /**
     * a method to get the name of the account
     *
     * @return a string with the type of account
     */
    @Override
    public String getType() {
        return "College Checking";
    }

    /**
     * a method to get the campus code
     *
     * @return a string of the campus depending on the campusCode
     */
    public String getCampus() {
        if (this.campusCode == NEW_BRUINSWICK) {
            return "NEW_BRUNSWICK";
        } else if (this.campusCode == NEWARK) {
            return "NEWARK";
        } else if (this.campusCode == CAMDEN) {
            return "CAMDEN";
        } else {
            return "";
        }
    }

    /**
     * a method to get the string literal of a college checking account
     */
    @Override
    public String toString() {
        return super.toString() + "::" + this.getCampus();
    }


    /**
     * a method to get the string literal of a college checking account
     * with fees
     * and interest.
     */
    public String toStringFeesAndInterest() {
        return this.toString() + "::fee $" +
                String.format("%.2f", this.fee()) + "::monthly interest $" +
                String.format("%.2f", this.monthlyInterest());
    }

    /**
     * a method to update the campusCode of an account
     *
     * @param campusCode
     */
    public void setCampusCode(int campusCode) {
        this.campusCode = campusCode;
    }
}
