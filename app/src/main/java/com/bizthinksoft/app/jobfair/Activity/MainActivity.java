package com.bizthinksoft.app.jobfair.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bizthinksoft.app.jobfair.R;
import com.bizthinksoft.app.jobfair.Utility.API;
import com.bizthinksoft.app.jobfair.Utility.VolllyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    EditText firstname,lastname,email,mobile,password,confirm,middle;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView login =(TextView)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        middle = findViewById(R.id.middle);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);

        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firstname.getText().toString().isEmpty())
                {
                    Toasty.error(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT, true).show();

                }
                else if(lastname.getText().toString().isEmpty())
                {
                    Toasty.error(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT, true).show();
                }
                else if(middle.getText().toString().isEmpty())
                {
                    Toasty.error(getApplicationContext(), "Enter Middle Name", Toast.LENGTH_SHORT, true).show();
                }
                else if(email.getText().toString().isEmpty())
                {
                    Toasty.error(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT, true).show();
                }
                else if(!isValidEmaillId(email.getText().toString().trim())){
                    Toasty.error(getApplicationContext(), "InValid Email Address.", Toast.LENGTH_SHORT, true).show();

                }
                else if(mobile.getText().toString().isEmpty())
                {
                    Toasty.error(getApplicationContext(), "Enter Mobile", Toast.LENGTH_SHORT, true).show();
                }
                else if(password.getText().toString().isEmpty())
                {
                    Toasty.error(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT, true).show();
                }
                else if(confirm.getText().toString().isEmpty())
                {
                    Toasty.error(getApplicationContext(), "Enter Confirm  Password", Toast.LENGTH_SHORT, true).show();
                }

                else if(!password.getText().toString().trim().equals(confirm.getText().toString().trim()))
                {
                    Toasty.error(getApplicationContext(), "Entered Password Miss Match ", Toast.LENGTH_SHORT, true).show();
                }
                else
                {
                    signup();
                }

            }
        });

    }

    private boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public void signup() {




        // Toast.makeText(this, "validation done"+cat_id.getTag(), Toast.LENGTH_SHORT).show();



        final ProgressDialog pDialog = new ProgressDialog(this);

        pDialog.setMessage("Creating Account...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();



                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("status").equals("200")) {
                                if (obj.getString("chkcode").equals("1")) {
                                    Toasty.success(getApplicationContext(), "Your Account Created Successfully ", Toast.LENGTH_SHORT, true).show();

                                    Bundle bundle = new Bundle();
                                    bundle.putString("email", String.valueOf(email.getText()));
                                    bundle.putString("password", String.valueOf(password.getText()));
                                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                    i.putExtras(bundle);
                                    startActivity(i);
                                } else if (obj.getString("chkcode").equals("3")) {

                                    Toasty.error(getApplicationContext(), "Email Already Exist", Toast.LENGTH_SHORT, true).show();

                                } else if (obj.getString("chkcode").equals("4")) {
                                    Toasty.error(getApplicationContext(), "Mobile Already Exist", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                            else
                            {
                                Toasty.error(getApplicationContext(), "Something Went Wrong ", Toast.LENGTH_SHORT, true).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("firstname", String.valueOf(firstname.getText()));
                params.put("middlename", String.valueOf(middle.getText()));
                params.put("lastname", String.valueOf(lastname.getText()));
                params.put("email", String.valueOf(email.getText()));
                params.put("password", String.valueOf(password.getText()));
                params.put("mobilenumber", String.valueOf(mobile.getText()));

                return params;
            }
        };
        VolllyRequest.getInstance(this).addToRequestQueue(stringRequest);

    }
}
