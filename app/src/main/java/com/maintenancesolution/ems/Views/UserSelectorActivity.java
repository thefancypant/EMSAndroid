package com.maintenancesolution.ems.Views;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maintenancesolution.R;
import com.maintenancesolution.ems.Views.ListActivity.ListActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class UserSelectorActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private ConstraintLayout customerLayout;
    private ConstraintLayout workerLayout;
    private int internetStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selector);
        Intent intent = getIntent();
        internetStatus = intent.getIntExtra("internet", 0);

        if (internetStatus == 2) {

            AlertDialog alertDialog = new AlertDialog.Builder(UserSelectorActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("We are sorry, Something went wrong, please check your connection and try again.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //
                        }
                    });
            alertDialog.show();
        }

        customerLayout = findViewById(R.id.customerLayout);
        workerLayout = findViewById(R.id.workerLayout);
        customerLayout.setEnabled(true);
        workerLayout.setEnabled(true);

        customerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat
                        .checkSelfPermission(getApplicationContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat
                            .requestPermissions(UserSelectorActivity.this
                                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                                    , MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                } else {
                    Intent goToNextActivity = new Intent(getApplicationContext(), CustomerRequestForm.class);
                    startActivity(goToNextActivity);
                }


            }
        });


        workerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat
                        .checkSelfPermission(getApplicationContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat
                            .requestPermissions(UserSelectorActivity.this
                                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                                    , MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }
                Intent goToNextActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(goToNextActivity);
            }
        });

        // Check if we have an access token already
        /*String token = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("MYTOKEN", "");
        if (!token.equals("")) {
            // Try to validate the token
            try {
                attemptVerify(token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }*/


    }

    private void attemptVerify(String token) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        String url = getString(R.string.global_url) + "/verify/";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("token", token);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Intent goToNextActivity = new Intent(getApplicationContext(), ListActivity.class);
                        startActivity(goToNextActivity);
                        finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do nothing

                    }
                });
        requestQueue.add(jsObjRequest);
    }

    private void toggle(String user) {

        if (user.equals("Worker")) {
            customerLayout.setEnabled(false);
        } else if (user.equals("Customer")) {
            workerLayout.setEnabled(false);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent goToNextActivity = new Intent(getApplicationContext(), CustomerRequestForm.class);
                    startActivity(goToNextActivity);

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Intent goToNextActivity = new Intent(getApplicationContext(), CustomerRequestForm.class);
                    startActivity(goToNextActivity);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
