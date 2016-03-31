package eu.alfred.calendarapp;

import java.util.Calendar;

/**
 * Created by Gary on 31.03.2016.
 */
public class UtilClass {
    public static int getMonthInt(String month) {
        switch (month) {
            case "january":
                return Calendar.JANUARY;
            case "february":
                return Calendar.FEBRUARY;
            case "march":
                return Calendar.MARCH;
            case "april":
                return Calendar.APRIL;
            case "may":
                return Calendar.MAY;
            case "june":
                return Calendar.JUNE;
            case "july":
                return Calendar.JULY;
            case "august":
                return Calendar.AUGUST;
            case "september":
                return Calendar.SEPTEMBER;
            case "october":
                return Calendar.OCTOBER;
            case "november":
                return Calendar.NOVEMBER;
            case "december":
                return Calendar.DECEMBER;
            default:
                return 0;
        }
    }
}