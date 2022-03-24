package src.bank;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * A class that deals with dates.
 * checks the validity of the date and can give user a string of a
 * specified date
 * or the current date.
 *
 * @author Raymond Phillips, Xiaoxuan Chen
 */
public class Date implements Comparable<Date> {

    public static final int QUADRENINIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    public static final int JANUARY = 1;
    public static final int FEBURARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;

    public static final int MONTHSTART = 0;
    public static final int FEBLEAP = 29;
    public static final int FEBREGULAR = 28;
    public static final int MONTHENDTHIRTY = 30;
    public static final int MONTHENDTHIRTYONE = 31;

    private int year;
    private int month;
    private int day;

    /**
     * constructor for the date class that takes in a string as an input
     * that uses the
     * StringTokenizer to break down the string into it's month, date, and
     * year and
     * uses the Integer wrapper class to assigns private local variables
     * the values of
     * month, date, and year.
     *
     * @param date, a string in the format of "month/date/year"
     */
    public Date(String date) {
        StringTokenizer st1 = new StringTokenizer(date, "/");

        month = Integer.parseInt(st1.nextToken());
        day = Integer.parseInt(st1.nextToken());
        year = Integer.parseInt(st1.nextToken());

    }

    /**
     * constructor for the date class
     * sets the year, date, and month to the current date year and month.
     */
    public Date() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    public static void main(String[] args) {
        Date d = new Date();
        Date c = new Date("12/2/2022");
        System.out.println(d.toString());
        System.out.println(c.compareTo(d));
        System.out.println(d.isValid());
        System.out.println(c.isValid());
    }

    /**
     * A method to check to see if the date is a valid date.
     *
     * @return true if the date is a valid date, otherwise returns false.
     */
    public boolean isValid() {
        if (this.day < MONTHSTART || this.month > DECEMBER ||
                this.month < JANUARY) {
            return false;
        } else {
            if (this.month == JANUARY || this.month == MARCH ||
                    this.month == MAY || this.month == JULY ||
                    this.month == AUGUST || this.month == OCTOBER ||
                    this.month == DECEMBER) {
                if (this.day > MONTHENDTHIRTYONE) {
                    return false;
                }
            } else if (this.month == APRIL || this.month == JUNE ||
                    this.month == SEPTEMBER || this.month == NOVEMBER) {
                if (this.day > MONTHENDTHIRTY) {
                    return false;
                }
            } else if (this.month == FEBURARY) {
                if (this.year % QUADRENINIAL == 0) {
                    if ( this.year % CENTENNIAL == 0) {
                        if (this.year % QUATERCENTENNIAL == 0) {
                            if (this.day > FEBLEAP) {
                                return false;
                            }
                        }
                    }
                } else if (this.day > FEBREGULAR) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * a getter method to get the year
     *
     * @return the year of this object.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * a getter method to get the month
     *
     * @return the month of this object.
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * a getter method to get the day
     *
     * @return the day of this object.
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Comparesion between two date objects.
     *
     * @param date, a Date object
     * @return an int of lexicographically difference
     */
    @Override
    public int compareTo(Date date) {
        //        return this.toString().compareTo(date.toString());
        int thisYear = this.getYear();
        int thisMonth = this.getMonth();
        int thisDay = this.getDay();

        int secondYear = date.getYear();
        int secondMonth = date.getMonth();
        int secondDay = date.getDay();

        if (thisYear == secondYear && thisMonth == secondMonth &&
                thisDay == secondDay) {
            return 0;
        } else if (thisYear != secondYear) {
            return secondYear - thisYear;
        } else if (thisYear == secondYear && thisMonth == secondMonth) {
            return secondDay - thisDay;
        } else {
            return secondMonth - thisMonth;
        }
    }

    /**
     * gives a string literal of a date object.
     *
     * @return a string literal of the date object.
     */
    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }
}