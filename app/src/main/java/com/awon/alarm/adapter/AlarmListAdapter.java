package com.awon.alarm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awon.alarm.R;
import com.awon.alarm.data.tables.Alarms;
import com.awon.alarm.util.PassAlarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Md.Awon-Uz-Zaman on 1/10/2019.
 */
public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {

    private Context context;
    private List<Alarms> alarms;
    private PassAlarm passAlarm;
    private PassAlarm editAlarm;

    public void setRecords(List<Alarms> alarms, Context context, PassAlarm passAlarm, PassAlarm editAlarm) {
        this.context = context;
        this.alarms = alarms;
        this.passAlarm = passAlarm;
        this.editAlarm = editAlarm;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alarm_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.alarmName.setText(alarms.get(i).getName());
        String time = DateUtils.formatDateTime(context, Long.parseLong(alarms.get(i).getTime()), DateUtils.FORMAT_SHOW_TIME);
        viewHolder.alarmTime.setText(time);
    }

    @Override
    public int getItemCount() {
        if (alarms != null)
            return alarms.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView alarmName;
        private final TextView alarmTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmName = (TextView) itemView.findViewById(R.id.alarm_name);
            alarmTime = (TextView) itemView.findViewById(R.id.alarm_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editAlarm.passAlarm(alarms.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    passAlarm.passAlarm(alarms.get(getAdapterPosition()));
                    return false;
                }
            });
        }
    }
}
