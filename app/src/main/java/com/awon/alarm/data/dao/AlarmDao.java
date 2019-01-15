package com.awon.alarm.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.awon.alarm.data.tables.Alarms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Md.Awon-Uz-Zaman on 1/15/2019.
 */
@Dao
public interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAlarm(Alarms alarm);

    @Query("SELECT * FROM Alarms")
    public List<Alarms> getAlarms();

    @Delete
    void deleteAlarm(Alarms alarms);
}
