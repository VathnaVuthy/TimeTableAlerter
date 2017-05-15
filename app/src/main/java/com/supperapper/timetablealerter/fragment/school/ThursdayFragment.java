package com.supperapper.timetablealerter.fragment.school;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.dataset.Schedule;
import com.supperapper.timetablealerter.viewholder.SchoolAdapter;

/**
 * Created by User on 5/14/2017.
 */

public class ThursdayFragment extends Fragment {
    RecyclerView mRecyclerView ;
    RecyclerView.LayoutManager mLayoutManager;
    SchoolAdapter schoolAdapter;

    public ThursdayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_school, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.school_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Schedule[] schedules = new Schedule[4];
        schedules[0] = new Schedule("Web Development","WD","STEP","214","012 123 123","Thursday","7:00","11:00","Alex");
        schedules[1] = new Schedule("Data Structure And Algorithms","DS","Royal University Of Phnom Penh","201","012 123 123","Thursday","14:00","15:30","Chor Chandara");
        schedules[2] = new Schedule("C++","CPP","Royal University Of Phnom Penh","201","012 123 123","Thursday","15:45","17:15","Thap Boung");
        schedules[3] = new Schedule("Thai Language","TH","Bangkok Thai School","201","012 123 123","Thursday","17:30","18:30","Keng");


        schoolAdapter = new SchoolAdapter(schedules);
        mRecyclerView.setAdapter(schoolAdapter);
        return view;
    }
}
