package com.supperapper.timetablealerter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.dataset.Task;

/**
 * Created by User on 5/15/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Task [] tasks ;
    Context context;

    public TaskAdapter(Task []tasks){
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_items_task,parent,false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(rootView);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks[position];
        holder.tv_topic.setText(task.getmTopic().toString());
        holder.tv_type.setText(task.getmTaskType().toString());
        holder.tv_note.setText(task.getmNote().toString());
        holder.tv_date.setText(task.getmDate().toString());
   //     holder.tv_location.setText(task.getmLocation().toString());
    }

    @Override
    public int getItemCount() {
        return tasks.length;
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView tv_topic,tv_type,tv_note,tv_date,tv_location;

        public TaskViewHolder(View itemView) {
            super(itemView);

            tv_topic = (TextView)itemView.findViewById(R.id.tv_task_topic);
            tv_type = (TextView)itemView.findViewById(R.id.tv_task_type);
            tv_note = (TextView)itemView.findViewById(R.id.tv_task_note);
            tv_date = (TextView)itemView.findViewById(R.id.tv_task_date);
            tv_location = (TextView)itemView.findViewById(R.id.tv_task_location);
        }
    }

}

