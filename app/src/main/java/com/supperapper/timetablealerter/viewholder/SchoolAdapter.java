package com.supperapper.timetablealerter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.dataset.Schedule;

/**
 * Created by User on 4/30/2017.
 */

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder> {
    private Schedule[] schedules;
    Context context;
    public SchoolAdapter(Schedule[] schedule){
        this.schedules = schedule;
    }
    @Override
    public SchoolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_schedule,parent,false);
        SchoolViewHolder schoolViewHolder = new SchoolViewHolder(rootview);
        context = parent.getContext();
        return schoolViewHolder;
    }

    @Override
    public void onBindViewHolder(SchoolViewHolder holder, int position) {
        Schedule schedule = schedules[position];
        holder.tv_subject.setText(schedule.getmSubject().toString());
        holder.tv_teacher.setText(schedule.getmTeacher());
        holder.tv_st_et.setText(schedule.getmStartTime() + " To " + schedule.getmEndTime());
        holder.tv_date.setText(schedule.getmDay());
        holder.tv_room.setText(schedule.getmRoom());
    }

    @Override
    public int getItemCount() {
        return schedules.length;
    }

    public class SchoolViewHolder extends RecyclerView.ViewHolder{
        TextView tv_subject,tv_teacher,tv_st_et,tv_date,tv_room;
        public SchoolViewHolder(View itemView) {
            super(itemView);
            tv_subject = (TextView)itemView.findViewById(R.id.tv_school_topic);
            tv_teacher = (TextView)itemView.findViewById(R.id.tv_school_teacher);
            tv_st_et = (TextView)itemView.findViewById(R.id.tv_school_st_et);
            tv_date = (TextView)itemView.findViewById(R.id.tv_school_date);
            tv_room = (TextView)itemView.findViewById(R.id.tv_school_room);
        }
    }
}
