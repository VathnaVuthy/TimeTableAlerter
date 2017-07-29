package com.supperapper.timetablealerter.fragment.Other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.database.DbManager;
import com.supperapper.timetablealerter.dataset.Schedule;
import com.supperapper.timetablealerter.dataset.Task;
import com.supperapper.timetablealerter.dataset.TaskPagerAdapter;
import com.supperapper.timetablealerter.viewholder.SchoolAdapter;
import com.supperapper.timetablealerter.viewholder.TaskAdapter;

/**
 * Created by User on 5/15/2017.
 */

public class ExamFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TaskAdapter mTaskAdapter;
    Task task;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task,container,false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.task_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);



        DbManager Manager = DbManager.getInstance(getContext());
        Task[] tasks = Manager.getAlltasks(new String[]{"EXAM"});

        mTaskAdapter = new TaskAdapter(tasks);
        mRecyclerView.setAdapter(mTaskAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DbManager dbManager = DbManager.getInstance(getContext());
        Task[] tasks = dbManager.getAlltasks(new String[]{"EXAM"});
        mTaskAdapter = new TaskAdapter(tasks);
        mRecyclerView.setAdapter(mTaskAdapter);
    }
}
