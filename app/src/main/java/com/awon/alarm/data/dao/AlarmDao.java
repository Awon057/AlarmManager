package com.awon.alarm.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.awon.alarm.data.tables.Alarms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Md.Awon-Uz-Zaman on 1/15/2019.
 */
@Dao
public interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlarm(Alarms alarm);

    @Query("SELECT * FROM Alarms")
    List<Alarms> getAlarms();

    @Query("SELECT * FROM Alarms WHERE pendingId LIKE :id ")
    Alarms getAlarm(String id);

    @Query("UPDATE Alarms SET name=:name, time=:time WHERE id = :id")
    void updateAlarm(String name, String time, String id);

    @Delete
    void deleteAlarm(Alarms alarms);
}
