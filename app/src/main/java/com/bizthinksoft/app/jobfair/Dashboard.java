package com.bizthinksoft.app.jobfair;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class Dashboard extends AppCompatActivity {
AHBottomNavigation bottomNavigation;
AppSharedPreferences appSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        appSharedPreferences =  new AppSharedPreferences(this);
        TextView username = findViewById(R.id.username);
        username.setText("Welcome "+appSharedPreferences.pref.getString("FirstName",null)+" "+appSharedPreferences.pref.getString("LastName",null));

        LinearLayout doctrate  = (LinearLayout)findViewById(R.id.doctrate);
        doctrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Dashboard.this,Doctrate.class);
                startActivity(intent);
            }
        });
        bottomNavigation =  (AHBottomNavigation)findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 =
                new AHBottomNavigationItem("Home",R.drawable.ic_icons8_home);
        AHBottomNavigationItem item2 =
                new AHBottomNavigationItem("Jobs",R.drawable.ic_job);
        AHBottomNavigationItem item3 =
                new AHBottomNavigationItem("Account",R.drawable.ic_user);

        AHBottomNavigationItem item4 =
                new AHBottomNavigationItem("Job Alert",R.drawable.ic_alarm);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setBehaviorTranslationEnabled(true);
        LinearLayout postgradation  = (LinearLayout)findViewById(R.id.postgrad);
        postgradation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Dashboard.this,PostGraduaction.class);
                startActivity(intent);
            }
        });

        LinearLayout graduate  = (LinearLayout)findViewById(R.id.graduate);
        graduate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Dashboard.this,Graduation.class);
                startActivity(intent);
            }
        });

        LinearLayout diploma  = (LinearLayout)findViewById(R.id.diploma);
        diploma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Dashboard.this,Diploma.class);
                startActivity(intent);
            }
        });

        LinearLayout hsc  = (LinearLayout)findViewById(R.id.hsc);
        hsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Dashboard.this,Hsc.class);
                startActivity(intent);
            }
        });

        LinearLayout ssc  = (LinearLayout)findViewById(R.id.ssc);
        ssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Dashboard.this,Ssc.class);
                startActivity(intent);
            }
        });

        TextView editProfile  =  (TextView)findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Dashboard.this,Profile.class);
                startActivity(intent);
            }
        });



    }
}
