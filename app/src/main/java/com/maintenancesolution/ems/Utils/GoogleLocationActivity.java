package com.maintenancesolution.ems.Utils;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class GoogleLocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String TAG = "GoogleLocationActivity";
    protected GoogleApiClient mGoogleApiClient;
    LocationManager locationManager;
    GpsLocationTracker mGpsLocationTracker;
    LocationRequest mLocationRequest;
    double lattiude;
    double longitude;
    private Location mLastLocation;
    private LocationCallback mLocationCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildGoogleApiClient();


    }

    void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.d(TAG, "onLocationResult: ");
                if (locationResult == null) {
                    return;
                } else {


                    mLastLocation = locationResult.getLastLocation();
                    longitude = mLastLocation.getLongitude();
                    lattiude = mLastLocation.getLatitude();

                    Log.i(TAG, String.format("updatedLatitude: %s", mLastLocation.getLatitude()));
                    Log.i(TAG, String.format("updatedLongitude: %s", mLastLocation.getLongitude()));
                }

            }
        };

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(1000);

        // LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,mLocationRequest,getApplicationContext());

        if (mLastLocation != null) {
            // Determine whether a Geocoder is available.
            Log.i(TAG, String.format("latitude: %s", mLastLocation.getLatitude()));
            Log.i(TAG, String.format("longitude: %s", mLastLocation.getLongitude()));
            longitude = mLastLocation.getLongitude();
            lattiude = mLastLocation.getLatitude();
        }

        updateLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        mGoogleApiClient.connect();

    }

    public void updateLocation() {

        Log.d(TAG, "updateLocation: +");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {


        Log.d(TAG, "onLocationResult: ");
        if (location == null) {
            return;
        } else {


            mLastLocation = location;
            Log.i(TAG, String.format("updatedLatitude: %s", mLastLocation.getLatitude()));
            Log.i(TAG, String.format("updatedLongitude: %s", mLastLocation.getLongitude()));
        }

    }


    public Location getLocation()

    {
        return mLastLocation;
    }

}
