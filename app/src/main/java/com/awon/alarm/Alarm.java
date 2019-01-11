package com.awon.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.awon.alarm.activity.StopAlarmActivity;

/**
 * Created by Md.Awon-Uz-Zaman on 1/9/2019.
 */
public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startActivity(new Intent(context, StopAlarmActivity.class));
    }
}
