package com.bizthinksoft.app.jobfair.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.bizthinksoft.app.jobfair.R;
import com.bizthinksoft.app.jobfair.Utility.AppSharedPreferences;

public class Profile extends AppCompatActivity {
    AppSharedPreferences appSharedPreferences;
    EditText firstname,lastname,email,mobile,middle,password,datepicker;
    private DatePicker datePicker;
    private Calendar calendar;
    private DatePickerDialog fromDatePickerDialog;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        appSharedPreferences =  new AppSharedPreferences(this);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        middle = findViewById(R.id.middle);
        datepicker = findViewById(R.id.datepicker);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);

            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }


        datepicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showDialog(999);
                return true;
            }
        });


        firstname.setText(appSharedPreferences.pref.getString("FirstName",null));
        lastname.setText(appSharedPreferences.pref.getString("LastName",null));
        middle.setText(appSharedPreferences.pref.getString("FirstName",null));
        email.setText(appSharedPreferences.pref.getString("email",null));
        mobile.setText(appSharedPreferences.pref.getString("Mobile",null));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
