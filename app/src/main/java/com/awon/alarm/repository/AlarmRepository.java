package com.awon.alarm.repository;

import android.content.Context;

import com.awon.alarm.data.AppDatabase;
import com.awon.alarm.data.tables.Alarms;
import com.awon.alarm.model.AlarmModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Md.Awon-Uz-Zaman on 1/10/2019.
 */
public class AlarmRepository {
    static AppDatabase instance;

    public static void saveAlarm(final Alarms alarmModel, Context context) {
        instance = AppDatabase.getInstance(context);
        instance.userDao().insertAlarm(alarmModel);
    }

    public static List<Alarms> getAlarms(Context context) {
        instance = AppDatabase.getInstance(context);
        return instance.userDao().getAlarms();
    }
}
