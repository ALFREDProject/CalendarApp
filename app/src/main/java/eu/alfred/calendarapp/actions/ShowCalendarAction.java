package eu.alfred.calendarapp.actions;

import java.util.Map;

import eu.alfred.api.proxies.interfaces.ICadeCommand;
import eu.alfred.api.speech.Cade;
import eu.alfred.calendarapp.CalendarView;
import eu.alfred.calendarapp.MainActivity;
import eu.alfred.calendarapp.R;

/**
 * Created by Gary on 26.02.2016.
 */
public class ShowCalendarAction implements ICadeCommand {

    MainActivity main;
    Cade cade;
    CalendarView mView;

    public ShowCalendarAction(MainActivity main, Cade cade) {
        this.main = main;
        this.cade = cade;
    }

    @Override
    public void performAction(String s, Map<String, String> map) {
        mView = (CalendarView) main.findViewById(R.id.calendar);
        mView.newDate(map,main);
        cade.sendActionResult(true);
    }

    @Override
    public void performWhQuery(String s, Map<String, String> map) {
    }

    @Override
    public void performValidity(String s, Map<String, String> map) {
    }

    @Override
    public void performEntityRecognizer(String s, Map<String, String> map) {
    }
}


