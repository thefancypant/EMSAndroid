package com.android.maintenancesolution.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.maintenancesolution.R;
import com.android.maintenancesolution.Views.ListActivity.ListActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserSelectorActivity extends AppCompatActivity {

    private ConstraintLayout customerLayout;
    private ConstraintLayout workerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selector);

        customerLayout = findViewById(R.id.customerLayout);
        workerLayout = findViewById(R.id.workerLayout);
        customerLayout.setEnabled(true);
        workerLayout.setEnabled(true);

        customerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToNextActivity = new Intent(getApplicationContext(), CustomerRequestForm.class);
                startActivity(goToNextActivity);
            }
        });

        workerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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


}
