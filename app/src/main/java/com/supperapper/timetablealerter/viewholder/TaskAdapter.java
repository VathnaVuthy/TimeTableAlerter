package com.supperapper.timetablealerter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.activity.TaskDetailActivity;
import com.supperapper.timetablealerter.database.DbManager;
import com.supperapper.timetablealerter.dataset.MapClass;
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
        DbManager dbManager = DbManager.getInstance(context);
        int mapId = dbManager.getMapIdByTaskId(Integer.parseInt(task.getmID()));
        MapClass map = dbManager.getMapFromDb(mapId);
        holder.tv_location.setText(map.getName());
    }

    @Override
    public int getItemCount() {
        return tasks.length;
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_topic,tv_type,tv_note,tv_date,tv_location;

        public TaskViewHolder(View itemView) {
            super(itemView);

            tv_topic = (TextView)itemView.findViewById(R.id.tv_task_topic);
            tv_type = (TextView)itemView.findViewById(R.id.tv_task_type);
            tv_note = (TextView)itemView.findViewById(R.id.tv_task_note);
            tv_date = (TextView)itemView.findViewById(R.id.tv_task_date);
            tv_location = (TextView)itemView.findViewById(R.id.tv_task_location);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Task task = tasks[position];
            Intent intent = new Intent(context, TaskDetailActivity.class);
            intent.putExtra("topic",task.getmTopic());
            intent.putExtra("subject", task.getmSubject());
            intent.putExtra("tasktype",task.getmTaskType());
            intent.putExtra("date",task.getmDate());
            intent.putExtra("note",task.getmNote());
            intent.putExtra("id", task.getmID());
            String id = task.getmID();
            DbManager dbManager = DbManager.getInstance(context);
            int mapId = dbManager.getMapIdByTaskId(Integer.parseInt(id));
            MapClass mapClass = dbManager.getMapFromDb(mapId);
            intent.putExtra("mapId",mapClass.getId());
            intent.putExtra("mapName",mapClass.getName());
            intent.putExtra("mapAddress",mapClass.getAddress());
            intent.putExtra("mapPhone",mapClass.getPhone());
            intent.putExtra("mapWebsite",mapClass.getWebsite());
            intent.putExtra("mapLat",mapClass.getLat());
            intent.putExtra("mapLang",mapClass.getLang());
            dbManager.close();

            context.startActivity(intent);


        }
    }

}

