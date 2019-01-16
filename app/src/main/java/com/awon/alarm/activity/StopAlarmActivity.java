package com.awon.alarm.activity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.awon.alarm.R;
import com.awon.alarm.data.tables.Alarms;
import com.awon.alarm.repository.AlarmRepository;
import com.awon.alarm.util.PassAlarm;

public class StopAlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonStop;
    private Ringtone r;
    private Alarms alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("Id", 0);

        AlarmRepository.getAlarm(StopAlarmActivity.this, id, passAlarm);

        r = RingtoneManager.getRingtone(StopAlarmActivity.this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        r.play();

        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(this);
    }

    PassAlarm passAlarm = new PassAlarm() {
        @Override
        public void passAlarm(Alarms a) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(a.getName());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStop:
                r.stop();
                finish();
                break;
        }
    }
}
