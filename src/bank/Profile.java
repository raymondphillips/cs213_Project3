package bank;

/**
 * A profile class that represents a users profile
 *
 * @author Raymond Phillips, Xiaoxuan Chen
 */
public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * a constructor for the Profile class takes in two strings and a date
     * object
     *
     * @param fname a string which represents the first name of profile holder
     * @param lname a string which represents the last name of the profile
     *              holder
     * @param date  object that represents the date of birth of the patient.
     */
    public Profile(String fname, String lname, Date date) {
        this.fname = fname;
        this.lname = lname;
        this.dob = date;
    }

    public static void main(String[] args) {
        Date da = new Date();
        Profile p = new Profile("John", "Adams", da);
        Date d1 = new Date();
        Profile p1 = new Profile("joe", "mama", d1);
        System.out.println(p.toString());
        System.out.println(p1.toString());
    }

    /**
     * an overridden method that returns the string literal of the patient
     * object.
     *
     * @return the string literal in the form of "firstName lastName DOB:
     * "mm/dd/year""
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * a method to compare two profiles
     *
     * @param obj a profile object for comparison
     * @return true if the profiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(((Profile) obj).toString());
    }

    /**
     * a method to check the validity of a dob
     *
     * @return true if date is not >= current date
     */
    public boolean isValidDOB() {
        if (this.dob.compareTo(new Date()) >= 0 && this.dob.isValid()) {
            return false;
        } else {
            return true;
        }
    }
}
