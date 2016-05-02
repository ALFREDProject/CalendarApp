package eu.alfred.calendarapp;

import java.util.Calendar;

/**
 * Created by Gary on 31.03.2016.
 */
public class UtilClass {
    public static int getMonthInt(String month) {
        switch (month) {
            case "january":
            case "januari":
            case "janvier":
                return Calendar.JANUARY;
            case "february":
            case "februari":
            case "février":
                return Calendar.FEBRUARY;
            case "march":
            case "maart":
            case "mars":
                return Calendar.MARCH;
            case "april":
            case "avril":
                return Calendar.APRIL;
            case "may":
            case "mei":
            case "mai":
                return Calendar.MAY;
            case "june":
            case "juni":
            case "juin":
                return Calendar.JUNE;
            case "july":
            case "juli":
            case "juillet":
                return Calendar.JULY;
            case "august":
            case "augustus":
            case "août":
                return Calendar.AUGUST;
            case "september":
            case "septembre":
                return Calendar.SEPTEMBER;
            case "october":
            case "oktober":
            case "octobre":
                return Calendar.OCTOBER;
            case "november":
            case "novembre":
                return Calendar.NOVEMBER;
            case "december":
            case "décembre":
                return Calendar.DECEMBER;
            default:
                return 0;
        }
    }
}