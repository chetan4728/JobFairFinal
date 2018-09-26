package com.bizthinksoft.app.jobfair.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizthinksoft.app.jobfair.Activity.Dashboard;
import com.bizthinksoft.app.jobfair.Activity.Profile;
import com.bizthinksoft.app.jobfair.R;
import com.bizthinksoft.app.jobfair.Utility.AppSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    AppSharedPreferences appSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        appSharedPreferences =  new AppSharedPreferences(getActivity());
        TextView username = view.findViewById(R.id.username);
        username.setText("Welcome "+appSharedPreferences.pref.getString("FirstName",null)+" "+appSharedPreferences.pref.getString("LastName",null));
        LinearLayout postgradation  = (LinearLayout)view.findViewById(R.id.postgrad);
        ActionBar actionBar =((Dashboard) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Educational");
        setHasOptionsMenu(true);
        postgradation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Dashboard)getActivity()).replaceFragment(new PostGradFragment());
            }
        });

        LinearLayout doctrate  = (LinearLayout)view.findViewById(R.id.doctrate);
        doctrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ((Dashboard)getActivity()).replaceFragment(new PhdFragment());

            }
        });

        LinearLayout graduate  = (LinearLayout)view.findViewById(R.id.graduate);
        graduate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Dashboard)getActivity()).replaceFragment(new Graduation_fragment());
            }
        });

        LinearLayout diploma  = (LinearLayout)view.findViewById(R.id.diploma);
        diploma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Dashboard)getActivity()).replaceFragment(new DiplomaFragmnet());
            }
        });

        LinearLayout hsc  = (LinearLayout)view.findViewById(R.id.hsc);
        hsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Dashboard)getActivity()).replaceFragment(new HscFragment());
            }
        });

        LinearLayout ssc  = (LinearLayout)view.findViewById(R.id.ssc);
        ssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Dashboard)getActivity()).replaceFragment(new SscFragment());
            }
        });


        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
            return true;
        };
        return super.onOptionsItemSelected(item);
    }
}
