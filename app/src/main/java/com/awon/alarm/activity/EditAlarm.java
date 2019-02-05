package com.awon.alarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.awon.alarm.R;
import com.awon.alarm.data.tables.Alarms;
import com.awon.alarm.repository.AlarmRepository;
import com.awon.alarm.util.AlarmReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EditAlarm extends AppCompatActivity implements View.OnClickListener {

    private TimePicker editTimePicker;
    private EditText editAlarmName;
    private MaterialButton cancelButton;
    private MaterialButton editButton;
    private Alarms a;
    private AlarmManager am;
    private Intent intent;
    private PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        a = (Alarms) getIntent().getSerializableExtra("alarm");

        editTimePicker = (TimePicker) findViewById(R.id.edit_time_picker);
        editAlarmName = (EditText) findViewById(R.id.edit_alarm_name);
        cancelButton = (MaterialButton) findViewById(R.id.cancel_button);
        editButton = (MaterialButton) findViewById(R.id.edit_button);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(a.getTime()));

        editAlarmName.setText(a.getName());
        editTimePicker.setHour(c.get(Calendar.HOUR_OF_DAY));
        editTimePicker.setMinute(c.get(Calendar.MINUTE));

        cancelButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_button:
                finish();
                break;
            case R.id.edit_button:
                editAlarm();
                break;
        }
    }

    private void editAlarm() {
        Integer alarmHour = editTimePicker.getCurrentHour();
        Integer alarmMinute = editTimePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);
        calendar.set(Calendar.SECOND, 00);

        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(EditAlarm.this, AlarmReceiver.class);
        intent.putExtra("Id", a.getId());
        pi = PendingIntent.getBroadcast(EditAlarm.this, a.getId(), intent, 0);

        Alarms alarms = new Alarms();
        alarms.setId(a.getId());
        if (editAlarmName != null && editAlarmName.getText().toString().length() > 0) {
            alarms.setName(editAlarmName.getText().toString());
            alarms.setTime(calendar.getTimeInMillis() + "");
            AlarmRepository.updateAlarm(EditAlarm.this, alarms);

            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pi);
            Toast.makeText(EditAlarm.this, "Time is updated", Toast.LENGTH_SHORT).show();
            finish();
        } else Toast.makeText(EditAlarm.this, "Please give name", Toast.LENGTH_SHORT).show();
    }
}
