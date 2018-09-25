package com.bizthinksoft.app.jobfair.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bizthinksoft.app.jobfair.Fragment.AlertFragment;
import com.bizthinksoft.app.jobfair.Fragment.DashboardFragment;
import com.bizthinksoft.app.jobfair.Fragment.HomeFragment;
import com.bizthinksoft.app.jobfair.Fragment.JobFragment;
import com.bizthinksoft.app.jobfair.R;
import com.bizthinksoft.app.jobfair.Utility.AppSharedPreferences;

public class Dashboard extends AppCompatActivity {
AHBottomNavigation bottomNavigation;
AppSharedPreferences appSharedPreferences;
FrameLayout frame_container;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        appSharedPreferences =  new AppSharedPreferences(this);

         actionBar = getSupportActionBar();




        frame_container = findViewById(R.id.frame_container);

        DashboardFragment DashboardFragment = new DashboardFragment();
        replaceFragment(DashboardFragment);

        bottomNavigation =  (AHBottomNavigation)findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 =
                new AHBottomNavigationItem("Home",R.drawable.ic_icons8_home);
        AHBottomNavigationItem item2 =
                new AHBottomNavigationItem("Experience",R.drawable.ic_job);
        AHBottomNavigationItem item3 =
                new AHBottomNavigationItem("Educational",R.drawable.ic_user);

        AHBottomNavigationItem item4 =
                new AHBottomNavigationItem("Interview",R.drawable.ic_alarm);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setBehaviorTranslationEnabled(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...

                if(position==0)
                {
                    DashboardFragment DashboardFragment = new DashboardFragment();
                    replaceFragment(DashboardFragment);
                    actionBar.setHomeButtonEnabled(false);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    actionBar.setDisplayShowHomeEnabled(false);
                    actionBar.setTitle("Welcome to My Job Fair 2018-19");
                }
                else if(position==1)
                {
                    JobFragment JobFragment = new JobFragment();
                    replaceFragment(JobFragment);
                    actionBar.setHomeButtonEnabled(false);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    actionBar.setDisplayShowHomeEnabled(false);
                    actionBar.setTitle("Experience");
                }
                else if(position==2)
                {
                    actionBar.setHomeButtonEnabled(false);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    actionBar.setDisplayShowHomeEnabled(false);
                    actionBar.setTitle("Profile");
                    HomeFragment HomeFragment = new HomeFragment();
                    replaceFragment(HomeFragment);
                }
                else if(position==3)
                {
                    actionBar.setHomeButtonEnabled(false);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    actionBar.setDisplayShowHomeEnabled(false);
                    actionBar.setTitle("Alert Notification");
                    AlertFragment AlertFragment = new AlertFragment();
                    replaceFragment(AlertFragment);
                }
                return true;
            }
        });

    }



    public void replaceFragment (Fragment fragment){
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_container, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
        {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setTitle("Welcome to My Job Fair 2018-19");
            if(getSupportFragmentManager().getBackStackEntryCount()!=1) {
                getSupportFragmentManager().popBackStack();
            }
            else
            {
                finish();
            }
        }
        else {
            finish();
     }



}
}
