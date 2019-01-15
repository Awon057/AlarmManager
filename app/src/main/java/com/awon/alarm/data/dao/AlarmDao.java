package com.awon.alarm.data.dao;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.awon.alarm.data.tables.Alarms;

import java.util.List;

/**
 * Created by Md.Awon-Uz-Zaman on 1/15/2019.
 */
public interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAlarm(Alarms alarm);
}
