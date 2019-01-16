package com.awon.alarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.awon.alarm.util.AlarmReceiver;
import com.awon.alarm.R;
import com.awon.alarm.data.tables.Alarms;
import com.awon.alarm.repository.AlarmRepository;

import java.util.Calendar;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton button;
    private TimePicker timePicker;
    Timer t = new Timer();
    private AlarmManager am;
    private PendingIntent pi;
    private int alarmId;
    private EditText name;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Alarms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        alarmId = getIntent().getIntExtra("alarmId", 0);

        button = (MaterialButton) findViewById(R.id.button);
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        name = (EditText) findViewById(R.id.name);

        button.setOnClickListener(this);
    }

    public void alarmClock() {
        Integer alarmHour = timePicker.getCurrentHour();
        Integer alarmMinute = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);
        calendar.set(Calendar.SECOND, 00);

        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("Id", alarmId);
        pi = PendingIntent.getBroadcast(MainActivity.this, alarmId, intent, 0);
        //am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);

        if (name != null && name.getText().toString().length() > 0) {
            final Alarms alarms = new Alarms();
            alarms.setName(name.getText().toString());
            alarms.setPendingId(alarmId);
            alarms.setTime(calendar.getTimeInMillis() + "");
            AlarmRepository.saveAlarm(alarms, MainActivity.this);

            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pi);
            Toast.makeText(MainActivity.this, "Time is set", Toast.LENGTH_SHORT).show();
            finish();
        } else Toast.makeText(MainActivity.this, "Please give name", Toast.LENGTH_SHORT).show();
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
