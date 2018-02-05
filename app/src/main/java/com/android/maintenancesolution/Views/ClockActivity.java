package com.android.maintenancesolution.Views;

import android.Manifest;
import android.content.BroadcastReceiver;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.maintenancesolution.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ClockActivity extends AppCompatActivity implements LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static IntentFilter s_intentFilter;

    static {
        s_intentFilter = new IntentFilter();
        s_intentFilter.addAction(Intent.ACTION_TIME_TICK);
        s_intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        s_intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
    }

    private final String TAG = "ClockActivity";
    @BindView(R.id.hoursTextView)
    TextView hoursTextView;
    @BindView(R.id.minutesTextView)
    TextView minutesTextView;
    @BindView(R.id.amPmTextView)
    TextView amPmTextView;
    @BindView(R.id.dayTextView)
    TextView dayTextView;
    private final BroadcastReceiver m_timeChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(Intent.ACTION_TIME_CHANGED) ||
                    action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                setClock();
            }
        }
    };
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
    double lattiude;
    double longitude;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        ButterKnife.bind(this);
        registerReceiver(m_timeChangedReceiver, s_intentFilter);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //enableGps();
        checkGpsPermissions();
        setClock();

    }

    private void setClock() {

        /*Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("hh aa");
        Log.d(TAG, "setClock: "+currentTime.toString());
        //Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(currentTime);   // assigns calendar to given date
        int hours = calendar.get(Calendar.HOUR); // gets hour in 24h format
        int minutes = calendar.get(Calendar.MINUTE);
        int am_pm = calendar.get(Calendar.AM_PM);
        calendar.get(Calendar.Mont)*/
        Calendar calendar = GregorianCalendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        SimpleDateFormat amPmFormat = new SimpleDateFormat("a");
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("E");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
        SimpleDateFormat minutesFormat = new SimpleDateFormat("m");

        Date date = new Date();
        String month = monthFormat.format(date).toString();
        String hour = Integer.toString(calendar.get(Calendar.HOUR));        // gets hour in 12h format
        //String minutes =Integer.toString(calendar.get(Calendar.MINUTE));
        String minutes = minutesFormat.format(new Date()).toString();
        String am_pm = amPmFormat.format(date).toString();
        String dayOfWeek = dayOfWeekFormat.format(date).toString();
        String year = yearFormat.format(date).toString();

        //hoursTextView.setText(hour+":");
        // minutesTextView.setText(minutes);
        //amPmTextView.setText(am_pm);
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
