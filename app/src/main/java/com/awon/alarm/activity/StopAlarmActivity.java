package com.awon.alarm.activity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.awon.alarm.R;

public class StopAlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonStop;
    private Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);

        r = RingtoneManager.getRingtone(StopAlarmActivity.this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        r.play();

        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(this);
    }

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
