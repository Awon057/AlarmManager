package com.awon.alarm.data.tables;

import android.arch.persistence.room.Entity;

/**
 * Created by Md.Awon-Uz-Zaman on 1/15/2019.
 */
@Entity
public class Alarms  {
    String name;
    int pendingId;
    String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPendingId() {
        return pendingId;
    }

    public void setPendingId(int pendingId) {
        this.pendingId = pendingId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
