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

import com.awon.alarm.AlarmReceiver;
import com.awon.alarm.R;
import com.awon.alarm.data.tables.Alarms;
import com.awon.alarm.repository.AlarmRepository;

import java.util.Calendar;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TimePicker timePicker;
    Timer t = new Timer();
    long time = 0;
    private AlarmManager am;
    private PendingIntent pi;
    private int alarmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmId = getIntent().getIntExtra("alarmId", 0);

        button = (Button) findViewById(R.id.button);
        timePicker = (TimePicker) findViewById(R.id.time_picker);

        button.setOnClickListener(this);
    }

    public void alarmClock() {
        Integer alarmHour = timePicker.getCurrentHour();
        Integer alarmMinute = timePicker.getCurrentMinute();

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);

        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        pi = PendingIntent.getBroadcast(MainActivity.this, alarmId, intent, 0);

        Toast.makeText(MainActivity.this, "Time is set", Toast.LENGTH_SHORT).show();

        //am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        final Alarms alarms = new Alarms();
        alarms.setName("");
        alarms.setPendingId(alarmId);
        alarms.setTime(calendar.getTimeInMillis() + "");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                AlarmRepository.saveAlarm(alarms, MainActivity.this);
            }
        };
        new Thread(runnable).start();
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
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
