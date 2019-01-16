package com.awon.alarm.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.awon.alarm.activity.StopAlarmActivity;

/**
 * Created by Md.Awon-Uz-Zaman on 1/9/2019.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, StopAlarmActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("Id",intent.getIntExtra("Id", 0));
        context.startActivity(i);
    }
}
