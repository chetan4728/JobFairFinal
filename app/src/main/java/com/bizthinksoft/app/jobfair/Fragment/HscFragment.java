package com.bizthinksoft.app.jobfair.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bizthinksoft.app.jobfair.Activity.Dashboard;
import com.bizthinksoft.app.jobfair.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HscFragment extends Fragment {


    public HscFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_hsc, container, false);
        ActionBar actionBar =((Dashboard) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("HSC Detail");
        setHasOptionsMenu(true);

        return  view;

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
