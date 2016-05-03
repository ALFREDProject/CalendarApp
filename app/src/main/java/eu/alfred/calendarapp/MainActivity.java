package eu.alfred.calendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import eu.alfred.api.storage.responses.BucketResponse;
import eu.alfred.calendarapp.actions.InsertEventAction;
import eu.alfred.calendarapp.actions.ShowCalendarAction;
import eu.alfred.calendarapp.actions.ShowEventAction;
import eu.alfred.ui.AppActivity;
import eu.alfred.ui.CircleButton;


public class MainActivity extends AppActivity implements CalendarView.OnCellTouchListener {

    CalendarView mView = null;

    TextView header;
    LinearLayout eventView;
    String android_id;

    final static String SHOW_CALENDAR_ACTION = "ShowCalendarAction";
    final static String INSERT_EVENT_ACTION = "InsertEventAction";
    final static String SHOW_EVENT_ACTION = "ShowEventAction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleButton = (CircleButton) findViewById(R.id.voiceControlBtn);
        circleButton.setOnTouchListener(new CircleTouchListener());

        header = (TextView) findViewById(R.id.header);
        Calendar c = Calendar.getInstance();
        String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        header.setText(month+" "+c.get(Calendar.YEAR));

        mView = (CalendarView)findViewById(R.id.calendar);
        mView.setOnCellTouchListener(this);
        mView.setHeader(header);

        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        eventView = (LinearLayout) findViewById(R.id.eventview);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        cloudStorage.createStructuredBucket(android_id, new BucketResponse() {
            @Override
            public void OnSuccess(JSONObject jsonObject) {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(eu.alfred.calendarapp.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == eu.alfred.calendarapp.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void performAction(String calledAction, Map<String, String> map) {
        switch (calledAction) {
            case SHOW_CALENDAR_ACTION:
                ShowCalendarAction sca = new ShowCalendarAction(this, cade);
                sca.performAction(calledAction, map);
                break;
            case INSERT_EVENT_ACTION:
                InsertEventAction iea = new InsertEventAction(this, cade, cloudStorage, android_id);
                iea.performAction(calledAction, map);
                break;
            case SHOW_EVENT_ACTION:
                ShowEventAction sea = new ShowEventAction(this, cade, cloudStorage, android_id,eventView);
                sea.performAction(calledAction, map);
                cade.sendActionResult(true);
                break;
            default:
                break;
        }
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

    public void onTouch(Cell cell) {
        eventView.removeAllViews();
        int year = mView.getYear();
        int month = mView.getMonth();
        int day = cell.getDayOfMonth();

        // FIX issue 6: make some correction on month and year
        if (cell instanceof CalendarView.GrayCell) {
            // oops, not pick current month...
            if (day < 15) {
                // pick one beginning day? then a next month day
                if (month == 11) {
                    month = 0;
                    year++;
                } else {
                    month++;
                }

            } else {
                // otherwise, previous month
                if (month == 0) {
                    month = 11;
                    year--;
                } else {
                    month--;
                }
            }
        }
        Log.i("CALLLL", day+" "+month+" "+year);
        JSONObject obj = new JSONObject();
		try {
			obj.put("date", day+"_"+month+"_"+year);
		} catch (Exception e) {

        }

        cloudStorage.readJsonArray(android_id, obj, new BucketResponse() {
            @Override
            public void OnSuccess(JSONObject jsonObject) {

            }

            @Override
            public void OnSuccess(JSONArray jsonArray) {
                try {
                    for(int i = 0; i < jsonArray.length(); i++) {
                        TextView tv = new TextView(getApplicationContext());
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(20);
                        tv.setText(jsonArray.getJSONObject(i).getString("title"));
                        eventView.addView(tv);
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
        Intent ret = new Intent();
        ret.putExtra("year", year);
        ret.putExtra("month", month);
        ret.putExtra("day", day);
        this.setResult(RESULT_OK, ret);
        return;
    }

    public TextView getHeader() {
        return header;
    }

}