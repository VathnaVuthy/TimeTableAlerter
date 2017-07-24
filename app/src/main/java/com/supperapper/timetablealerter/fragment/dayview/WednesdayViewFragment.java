package com.supperapper.timetablealerter.fragment.dayview;

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
import com.supperapper.timetablealerter.viewholder.DynamicAdapter;
import com.supperapper.timetablealerter.viewholder.SchoolAdapter;

/**
 * Created by User on 5/14/2017.
 */

public class WednesdayViewFragment extends Fragment {
    RecyclerView mRecyclerView ;
    RecyclerView.LayoutManager mLayoutManager;
    DynamicAdapter dynamicAdapter;

    public WednesdayViewFragment() {
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

        DbManager dbManager = DbManager.getInstance(getContext());
        Schedule[] schedules = dbManager.getAllSchdule("tblwednesdayschedule");
        Task[] tasks = dbManager.getTaskForDayview("wednesday");

        dynamicAdapter = new DynamicAdapter(tasks,schedules);
        mRecyclerView.setAdapter(dynamicAdapter);
        return view;
    }
}
