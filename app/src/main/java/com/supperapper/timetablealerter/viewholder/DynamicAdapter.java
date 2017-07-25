package com.supperapper.timetablealerter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.dataset.Schedule;
import com.supperapper.timetablealerter.dataset.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 5/17/2017.
 */

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynamicViewHolder> {
    private Task []tasks;
    private Schedule []schedules;
    private Context context;
    public DynamicAdapter(Schedule []schedules,Task []tasks){
        this.schedules = schedules;
        this.tasks = tasks;
    }

    @Override
    public DynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_dynamic,parent,false);
        DynamicViewHolder dynamicViewHolder = new DynamicViewHolder(rootView);
        return dynamicViewHolder;
    }

    @Override
    public void onBindViewHolder(DynamicViewHolder holder, int position) {
        if(position<tasks.length){
            Task task = this.tasks[position];
            holder.topic.setText(task.getmTopic());
            holder.type.setText(task.getmTaskType());
            holder.note_or_st_et.setText(task.getmNote());
            holder.date.setText(task.getmDate());
//            holder.location_or_room.setText(task.getmLocation().toString());
        }else{
            Schedule schedule = schedules[(schedules.length -1)];
            holder.topic.setText(schedule.getmSubject());
            holder.teacher.setText(schedule.getmTeacher());
            holder.note_or_st_et.setText(schedule.getmStartTime() + "-" + schedule.getmEndTime());
            holder.type.setText(schedule.getmType());
        //    holder.date.setText(schedule.getmDay().toString());
            holder.location_or_room.setText(schedule.getmRoom());
        }
    }
    @Override
    public int getItemCount() {
        int length;
        length = tasks.length + schedules.length;
        return length;
    }


    public class DynamicViewHolder extends RecyclerView.ViewHolder{
        TextView topic ,teacher,note_or_st_et,type,date,location_or_room;
        public DynamicViewHolder(View itemView) {
            super(itemView);
            topic = (TextView)itemView.findViewById(R.id.dynamic_topic);
            teacher = (TextView)itemView.findViewById(R.id.dynamic_teacher);
            note_or_st_et = (TextView)itemView.findViewById(R.id.dynamic_note_or_st_et);
            type = (TextView)itemView.findViewById(R.id.dynamic_type);
            date = (TextView)itemView.findViewById(R.id.dynamic_date);
            location_or_room = (TextView)itemView.findViewById(R.id.dynamic_room_or_location);
        }

    }

}


