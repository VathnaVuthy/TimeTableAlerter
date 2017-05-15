package com.supperapper.timetablealerter.fragment.Other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.dataset.Task;
import com.supperapper.timetablealerter.viewholder.TaskAdapter;

/**
 * Created by User on 5/15/2017.
 */

public class OtherFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TaskAdapter mTaskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task,container,false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.task_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Task[] tasks = new Task[3];
        tasks[0] = new Task("Delete Binary Search Tree","Data Structure","Exam","12-02-2017","RUPP","Be On Time");
        tasks[1] = new Task("Lesson 3-8","English","Exam","12-02-2017","RUPP","Be On Time");
        tasks[2] = new Task("Memo","English","Exam","12-02-2017","CamAsean" ,"Be On Time");

        mTaskAdapter = new TaskAdapter(tasks);
        mRecyclerView.setAdapter(mTaskAdapter);
        return view;
    }
}
