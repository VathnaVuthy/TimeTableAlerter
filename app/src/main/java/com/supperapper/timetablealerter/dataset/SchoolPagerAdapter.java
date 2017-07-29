package com.supperapper.timetablealerter.dataset;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.supperapper.timetablealerter.fragment.DayViewFragment;
import com.supperapper.timetablealerter.fragment.school.FridayFragment;
import com.supperapper.timetablealerter.fragment.school.MondayFragment;
import com.supperapper.timetablealerter.fragment.school.SaturdayFragment;
import com.supperapper.timetablealerter.fragment.school.ScheduleFragment;
import com.supperapper.timetablealerter.fragment.school.SchoolFragment;
import com.supperapper.timetablealerter.fragment.school.SundayFragment;
import com.supperapper.timetablealerter.fragment.school.ThursdayFragment;
import com.supperapper.timetablealerter.fragment.school.TuesdayFragment;
import com.supperapper.timetablealerter.fragment.school.WednesdayFragment;

/**
 * Created by User on 5/14/2017.
 */

public class SchoolPagerAdapter extends FragmentStatePagerAdapter {

        String tabTitles[] = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday","Sunday"};
        Context context;

        public SchoolPagerAdapter(android.support.v4.app.FragmentManager fm, Context context) {
            super(fm);
            this.context = context;

        }
        @Override
        public int getCount() {
            return tabTitles.length;
        }

        public Fragment getItem(int position){

//            scheduleFragment.setDay(position);
//            ScheduleFragment scheduleFragment = new ScheduleFragment();
//            scheduleFragment.setDay(position);
//            return scheduleFragment;
//
        switch (position) {
                case 0:
                    return new MondayFragment();
                case 1:
                    return new TuesdayFragment();
                case 2:
                    return new WednesdayFragment();
                case 3:
                    return new ThursdayFragment();
                case 4:
                    return new FridayFragment();
                case 5:
                    return new SaturdayFragment();
                case 6:
                    return new SundayFragment();
            }

                return null;
            //Default Return
        }

        @Override
        public CharSequence getPageTitle(int position){
            return tabTitles[position];
        }

    }
