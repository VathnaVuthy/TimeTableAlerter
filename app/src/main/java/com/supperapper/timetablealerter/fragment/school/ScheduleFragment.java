package com.supperapper.timetablealerter.fragment.school;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.database.DbManager;
import com.supperapper.timetablealerter.dataset.Schedule;
import com.supperapper.timetablealerter.viewholder.SchoolAdapter;

public class ScheduleFragment extends Fragment {
    RecyclerView mRecyclerView ;
    RecyclerView.LayoutManager mLayoutManager;
    SchoolAdapter schoolAdapter;

    Schedule[] schedules;
    int day;
    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("Schedule", "onCreateview");
        View view =  inflater.inflate(R.layout.fragment_school, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.school_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        schoolAdapter = new SchoolAdapter(schedules);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(schoolAdapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DbManager dbManager = DbManager.getInstance(getContext());
        Schedule[] schedules = dbManager.getAllSchdule("tblmondayschedule");
        schoolAdapter = new SchoolAdapter(schedules);
        mRecyclerView.setAdapter(schoolAdapter);
    }

    public void setDay(int day) {
        this.day = day;

        Log.d("Schedule", "setDay");

        DbManager dbManager = DbManager.getInstance(getContext());

        String name;
        if (day == 0){

             name = "tblmondayschedule";
        } else if (day ==1){

            name = "tbltuesdayschedule";

        } else if (day == 2){

            name = "tblwednesdayschedule";

        } else if (day == 3){

            name = "tblthursdayschedule";
        } else if (day == 4){

            name = "tblfridayschedule";

        } else if (day == 5){

            name = "tblsaturdayschedule";
        } else {

            name = "tblsundayschedule";
        }

        Schedule[] schedules = dbManager.getAllSchdule(name);
        this.schedules = schedules;


    }
}