package com.supperapper.timetablealerter.dataset;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.supperapper.timetablealerter.fragment.AboutUsFragment;
import com.supperapper.timetablealerter.fragment.Other.ExamFragment;
import com.supperapper.timetablealerter.fragment.Other.MeetingFragment;
import com.supperapper.timetablealerter.fragment.Other.OtherFragment;
import com.supperapper.timetablealerter.fragment.Other.RegularActivityFragment;
import com.supperapper.timetablealerter.fragment.Other.WeddingFragment;
import com.supperapper.timetablealerter.fragment.school.SchoolFragment;
import com.supperapper.timetablealerter.fragment.SettingFragment;
import com.supperapper.timetablealerter.fragment.TaskFragment;
import com.supperapper.timetablealerter.fragment.WeekViewFragment;

/**
 * Created by User on 5/14/2017.
 */

public class TaskPagerAdapter extends FragmentPagerAdapter {

    String tabTitles[] = new String[]{"Exam", "Meeting", "Wedding", "R.Activity", "Other"};
    Context context;

    public TaskPagerAdapter(android.support.v4.app.FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    public Fragment getItem(int position){
        switch (position) {
            case 0:
                return new ExamFragment();
            case 1:
                return new MeetingFragment();
            case 2:
                return new WeddingFragment();
            case 3:
                return new RegularActivityFragment();
            case 4:
                return new OtherFragment();
        }

        return null;
        //    return null; Default Return
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }
}