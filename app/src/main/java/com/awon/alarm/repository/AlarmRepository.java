package com.awon.alarm.repository;

import android.content.Context;

import com.awon.alarm.activity.AlarmListActivity;
import com.awon.alarm.data.AppDatabase;
import com.awon.alarm.data.tables.Alarms;
import com.awon.alarm.model.AlarmModel;
import com.awon.alarm.util.GetAlarms;
import com.awon.alarm.util.PassAlarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Md.Awon-Uz-Zaman on 1/10/2019.
 */
public class AlarmRepository {
    static AppDatabase instance;
    static List<Alarms> alarmsList;

    public static void saveAlarm(final Alarms alarmModel, final Context context) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                instance = AppDatabase.getInstance(context);
                instance.userDao().insertAlarm(alarmModel);
            }
        };
        new Thread(runnable).start();
    }

    public static void getAlarms(final Context context, final GetAlarms getAlarms) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                instance = AppDatabase.getInstance(context);
                getAlarms.getAlarms(instance.userDao().getAlarms());
            }
        };
        new Thread(runnable).start();
    }

    public static void deleteAlarm(final Alarms a, final Context context) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                instance = AppDatabase.getInstance(context);
                instance.userDao().deleteAlarm(a);
            }
        };
        new Thread(runnable).start();
    }

    public static void getAlarm(final Context context, final int id, final PassAlarm passAlarm) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                instance = AppDatabase.getInstance(context);
                passAlarm.passAlarm(instance.userDao().getAlarm(id + ""));
            }
        };
        new Thread(runnable).start();
    }
}
