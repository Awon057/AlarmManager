package com.awon.alarm.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.awon.alarm.R;
import com.awon.alarm.adapter.AlarmListAdapter;
import com.awon.alarm.data.tables.Alarms;
import com.awon.alarm.repository.AlarmRepository;

import java.util.ArrayList;

public class AlarmListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private AlarmListAdapter adapter;
    private ArrayList<Alarms> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Alarms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        /*records = AlarmRepository.getAlarms(AlarmListActivity.this);
        if (records != null)
            if (records.size() > 0) {
                adapter.setRecords(records, AllEmpReportActivity.this);
            }*/
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

}
