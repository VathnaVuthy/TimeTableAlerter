package com.supperapper.timetablealerter.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.dataset.Schedule;
import com.supperapper.timetablealerter.dataset.Task;
import com.supperapper.timetablealerter.viewholder.DynamicAdapter;
import com.supperapper.timetablealerter.viewholder.TaskAdapter;


public class DayViewFragment extends Fragment {


    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public DayViewFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task,container,false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.task_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Task[] tasks = new Task[3];
        tasks[0] = new Task("Delete Binary Search Tree","Data Structure","Exam","12-02-2017","RUPP","Be On Time");
        tasks[1] = new Task("Lesson 3-8","English","Exam","12-02-2017","RUPP","Be On Time");
        tasks[2] = new Task("Memo","English","Exam","12-02-2017","CamAsean" ,"Be On Time");

        Schedule[] schedules = new Schedule[4];
        schedules[0] = new Schedule("Java Programming J2SE","Java","Korea Software HRD","BTB","012 123 123","Saturday","7:00","11:00","Voy Ratana");
        schedules[1] = new Schedule("Computer Architecture","CA","Royal University Of Phnom Penh","201","012 123 123","Saturday","14:00","15:30","Svoeuy Sivuthary");
        schedules[2] = new Schedule("C++","CPP","Royal University Of Phnom Penh","201","012 123 123","Saturday","15:45","17:15","Thap Boung");
        schedules[3] = new Schedule("Thai Language","TH","Bangkok Thai School","201","012 123 123","Saturday","17:30","18:30","Keng");

   //   //  DynamicAdapter dynamicAdapter = new DynamicAdapter(tasks,schedules);
  //      mRecyclerView.setAdapter(dynamicAdapter);
        return view;
    }
}
