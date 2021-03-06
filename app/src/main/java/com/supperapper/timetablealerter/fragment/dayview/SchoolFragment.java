package com.supperapper.timetablealerter.fragment.dayview;


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

public class SchoolFragment extends Fragment {

    RecyclerView mRecyclerView ;
    RecyclerView.LayoutManager mLayoutManager;
    SchoolAdapter schoolAdapter;

    public SchoolFragment() {
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

        Schedule[] schedules = new Schedule[12];
        schedules[0] = new Schedule("Data Structure","DS","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Chor Chandara");
        schedules[1] = new Schedule("C++","CPP","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Thap Boung");
        schedules[2] = new Schedule("Database","DB","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Var Sovanndara");
        schedules[3] = new Schedule("Data Communication","DC","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Peng Kun");
        schedules[4] = new Schedule("Computer Architecture","CA","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Svoeuy Sivuthary");
        schedules[5] = new Schedule("English","ENG","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Chan Saramphong");
        schedules[6] = new Schedule("Data Structure","DS","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Chor Chandara");
        schedules[7] = new Schedule("C++","CPP","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Thap Boung");
        schedules[8] = new Schedule("Database","DB","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Var Sovanndara");
        schedules[9] = new Schedule("Data Communication","DC","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Peng Kun");
        schedules[10] = new Schedule("Computer Architecture","CA","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Svoeuy Sivuthary");
        schedules[11] = new Schedule("English","ENG","Royal University Of Phnom Penh","201","012 65 85 85","Mon","7:30","8:30","Chan Saramphong");

        schoolAdapter = new SchoolAdapter(schedules);
        mRecyclerView.setAdapter(schoolAdapter);
        return view;
    }

}
