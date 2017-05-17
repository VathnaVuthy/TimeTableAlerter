package com.supperapper.timetablealerter.dataset;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.supperapper.timetablealerter.fragment.DayViewFragment;
import com.supperapper.timetablealerter.fragment.dayview.FridayViewFragment;
import com.supperapper.timetablealerter.fragment.dayview.MondayViewFragment;
import com.supperapper.timetablealerter.fragment.dayview.SaturdayViewFragment;
import com.supperapper.timetablealerter.fragment.dayview.SundayViewFragment;
import com.supperapper.timetablealerter.fragment.dayview.ThursdayViewFragment;
import com.supperapper.timetablealerter.fragment.dayview.TuesdayViewFragment;
import com.supperapper.timetablealerter.fragment.dayview.WednesdayViewFragment;

/**
 * Created by User on 5/17/2017.
 */

public class DynamicPagerAdapter extends FragmentStatePagerAdapter {

    String tabTitles[] = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday","Sunday"};
    Context context;

    public DynamicPagerAdapter(android.support.v4.app.FragmentManager fm, Context context) {
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
                return new MondayViewFragment();
            case 1:
                return new TuesdayViewFragment();
            case 2:
                return new WednesdayViewFragment();
            case 3:
                return new ThursdayViewFragment();
            case 4:
                return new FridayViewFragment();
            case 5:
                return new SaturdayViewFragment();
            case 6:
                return new SundayViewFragment();
        }

        return null;
        //    return null; Default Return
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }
}
