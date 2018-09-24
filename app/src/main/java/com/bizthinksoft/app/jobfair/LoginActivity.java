package com.bizthinksoft.app.jobfair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    AppSharedPreferences app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView signup =(TextView)findViewById(R.id.signup);
        Button  login =  (Button)findViewById(R.id.login);

        final EditText email = (EditText)findViewById(R.id.email);
        final EditText password = (EditText)findViewById(R.id.password);


        Bundle bundle = getIntent().getExtras();
        app =  new AppSharedPreferences(this);

        // Toast.makeText(this, ""+Array, Toast.LENGTH_SHORT).show();
        if (this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("email")) {
            String get_email = bundle.getString("email");
            String get_password = bundle.getString("password");
            email.setText(get_email);
            password.setText(get_password);


        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(email.getText().toString().trim().isEmpty())
                {

                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    login();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void login()
    {

        final EditText email = (EditText)findViewById(R.id.email);
        final EditText password = (EditText)findViewById(R.id.password);
        String  tag_string_req = "string_req";



        final ProgressDialog pDialog = new ProgressDialog(this);

        pDialog.setMessage("Logging in ...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                             Log.i("detail",response);
                            if(obj.getString("status").equals("200")) {
                                if (obj.getString("chkcode").equals("1")) {
                                    Toasty.success(getApplicationContext(), "Login Successful ", Toast.LENGTH_SHORT, true).show();

                                    JSONObject responsedata = obj.getJSONObject("response");


                                    app.editor.putString(AppSharedPreferences.FirstName,responsedata.getString("c_first_name"));
                                    app.editor.putString(AppSharedPreferences.LastName,responsedata.getString("c_last_name"));
                                    app.editor.putString(AppSharedPreferences.Email,responsedata.getString("c_primary_email"));
                                    app.editor.putString(AppSharedPreferences.Mobile,responsedata.getString("c_primary_mobile"));
                                    app.editor.putString(AppSharedPreferences.mast_id,responsedata.getString("c_id"));
                                    app.editor.commit();


                                    Bundle bundle = new Bundle();
                                    bundle.putString("email", String.valueOf(email.getText()));
                                    bundle.putString("password", String.valueOf(password.getText()));
                                    Intent i = new Intent(LoginActivity.this, Dashboard.class);
                                    i.putExtras(bundle);
                                    startActivity(i);
                                    finish();
                                }
                                else if (obj.getString("chkcode").equals("3)")) {

                                    Toasty.error(getApplicationContext(), "Your account is deactivated, Please contact Admin", Toast.LENGTH_SHORT, true).show();

                                }
                                else if (obj.getString("chkcode").equals("4)")) {

                                    Toasty.error(getApplicationContext(), "Your account is deleted, Please contact Admin", Toast.LENGTH_SHORT, true).show();

                                }

                                else if (obj.getString("chkcode").equals("5")) {

                                    Toasty.error(getApplicationContext(), "Wrong User Name or Password, Please Try Again", Toast.LENGTH_SHORT, true).show();

                                }
                            }
                            else
                            {
                                Toasty.error(getApplicationContext(), "Something Went Wrong ", Toast.LENGTH_SHORT, true).show();
                            }

                            pDialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("username", String.valueOf(email.getText()));
                params.put("login_password", String.valueOf(password.getText()));
                return params;
            }
        };
        VolllyRequest.getInstance(this).addToRequestQueue(stringRequest);

    }
}
