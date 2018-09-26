package com.bizthinksoft.app.jobfair.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.bizthinksoft.app.jobfair.R;
import com.bizthinksoft.app.jobfair.Utility.AppSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    AppSharedPreferences appSharedPreferences;
    EditText firstname,lastname,email,mobile,middle,password,datepicker;
    private DatePicker datePicker;
    private Calendar calendar;
    private DatePickerDialog fromDatePickerDialog;
    private int year, month, day;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);


        appSharedPreferences =  new AppSharedPreferences(getActivity());
        firstname = view.findViewById(R.id.firstname);
        lastname = view.findViewById(R.id.lastname);
        email = view.findViewById(R.id.email);
        mobile = view.findViewById(R.id.mobile);
        middle = view.findViewById(R.id.middle);
        datepicker = view.findViewById(R.id.datepicker);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);

            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        firstname.setText(appSharedPreferences.pref.getString("FirstName",null));
        lastname.setText(appSharedPreferences.pref.getString("LastName",null));
        middle.setText(appSharedPreferences.pref.getString("FirstName",null));
        email.setText(appSharedPreferences.pref.getString("email",null));
        mobile.setText(appSharedPreferences.pref.getString("Mobile",null));

        return view;
    }


    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        datepicker.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
