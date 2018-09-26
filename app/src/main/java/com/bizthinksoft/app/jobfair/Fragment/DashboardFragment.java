package com.bizthinksoft.app.jobfair.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bizthinksoft.app.jobfair.Activity.Profile;
import com.bizthinksoft.app.jobfair.R;
import com.bizthinksoft.app.jobfair.Utility.AppSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

AppSharedPreferences appSharedPreferences;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        appSharedPreferences =  new AppSharedPreferences(getActivity());
        TextView username = view.findViewById(R.id.username);
        username.setText("Welcome "+appSharedPreferences.pref.getString("FirstName",null)+" "+appSharedPreferences.pref.getString("LastName",null));
        TextView editProfile  =  (TextView)view.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(),Profile.class);
                startActivity(intent);
            }
        });
        return  view;
    }

}
