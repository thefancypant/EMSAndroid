package com.android.maintenancesolution.Views;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.maintenancesolution.Models.ActiveClockResponse;
import com.android.maintenancesolution.Models.Center;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.R;
import com.android.maintenancesolution.Utils.PreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClockActivity extends AppCompatActivity implements LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static IntentFilter s_intentFilter;



    private final String TAG = "ClockActivity";
    @BindView(R.id.hoursTextView)
    TextView hoursTextView;
    @BindView(R.id.minutesTextView)
    TextView minutesTextView;
    @BindView(R.id.amPmTextView)
    TextView amPmTextView;
    @BindView(R.id.dayTextView)
    TextView dayTextView;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.buttonClockIn)
    Button buttonClockIn;
    @BindView(R.id.buttonLunchIn)
    Button buttonLunchIn;
    @BindView(R.id.selectedCenterTextview)
    TextView selectedCenterTextview;
    @BindView(R.id.clockInTextView)
    TextView clockInTextView;
    @BindView(R.id.lunchTextView)
    TextView lunchTextView;
    @BindView(R.id.clockOutTextView)
    TextView clockOutTextView;
    @BindView(R.id.clockInTimeTextView)
    TextView clockInTimeTextView;
    @BindView(R.id.lunchInTimeTextView)
    TextView lunchInTimeTextView;
    @BindView(R.id.lunchOutTimeTextView)
    TextView lunchOutTimeTextView;
    @BindView(R.id.clockOutTimeTextView)
    TextView clockOutTimeTextView;
    private double lattiude;
    private double longitude;
    private LocationManager locationManager;
    private PreferenceUtils preferenceUtils;
    private String header;
    private String selectedCenter;
    private String LUNCHIN = "LUNCHIN";
    private String LUNCHOUT = "LUNCHOUT";
    private String CLOCKOUT = "CLOCKOUT";
    private String CLOCKIN = "CLOCKIN";

    private String clockinStatus;
    private String lunchInStatus;
    private String clockIntime = "";
    private String clockOuttime = "";
    private String LunchIntime = "";
    private String LunchOuttime = "";
    private ActiveClockResponse activeClockResponse = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        ButterKnife.bind(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //enableGps();
        spinner.setEnabled(false);
        getPrefUtils();
        setEmptyUI();
        getActiveClock();
        checkGpsPermissions();
        setClock();
        getCenters();

    }

    private void setEmptyUI() {
        buttonLunchIn.setVisibility(View.GONE);
        selectedCenterTextview.setVisibility(View.GONE);
        clockInTextView.setVisibility(View.GONE);
        clockInTimeTextView.setVisibility(View.GONE);
        lunchTextView.setVisibility(View.GONE);
        lunchInTimeTextView.setVisibility(View.GONE);
        lunchOutTimeTextView.setVisibility(View.GONE);
        clockOutTextView.setVisibility(View.GONE);
        clockOutTimeTextView.setVisibility(View.GONE);
    }

    private void getActiveClock() {
        NetworkService
                .getInstance()
                .getActiveColck(header)
                .enqueue(new Callback<ActiveClockResponse>() {
                    @Override
                    public void onResponse(Call<ActiveClockResponse> call, Response<ActiveClockResponse> response) {
                        processActiveClock(response);
                    }

                    @Override
                    public void onFailure(Call<ActiveClockResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.toString());

                    }
                });
    }


    private void processActiveClock(Response<ActiveClockResponse> response) {

        activeClockResponse = response.body();
        if (response.code() >= 200 && response.code() < 300) {
            if (response.body() != null) {
                if (response.body().getCenter() != null) {
                    selectedCenterTextview.setVisibility(View.VISIBLE);
                    selectedCenterTextview.setText(response.body().getCenter().getName());
                }
                if (/*response.body().getClockOutDatetime()!=null ||*/
                        response.body().getClockInDatetime() != null ||
                                response.body().getLunchInDatetime() != null ||
                                response.body().getLunchOutDatetime() != null
                            /*response.body().getClockOutDatetime()!=null*/) {
                    lunchTextView.setVisibility(View.VISIBLE);
                    clockOutTextView.setVisibility(View.VISIBLE);
                    clockInTextView.setVisibility(View.VISIBLE);
                }

                if (response.body().getClockInDatetime() != null) {
                    buttonClockIn.setText("Clock out");
                    clockInTimeTextView.setVisibility(View.VISIBLE);
                    clockIntime = response.body().getClockInDatetime().getTime();
                    clockInTimeTextView.setText(response.body().getClockInDatetime().getTime());
                }
                if (response.body().getLunchInDatetime() != null) {
                    lunchInTimeTextView.setVisibility(View.VISIBLE);
                    LunchIntime = response.body().getLunchInDatetime().getTime();
                    lunchInTimeTextView.setText(response.body().getLunchInDatetime().getTime());
                } else {
                    // if ()
                    buttonLunchIn.setVisibility(View.VISIBLE);

                }

                if (response.body().getLunchOutDatetime() != null) {
                    lunchOutTimeTextView.setVisibility(View.VISIBLE);
                    LunchOuttime = response.body().getLunchOutDatetime().getTime();
                    lunchOutTimeTextView.setText(response.body().getLunchOutDatetime().getTime());
                }


            } else {

            }

        }
    }

    private void getCenters() {
        NetworkService
                .getInstance()
                .getCenters(header)
                .enqueue(new Callback<List<Center>>() {
                    @Override
                    public void onResponse(Call<List<Center>> call, Response<List<Center>> response) {
                        processCenters(response);
                    }

                    @Override
                    public void onFailure(Call<List<Center>> call, Throwable t) {

                    }
                });
    }

    private void processCenters(Response<List<Center>> response) {
        if (response.code() >= 200 && response.code() < 300) {
            final ArrayList<String> spinnerArray = new ArrayList<>();
            spinnerArray.add("Select a center");

            for (int i = 0; i < response.body().size(); i++) {
                spinnerArray.add(response.body().get(i).getName());
            }

            spinner.setEnabled(true);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
            spinner.setAdapter(arrayAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position != 0) {
                        if (!spinnerArray.get(position).equals("Select Work Type")) {
                            selectedCenter = Integer.toString(position);
                            Log.d(TAG, "onItemSelected: " + selectedCenter);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }

    private void setClock() {

        Calendar calendar = GregorianCalendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        SimpleDateFormat amPmFormat = new SimpleDateFormat("a");
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("E");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
        SimpleDateFormat minutesFormat = new SimpleDateFormat("m");
        SimpleDateFormat hoursFormat = new SimpleDateFormat("hh");

        Date date = new Date();
        String month = monthFormat.format(date).toString();
        //String hour = Integer.toString(calendar.get(Calendar.HOUR));        // gets hour in 12h format
        //String minutes =Integer.toString(calendar.get(Calendar.MINUTE));
        String hour = hoursFormat.format(new Date()).toString();
        String minutes = minutesFormat.format(new Date()).toString();
        String am_pm = amPmFormat.format(date).toString();
        String dayOfWeek = dayOfWeekFormat.format(date).toString();
        String year = yearFormat.format(date).toString();

        dayTextView.setText(dayOfWeek.toUpperCase() + " " + month.toUpperCase() + " " + year);

        Log.d(TAG, "setClock: " + hour + ":" + minutes + " " + am_pm + " " + dayOfWeek + " " + month + " " + year);

    }

    private void checkGpsPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkGpsPermissions: Permissions not available");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

            //return false;
        } else {
            Log.d(TAG, "checkGpsPermissions: Permissions available");
            gpsIsEnabled();

        }

    }

    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(ClockActivity.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }

    private void gpsIsEnabled() {

        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Log.d(TAG, "gpsIsEnabled: Permissions available.Gps not enabled");
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS not enabled");  // GPS not found
            builder.setMessage("Do you want to enable GPS"); // Want to enable?
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d(TAG, "gpsIsEnabled: Dialog Yes clicked ");

                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d(TAG, "gpsIsEnabled: Dialog No clicked ");

                }
            });
            builder.create().show();
            return;
        } else {
            Log.d(TAG, "gpsIsEnabled: Permissions available.Gps not enabled");
            getLocation();
        }

    }

    @OnClick(R.id.buttonClockIn)
    public void buttonClockIn() {
        if (clockIntime == "") {
            lunchTextView.setVisibility(View.VISIBLE);
            clockOutTextView.setVisibility(View.VISIBLE);
            clockInTextView.setVisibility(View.VISIBLE);

            clockInTimeTextView.setVisibility(View.VISIBLE);
            clockIntime = getTime();
            clockInTimeTextView.setText(getTime());

        } else {
            clockOuttime = getTime();
            clockOutTimeTextView.setVisibility(View.VISIBLE);
            clockOutTimeTextView.setText(clockOuttime);
        }

    }

    @OnClick(R.id.buttonLunchIn)
    public void buttonLunchIn() {
        if (LunchIntime == "") {
            LunchIntime = getTime();
            lunchInTimeTextView.setVisibility(View.VISIBLE);
            lunchInTimeTextView.setText(LunchIntime);
            buttonLunchIn.setText("Lunch out");

        } else if (LunchOuttime == "") {
            LunchOuttime = getTime();
            lunchOutTimeTextView.setVisibility(View.VISIBLE);
            lunchOutTimeTextView.setText(LunchOuttime);
            buttonLunchIn.setClickable(false);
            buttonClockIn.setClickable(true);
            buttonLunchIn.setVisibility(View.GONE);


        }

    }

    private String getTime() {
        Calendar calendar = GregorianCalendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat amPmFormat = new SimpleDateFormat("a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat minutesFormat = new SimpleDateFormat("mm");
        SimpleDateFormat hoursFormat = new SimpleDateFormat("hh");
        //yyyy.MM.dd

        Date date = new Date();
        String month = monthFormat.format(date).toString();
        String hour = hoursFormat.format(new Date()).toString();
        //String minutes =Integer.toString(calendar.get(Calendar.MINUTE));
        String minutes = minutesFormat.format(new Date()).toString();
        String am_pm = amPmFormat.format(date).toString();
        String dayOfMonth = dateFormat.format(date).toString();
        String year = yearFormat.format(date).toString();

        //dayTextView.setText(dayOfWeek.toUpperCase() + " " + month.toUpperCase() + " " + year);

        String dateObject = month + "/" + dayOfMonth + "/" + year + " " + hour + ":" + minutes + am_pm;
        String time = hour + ":" + minutes + am_pm;
        Log.d(TAG, "getTime: " + dateObject);
        //Log.d(TAG, "setClock: " + hour + ":" + minutes + " " + am_pm + " " + dayOfWeek + " " + month + " " + year);

        return time;


    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                /*&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {

            // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            Log.d(TAG, "getLocation: Permissions not found");


            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else

        {
            Log.d(TAG, "getLocation: Permissions found seeking location data");

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1, this);
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
        gpsIsEnabled();

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

        gpsIsEnabled();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
                        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

                        gpsIsEnabled();
                    }

                } else {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

                }
                return;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            //Request location updates:
            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
