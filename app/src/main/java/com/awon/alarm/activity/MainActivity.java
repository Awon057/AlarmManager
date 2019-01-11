package com.awon.alarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.awon.alarm.Alarm;
import com.awon.alarm.R;

import java.util.Calendar;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TimePicker timePicker;
    Timer t = new Timer();
    long time = 0;
    private AlarmManager am;
    private PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        timePicker = (TimePicker) findViewById(R.id.time_picker);

        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, Alarm.class);
        pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        button.setOnClickListener(this);
    }

    public void alarmClock() {
        Integer alarmHour = timePicker.getCurrentHour();
        Integer alarmMinute = timePicker.getCurrentMinute();

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);

        Toast.makeText(MainActivity.this, "Time is set", Toast.LENGTH_SHORT).show();

        am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
        //am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                alarmClock();
                break;
        }
    }


}
