package com.supperapper.timetablealerter.activity;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.fragment.AboutUsFragment;
import com.supperapper.timetablealerter.fragment.DayViewFragment;
import com.supperapper.timetablealerter.fragment.MeetingFragment;
import com.supperapper.timetablealerter.fragment.SchoolFragment;
import com.supperapper.timetablealerter.fragment.SettingFragment;
import com.supperapper.timetablealerter.fragment.TaskFragment;
import com.supperapper.timetablealerter.fragment.WeekViewFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    Menu copyMenu;
    boolean hasAddBtn;
    boolean hasScheduleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hasAddBtn = false;
        hasScheduleBtn = false;
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

//        ViewPager viewPager = (ViewPager) findViewById(R.id.lyt_super);
//        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), MainActivity.this);
//        viewPager.setAdapter(pagerAdapter);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.setupWithViewPager(viewPager);
//
//        for(int i = 0; i < tabLayout.getTabCount(); i++){
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(pagerAdapter.getTabView(i));
//        }

    }

    public void onDayViewClicked(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        DayViewFragment dayViewFragment = new DayViewFragment();
        fragmentTransaction.replace(R.id.lyt_super,dayViewFragment);
        fragmentTransaction.commit();
        toolbar.setTitle("Dayview");

        if(hasAddBtn==true){
            copyMenu.removeItem(R.id.add_new_task);
            hasAddBtn = false;
        }
        if(hasScheduleBtn==true){
            copyMenu.removeItem(R.id.add_schedule);
            hasScheduleBtn = false;
        }


    }
    public void onWeekViewClicked(){
        toolbar.setTitle("Weekview");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        WeekViewFragment weekViewFragment = new WeekViewFragment();
        fragmentTransaction.replace(R.id.lyt_super,weekViewFragment);
        fragmentTransaction.commit();

        if(hasAddBtn==true){
            copyMenu.removeItem(R.id.add_new_task);
            hasAddBtn = false;
        }
        if(hasScheduleBtn==true){
            copyMenu.removeItem(R.id.add_schedule);
            hasScheduleBtn = false;
        }
    }
    public void onMeetingViewClicked(){
        toolbar.setTitle("Meeting");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        MeetingFragment meetingFragment = new MeetingFragment();
        fragmentTransaction.replace(R.id.lyt_super,meetingFragment);
        fragmentTransaction.commit();

        if(hasScheduleBtn==true){
            copyMenu.removeItem(R.id.add_schedule);
            hasScheduleBtn = false;
        }
        if(hasAddBtn == false){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.toolbar_menu, copyMenu);
            hasAddBtn = true;
        }
    }
    public void onSchoolViewClicked(){
        toolbar.setTitle("School Schedule");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        SchoolFragment schoolFragment = new SchoolFragment();
        fragmentTransaction.replace(R.id.lyt_super,schoolFragment);
        fragmentTransaction.commit();
        if(hasAddBtn == true){
            copyMenu.removeItem(R.id.add_new_task);
            hasAddBtn = false;
        }
        if(hasScheduleBtn==false){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.toolbar_menu_scheduel, copyMenu);
            hasScheduleBtn = true;
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.lyt_super);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

    }
    public void onTaskViewClicked(){
        toolbar.setTitle("Taskview");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        TaskFragment taskFragment = new TaskFragment();
        fragmentTransaction.replace(R.id.lyt_super,taskFragment);
        fragmentTransaction.commit();

        if(hasScheduleBtn==true){
            copyMenu.removeItem(R.id.add_schedule);
            hasScheduleBtn = false;
        }
        if(hasAddBtn == false){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.toolbar_menu, copyMenu);
            hasAddBtn = true;
        }

    }
    public void onSettingClicked(){
        toolbar.setTitle("Setting");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        SettingFragment settingFragment = new SettingFragment();
        fragmentTransaction.replace(R.id.lyt_super,settingFragment);
        fragmentTransaction.commit();

        if(hasAddBtn==true){
            copyMenu.removeItem(R.id.add_new_task);
            hasAddBtn = false;
        }
        if(hasScheduleBtn==true){
            copyMenu.removeItem(R.id.add_schedule);
            hasScheduleBtn = false;
        }
    }
    public void onAboutUsClicked(){
        toolbar.setTitle("About Us");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        AboutUsFragment aboutUsFragment = new AboutUsFragment();
        fragmentTransaction.replace(R.id.lyt_super,aboutUsFragment);
        fragmentTransaction.commit();
        if(hasAddBtn==true){
            copyMenu.removeItem(R.id.add_new_task);
            hasAddBtn = false;
        }
        if(hasScheduleBtn==true){
            copyMenu.removeItem(R.id.add_schedule);
            hasScheduleBtn = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_new_task){
            Intent intent = new Intent(this,AddNewTaskActivity.class);
            startActivity(intent);
        }else{

            Intent addScheudleIntent = new Intent(this,AddScheduleActivity.class);
            startActivity(addScheudleIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_dayView:
                onDayViewClicked();
                break;
            case R.id.nav_weekView:
                onWeekViewClicked();
                break;
            case R.id.nav_schedule:
                onSchoolViewClicked();
                break;
            case R.id.nav_meeting:
                onMeetingViewClicked();
                break;
            case R.id.nav_task:
                onTaskViewClicked();
                break;
            case R.id.nav_setting:
                onSettingClicked();
                break;
            case R.id.nav_about_us:
                onAboutUsClicked();
                break;
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        copyMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_toolbar_menu, copyMenu);
        return true;
    }

    class PagerAdapter extends FragmentPagerAdapter {

        String tabTitles[] = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday","Sunday"};
        Context context;

        public PagerAdapter(android.support.v4.app.FragmentManager fm, Context context) {
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
                   return new SchoolFragment();
               case 1:
                   return new SchoolFragment();
               case 2:
                   return new SchoolFragment();
               case 3:
                   return new SchoolFragment();
               case 4:
                   return new SchoolFragment();
               case 5:
                   return new SchoolFragment();
               case 6:
                   return new SchoolFragment();
           }

           return new SchoolFragment();
        //    return null; Default Return
       }

        @Override
        public CharSequence getPageTitle(int position){
            return tabTitles[position];
        }

        public View getTabView(int position){
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
        }
    }

}
