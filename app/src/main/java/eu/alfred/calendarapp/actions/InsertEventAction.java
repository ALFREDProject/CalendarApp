package eu.alfred.calendarapp.actions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import eu.alfred.api.proxies.interfaces.ICadeCommand;
import eu.alfred.api.speech.Cade;
import eu.alfred.api.storage.CloudStorage;
import eu.alfred.api.storage.responses.BucketResponse;
import eu.alfred.calendarapp.Event;
import eu.alfred.calendarapp.MainActivity;
import eu.alfred.calendarapp.UtilClass;

/**
 * Created by Gary on 31.03.2016.
 */
public class InsertEventAction implements ICadeCommand {

    MainActivity main;
    Cade cade;
    String android_id;
    CloudStorage cloudStorage;

    public InsertEventAction(MainActivity main, Cade cade, CloudStorage cloudStorage, String android_id) {
        this.main = main;
        this.cade = cade;
        this.cloudStorage = cloudStorage;
        this.android_id = android_id;
    }

    @Override
    public void performAction(String s, Map<String, String> map) {
        JSONObject eventObject = new JSONObject();
        String year = map.get("selected_year").substring(1);
        String month = map.get("selected_month");
        String day = map.get("selected_day").substring(1);
        String name = map.get("selected_event");

        cloudStorage.writeJsonObject(android_id, new Event(Integer.parseInt(year), UtilClass.getMonthInt(month),
                Integer.parseInt(day), name).toJson(), new BucketResponse() {
            @Override
            public void OnSuccess(JSONObject jsonObject) {
                cade.sendActionResult(true);
            }

            @Override
            public void OnSuccess(JSONArray jsonArray) {

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
