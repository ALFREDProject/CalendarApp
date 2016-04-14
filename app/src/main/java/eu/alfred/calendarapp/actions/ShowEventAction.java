package eu.alfred.calendarapp.actions;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import eu.alfred.api.proxies.interfaces.ICadeCommand;
import eu.alfred.api.speech.Cade;
import eu.alfred.api.storage.CloudStorage;
import eu.alfred.api.storage.responses.BucketResponse;
import eu.alfred.calendarapp.CalendarView;
import eu.alfred.calendarapp.MainActivity;
import eu.alfred.calendarapp.R;
import eu.alfred.calendarapp.UtilClass;

/**
 * Created by Gary on 14.04.2016.
 */
public class ShowEventAction implements ICadeCommand {

    MainActivity main;
    Cade cade;
    String android_id;
    CloudStorage cloudStorage;
    LinearLayout eventView;
    CalendarView mView;

    public ShowEventAction(MainActivity main, Cade cade, CloudStorage cloudStorage, String android_id, LinearLayout eventView) {
        this.main = main;
        this.cade = cade;
        this.cloudStorage = cloudStorage;
        this.android_id = android_id;
        this.eventView = eventView;
    }

    @Override
    public void performAction(String s, Map<String, String> map) {

        String year = map.get("selected_year");
        String month = map.get("selected_month");
        String day = map.get("selected_day");

        mView = (CalendarView) main.findViewById(R.id.calendar);
        mView.newDate(map,main);

        JSONObject obj = new JSONObject();
        try {
            obj.put("date", Integer.parseInt(day)+"_"+UtilClass.getMonthInt(month)+"_"+Integer.parseInt(year));
        } catch (Exception e) {

        }

        cloudStorage.readJsonArray(android_id, obj, new BucketResponse() {
            @Override
            public void OnSuccess(JSONObject jsonObject) {

            }

            @Override
            public void OnSuccess(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TextView tv = new TextView(main.getApplicationContext());
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(20);
                        tv.setText(jsonArray.getJSONObject(i).getString("title"));
                        eventView.addView(tv);
                        cade.sendActionResult(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnSuccess(byte[] bytes) {

            }

            @Override
            public void OnError(Exception e) {

            }
        });
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
