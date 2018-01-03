package com.android.maintenancesolution;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.maintenancesolution.Models.Order;
import com.android.maintenancesolution.Models.PostLocationRequest;
import com.android.maintenancesolution.Models.PostLocationResponse;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.Utils.PreferenceUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ListActivity extends AppCompatActivity implements LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    OrderListAdapter adapter;
    double lattiude;
    double longitude;
    ListView mListView;
    ProgressBar mProgressView;
    ConstraintLayout constraint_layout;
    LocationManager locationManager;
    PreferenceUtils preferenceUtils;
    Intent intent;
    private String header;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_refresh:
                this.makeRequest();
                return true;
            case R.id.action_logout:
                //Logout logic goes here
                PreferenceManager.getDefaultSharedPreferences(this).edit().remove("MYTOKEN").apply();
                final Intent intent = new Intent(ListActivity.this, UserSelectorActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

  /*  public void makeRequest() {
        showProgress(true);


        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.global_url) + "/user_works/";
        JsonArrayRequest localJReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        showProgress(false);
                        final ArrayList<Order> orderList = Order.getOrdersFromJsonArray(response);
                        final OrderAdapter adapter = new OrderAdapter(getBaseContext(), orderList);
                        mListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                if (ActivityCompat.checkSelfPermission(ListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    if (!checkLocationPermission()) {

                                        AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();
                                        alertDialog.setTitle("Warning");
                                        alertDialog.setMessage("The HOP application will no longer be able to update your location, is this ok?");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                });
                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        checkLocationPermission();
                                                    }
                                                });
                                        alertDialog.show();
                                    } else {
                                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ListActivity.this);
                                    }
                                } else {
                                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ListActivity.this);
                                }
                                Order item = (Order) adapter.getItem(i);
                                final Intent intent = new Intent(ListActivity.this, OrderDetail.class);
                                intent.putExtra("Order", item);
                                Date currentTime = Calendar.getInstance().getTime();
                                Date date = new Date();   // given date
                                Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                                calendar.setTime(date);   // assigns calendar to given date
                                int hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
                                int minutes = calendar.get(Calendar.MINUTE);
                                String url = getString(R.string.global_url) + "/works/" + item.id + "/";
                                JSONObject jsonBody = new JSONObject();
                                try {
                                    jsonBody.put("latitude", ListActivity.this.lattiude);
                                    jsonBody.put("longitude", ListActivity.this.longitude);
                                    jsonBody.put("register_time", hours + ":" + minutes);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                                        (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                showProgress(false);
                                                startActivity(intent);
                                            }


                                        }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // TODO Auto-generated method stub
                                                AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();
                                                alertDialog.setTitle("Failure");
                                                alertDialog.setMessage("We are sorry, Something went wrong, please check your connection and try again.");
                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                //
                                                            }
                                                        });
                                                alertDialog.show();
                                            }
                                        }) {
                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Authorization", "JWT " + PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("MYTOKEN", ""));
                                        return params;
                                    }
                                };
                                requestQueue.add(jsObjRequest);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // this is an error
                        AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();
                        alertDialog.setTitle("Failure");
                        alertDialog.setMessage("We are sorry, Something went wrong, please check your connection and try again.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                });
                        alertDialog.show();
                    }
                }) {//here before semicolon ; and use { }.
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "JWT " + PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("MYTOKEN", ""));
                return params;
            }

            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();

            }
        };

        requestQueue.add(localJReq);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLocationPermission();
        getPrefUtils();

        setContentView(R.layout.activity_newlist);
        mListView = findViewById(R.id.recipe_list_view);
        // mListView.setEmptyView(findViewById(R.id.recipe_list_view));
        makeRequest();
        //this.makeRequest();
    }

    private void makeRequest() {
        showProgress(true);

        NetworkService
                .getInstance()
                .getUserWorks(header)
                .enqueue(new Callback<List<com.android.maintenancesolution.Models.Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, retrofit2.Response<List<Order>> response) {
                        if (response.code() >= 200 && response.code() < 299) {
                            processMakeRequest(response.body());
                        } else {
                            showProgress(false);
                            showErrorDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        showProgress(false);
                        showErrorDialog();

                    }
                });

    }

    private void processMakeRequest(List<Order> orderList) {
        showProgress(false);

        adapter = new OrderListAdapter(ListActivity.this, orderList);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(ListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (!checkLocationPermission()) {

                        AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("The HOP application will no longer be able to update your location, is this ok?");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkLocationPermission();
                                    }
                                });
                        alertDialog.show();
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ListActivity.this);
                    }
                } else {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ListActivity.this);
                }
                Order item = (Order) adapter.getItem(i);

                intent = new Intent(ListActivity.this, OrderDetail.class);
                intent.putExtra("Order", item);
                Date currentTime = Calendar.getInstance().getTime();
                Date date = new Date();   // given date
                Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                calendar.setTime(date);   // assigns calendar to given date
                int hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
                int minutes = calendar.get(Calendar.MINUTE);
                postLocationNetwork(hours, minutes, item.getId());
                /*Order item = (Order) adapter.getItem(i);
                final Intent intent = new Intent(ListActivity.this,OrderDetail.class);
                intent.putExtra("Order", item);
                Date currentTime = Calendar.getInstance().getTime();
                Date date = new Date();   // given date
                Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                calendar.setTime(date);   // assigns calendar to given date
                int hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
                int minutes = calendar.get(Calendar.MINUTE);
                String url = getString(R.string.global_url) + "/works/"+item.id+"/";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("latitude", ListActivity.this.lattiude);
                    jsonBody.put("longitude", ListActivity.this.longitude);
                    jsonBody.put("register_time", hours+":"+minutes);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                showProgress(false);
                                startActivity(intent);
                            }



                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();
                                alertDialog.setTitle("Failure");
                                alertDialog.setMessage("We are sorry, Something went wrong, please check your connection and try again.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //
                                            }
                                        });
                                alertDialog.show();
                            }
                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "JWT "+PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("MYTOKEN", ""));
                        return params;
                    }
                };
                requestQueue.add(jsObjRequest);*/
            }
        });


    }

    private void postLocationNetwork(int hours, int minutes, int id) {
        showProgress(true);

        PostLocationRequest postLocationRequest =
                new PostLocationRequest(
                        longitude
                        , lattiude
                        , (Integer.toString(hours) + ":" + Integer.toString(minutes)));

        NetworkService
                .getInstance()
                .postLocation(header, Integer.toString(id), postLocationRequest)
                .enqueue(new Callback<PostLocationResponse>() {
                    @Override
                    public void onResponse(Call<PostLocationResponse> call, retrofit2.Response<PostLocationResponse> response) {
                        if (response.code() >= 200 && response.code() < 299) {
                            processLocationNetwork(response.body());
                        } else {
                            showProgress(false);
                            showErrorDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostLocationResponse> call, Throwable t) {
                        showProgress(false);
                        showErrorDialog();

                    }
                });
    }

    private void processLocationNetwork(PostLocationResponse body) {
        showProgress(false);

        if (body.getMessage().equals("Success")) {
            startActivity(intent);
        }

    }

    private void showErrorDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(ListActivity.this).create();
        alertDialog.setTitle("Failure");
        alertDialog.setMessage("We are sorry, Something went wrong, please check your connection and try again.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
        alertDialog.show();
    }


    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(ListActivity.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                //Request location updates:
                try {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.d("location", "onLocationChanged: " + Double.toString(latitude));
        Log.d("location", "onLocationChanged: " + Double.toString(longitude));

        this.lattiude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        constraint_layout = findViewById(R.id.list_constraint_layout);
        mProgressView = findViewById(R.id.list_progress_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            constraint_layout.setVisibility(show ? View.GONE : View.VISIBLE);
            constraint_layout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    constraint_layout.setVisibility(show ? View.GONE : View.VISIBLE);

                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            constraint_layout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
