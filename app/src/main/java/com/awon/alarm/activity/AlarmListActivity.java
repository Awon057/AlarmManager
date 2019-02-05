package com.awon.alarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.awon.alarm.R;
import com.awon.alarm.adapter.AlarmListAdapter;
import com.awon.alarm.data.tables.Alarms;
import com.awon.alarm.repository.AlarmRepository;
import com.awon.alarm.util.AlarmReceiver;
import com.awon.alarm.util.GetAlarms;
import com.awon.alarm.util.PassAlarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private AlarmListAdapter adapter;
    private List<Alarms> records;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Alarms");

        mRecyclerView = (RecyclerView) findViewById(R.id.alarm_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setHasFixedSize(true);
        adapter = new AlarmListAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        AlarmRepository.getAlarms(AlarmListActivity.this, getAlarms);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_alarm:
                Intent intent = new Intent(AlarmListActivity.this, MainActivity.class);
                intent.putExtra("alarmId", records.size() + 1);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    GetAlarms getAlarms = new GetAlarms() {
        @Override
        public void getAlarms(final List<Alarms> alarmsList) {
            if (alarmsList != null)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        records = alarmsList;
                        adapter.setRecords(alarmsList, AlarmListActivity.this, passAlarm, editAlarm);
                    }
                });
        }
    };

    PassAlarm editAlarm = new PassAlarm() {
        @Override
        public void passAlarm(Alarms a) {
            Intent intent = new Intent(AlarmListActivity.this, EditAlarm.class);
            intent.putExtra("alarm", a);
            startActivity(intent);
        }
    };

    PassAlarm passAlarm = new PassAlarm() {
        @Override
        public void passAlarm(Alarms a) {
            showDialog(a);
        }
    };

    private void showDialog(final Alarms a) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(AlarmListActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(AlarmListActivity.this);
        }
        builder.setMessage("Do you want to delete the Alarm?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAlarm(a);
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteAlarm(final Alarms a) {
        AlarmRepository.deleteAlarm(a, AlarmListActivity.this, getAlarms);
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, a.getPendingId(), intent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
