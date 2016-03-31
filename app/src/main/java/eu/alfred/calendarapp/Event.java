package eu.alfred.calendarapp;

import org.json.JSONObject;

/**
 * Created by Gary on 26.02.2016.
 */
public class Event {

    int year;
    int month;
    int day;
    String description;

    public Event (int year, int month, int day, String description) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.description = description;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("title", description);
            obj.put("year", year);
            obj.put("month", month);
            obj.put("day", day);
            obj.put("date", day+"_"+month+"_"+year);
        }
        catch (Exception ex) {
            System.err.println("Json Exception.");
            ex.printStackTrace();
        }
        return obj;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
