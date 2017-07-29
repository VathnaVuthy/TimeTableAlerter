package com.supperapper.timetablealerter.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.share.model.ShareLinkContent;
import com.supperapper.timetablealerter.R;
import com.supperapper.timetablealerter.dataset.App;
import com.supperapper.timetablealerter.dataset.DynamicPagerAdapter;
import com.supperapper.timetablealerter.dataset.SchoolPagerAdapter;
import com.supperapper.timetablealerter.dataset.TaskPagerAdapter;
import com.supperapper.timetablealerter.dataset.User;
import com.supperapper.timetablealerter.fragment.AboutUsFragment;
import com.supperapper.timetablealerter.fragment.SettingFragment;
import com.supperapper.timetablealerter.service.NotificationChecker;
import com.supperapper.timetablealerter.viewholder.DynamicAdapter;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    SchoolPagerAdapter pagerAdapter;
    TaskPagerAdapter taskPagerAdapter;
    DynamicPagerAdapter dynamicPagerAdapter;
    Toolbar toolbar;
    Menu copyMenu;
    boolean hasAddBtn;
    boolean hasScheduleBtn;
    TextView txtUsername;
    TextView txtEmail;
    ImageView imgProfile;
    View headerView;
    SharedPreferences sharedPreferences;
    boolean preferences = false;
    String lastlogin;
    private ShareActionProvider shareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Start Service
        Intent startservice = new Intent(this, NotificationChecker.class);
        startService(startservice);

        hasAddBtn = false;
        hasScheduleBtn = false;
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        lastlogin = LoginActivity.getDefaults(LoginActivity.LAST_LOGIN_METHOD, this);

        Log.d("MainActivity last login", "=" + lastlogin);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setChecked(true);

        headerView = navigationView.getHeaderView(0);
        txtUsername = (TextView) headerView.findViewById(R.id.txt_name);
        txtEmail = (TextView) headerView.findViewById(R.id.txt_email);

         final CircleImageView circleImageProfile = (CircleImageView) headerView.findViewById(R.id.header_profile_pic);
         sharedPreferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);


        String name = sharedPreferences.getString("name", null);
        String email = sharedPreferences.getString("email", null);


        String login =   SettingActivity.getDefaults(SettingActivity.isLogin, MainActivity.this);

        Log.d("MainActivity", "value is" + login);


        ViewPager viewPager = (ViewPager) findViewById(R.id.lyt_super);
        dynamicPagerAdapter = new DynamicPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(dynamicPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(getDynamicTabVIew(i));
        }


        if (login != null){

           if (login.equals("login")){

                Log.d("login", "true");
               // if(App.getInstance(MainActivity.this).getLoginMethod() == App.LOGIN_METHOD_USERNAME_PASSWORD){
                if (lastlogin != null){
//
                    Log.d("Log to", "share preferences");
//
                    if (lastlogin.equals("facebook")){


                        Log.d("MainActivity", "Last login via" + lastlogin);

                        Profile profile = Profile.getCurrentProfile();

                        String profileImageUrl = profile.getProfilePictureUri(230,230).toString();

                        ImageRequest imageRequest = new ImageRequest(profileImageUrl, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {

                                circleImageProfile.setImageBitmap(response);
                            }
                        }, 230, 230, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, null);

                        App.getInstance(this).addRequest(imageRequest);

                        Log.d("Login", "via facebook");

                        if(name != null){

                            txtUsername.setText(name);
                            txtEmail.setText(email);

                        } else {

                            loadProfileFromFacebook();
                        }


                    } else {


                        Log.d("Login", "via" + lastlogin);

                        txtUsername.setText("Larry Page");
                        txtEmail.setText("larrypage@gmail.com");
                        circleImageProfile.setImageResource(R.drawable.profile_larrypage);

                    }
                } else {

                    Log.d("no", "login");
                }


            } else {

                Log.d("logout", "true");
            }


       // }


 //     if(App.getInstance(MainActivity.this).getLogin() == App.IS_LOGIN){

//          if(App.getInstance(MainActivity.this).getLoginMethod() == App.LOGIN_METHOD_USERNAME_PASSWORD){
//
////              txtUsername.setText("Larry Page");
////              txtEmail.setText("larrypage@gmail.com");
//              txtUsername.setText("Your name");
//              txtEmail.setText("example@gmail.com");
//              circleImageProfile.setImageResource(R.drawable.profile_larrypage);
//
//          } else {
//
//              Profile profile = Profile.getCurrentProfile();
//
//              Log.d("Login", "via facebook");
//              if(name != null){
//
//                  txtUsername.setText(name);
//                  txtEmail.setText(email);
//
//              } else if(name == null){
//
//                  loadProfileFromFacebook();
//              }
//
//            String profileImageUrl = profile.getProfilePictureUri(230,230).toString();
//
//            ImageRequest imageRequest = new ImageRequest(profileImageUrl, new Response.Listener<Bitmap>() {
//                @Override
//                public void onResponse(Bitmap response) {
//
//                    circleImageProfile.setImageBitmap(response);
//                }
//            }, 230, 230, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, null);
//
//            App.getInstance(this).addRequest(imageRequest);
//
//          }

     } else {

          txtUsername.setText("Your name");
          txtEmail.setText("example@gmail.com");
          circleImageProfile.setImageResource(R.drawable.profile_larrypage);

     }





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

    private void loadProfileFromFacebook() {
        Log.d("ckcc", "loadProfileFromFacebook");
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        final GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/me/",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        preferences = true;
                        Log.d("ckcc", "loadProfileFromFacebook completed");
                        JSONObject result = response.getJSONObject();

                        try {
                            String id = result.getString("id");
                            String name = result.getString("name");
                            String email = result.getString("email");


                       //     Uri image = com.facebook.internal.ImageRequest.getProfilePictureUri(result.optString("id"), 230, 230);

                       //     Log.d("Profile",image.toString());

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id", id);
                            editor.putString("name", name);
                            editor.putString("email", email);
                            editor.commit();


                            txtUsername.setText(name);
                            txtEmail.setText(email);
                       //     circleImageProfile.setImageURI(image);
                       //     circleImageProfile.setImageURI(image);
                      //      imgProfile.setImageURI(image);

//                            String imageUrl = Profile.getCurrentProfile().getProfilePictureUri(230, 230).toString();
//                            displayProfileImageFromServer(imageUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();

    }


    private void displayProfileImageFromServer(String imageUrl) {
        ImageRequest request = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                    imgProfile.setImageBitmap(response);
                    Log.d("Respone while null:", String.valueOf(response));
                    Log.d("TTA", String.valueOf(imgProfile));
            }
        }, 512, 512, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error while loading profile image.", Toast.LENGTH_LONG).show();
            }
        });
        App.getInstance(MainActivity.this).addRequest(request);
    }


    public void onDayViewClicked(){
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        DayViewFragment dayViewFragment = new DayViewFragment();
//        fragmentTransaction.replace(R.id.lyt_super,dayViewFragment);
//        fragmentTransaction.commit();
        toolbar.setTitle("Dayview");

//        if(hasAddBtn==true){
//            copyMenu.removeItem(R.id.add_new_task);
//            hasAddBtn = false;
//        }
//        if(hasScheduleBtn==true){
//            copyMenu.removeItem(R.id.add_schedule);
//            hasScheduleBtn = false;
//        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.lyt_super);
        DynamicPagerAdapter pagerAdapter = new DynamicPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(getDynamicTabVIew(i));
        }


    }
    public void onWeekViewClicked(){
        toolbar.setTitle("Weekview");
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        WeekViewFragment weekViewFragment = new WeekViewFragment();
//        fragmentTransaction.replace(R.id.lyt_super,weekViewFragment);
//        fragmentTransaction.commit();

        if(hasAddBtn==true){
            copyMenu.removeItem(R.id.add_new_task);
            hasAddBtn = false;
        }
        if(hasScheduleBtn==true){
            copyMenu.removeItem(R.id.add_schedule);
            hasScheduleBtn = false;
        }
    }
//    public void onMeetingViewClicked(){
//        toolbar.setTitle("Meeting");
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        MeetingFragment meetingFragment = new MeetingFragment();
//        fragmentTransaction.replace(R.id.lyt_super,meetingFragment);
//        fragmentTransaction.commit();
//
//        if(hasScheduleBtn==true){
//            copyMenu.removeItem(R.id.add_schedule);
//            hasScheduleBtn = false;
//        }
//        if(hasAddBtn == false){
//            MenuInflater inflater = getMenuInflater();
//            inflater.inflate(R.menu.toolbar_menu, copyMenu);
//            hasAddBtn = true;
//        }
//    }
    public void onSchoolViewClicked(){
        toolbar.setTitle("School Schedule");
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        SchoolFragment schoolFragment = new SchoolFragment();
//        fragmentTransaction.replace(R.id.lyt_super,schoolFragment);
//        fragmentTransaction.commit();
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
        pagerAdapter = new SchoolPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(getSchoolTabView(i));
        }

    }
    public void onTaskViewClicked(){
        toolbar.setTitle("Taskview");
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        TaskFragment taskFragment = new TaskFragment();
//        fragmentTransaction.replace(R.id.lyt_super,taskFragment);
//        fragmentTransaction.commit();

        if(hasScheduleBtn==true){
            copyMenu.removeItem(R.id.add_schedule);
            hasScheduleBtn = false;
        }
        if(hasAddBtn == false){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.toolbar_menu, copyMenu);
            hasAddBtn = true;
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.lyt_super);
        taskPagerAdapter = new TaskPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(taskPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(getTaskTabView(i));
        }

    }
    public void onSettingClicked(){

        Intent intent = new Intent(this,SettingActivity.class);

        intent.putExtra("name", txtUsername.getText());
        intent.putExtra("email", txtEmail.getText());


        startActivity(intent);
    }

    public void onShareClick(){


        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.instagram.android");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);


    }


    public void onAboutUsClicked(){
        toolbar.setTitle("About Us");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        AboutUsFragment aboutUsFragment = new AboutUsFragment();
        fragmentTransaction.replace(R.id.lyt_super,aboutUsFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
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
//            case R.id.nav_meeting:
//                onMeetingViewClicked();
//                break;
            case R.id.nav_task:
                onTaskViewClicked();
                break;
            case R.id.nav_setting:
                onSettingClicked();
                break;
            case R.id.nav_about_us:
                onAboutUsClicked();
                break;
            case R.id.nav_share:
                onShareClick();
                break;
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() != R.id.nav_dayView) {
            navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setChecked(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        copyMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_toolbar_menu, copyMenu);
        return true;
    }

//    class PagerAdapter extends FragmentPagerAdapter {
//
//        String tabTitles[] = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday","Sunday"};
//        Context context;
//
//        public PagerAdapter(android.support.v4.app.FragmentManager fm, Context context) {
//            super(fm);
//            this.context = context;
//        }
//
//        @Override
//        public int getCount() {
//            return tabTitles.length;
//        }
//
//        public Fragment getItem(int position){
//           switch (position) {
//               case 0:
//                   return new SchoolFragment();
//               case 1:
//                   return new SchoolFragment();
//               case 2:
//                   return new SchoolFragment();
//               case 3:
//                   return new SchoolFragment();
//               case 4:
//                   return new SchoolFragment();
//               case 5:
//                   return new SchoolFragment();
//               case 6:
//                   return new SchoolFragment();
//           }
//
//           return new SchoolFragment();
//        //    return null; Default Return
//       }
//
//        @Override
//        public CharSequence getPageTitle(int position){
//            return tabTitles[position];
//        }
//
//        public View getTabView(int position){
//            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
//            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
//            tv.setText(tabTitles[position]);
//            return tab;
//        }
//    }

    public View getSchoolTabView(int position){
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(pagerAdapter.getPageTitle(position));
            return tab;
        }
    public View getTaskTabView(int position){
        View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab,null);
        TextView tv = (TextView) tab.findViewById(R.id.custom_text);
        tv.setText(taskPagerAdapter.getPageTitle(position));
        return tab;
        }

    public View getDynamicTabVIew(int position){
        View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab,null);
        TextView tv = (TextView) tab.findViewById(R.id.custom_text);
        tv.setText(dynamicPagerAdapter.getPageTitle(position));
        return tab;
    }
}

