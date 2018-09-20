package com.maintenancesolution.ems.Views;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.maintenancesolution.R;
import com.maintenancesolution.ems.Utils.Constants;
import com.maintenancesolution.ems.Utils.PreferenceUtils;
import com.maintenancesolution.ems.Views.ListActivity.ListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    @BindView(R.id.generalMaintenanceLayout)
    ConstraintLayout generalMaintenanceLayout;
    @BindView(R.id.timeCardLayout)
    ConstraintLayout timeCardLayout;
    @BindView(R.id.InventoryCardItemZero)
    ConstraintLayout InventoryCardItemLayout;
    @BindView(R.id.InventoryCardItemOne)
    ConstraintLayout InventoryCardItemOne;
    @BindView(R.id.InventoryCardItemTwo)
    ConstraintLayout InventoryCardItemTwo;
    @BindView(R.id.list_constraint_layout)
    ConstraintLayout listConstraintLayout;
    /*@BindView(R.id.textViewNewOrder)
    TextView textViewNewOrder;*/
    MenuInflater inflater;
    @BindView(R.id.jobRequestLayout)
    ConstraintLayout jobRequestLayout;
    private String TAG = "DashboardActivity";
    private Toolbar toolbar;
    private Button refreshButton;
    private PreferenceUtils preferenceUtils;
    private String header;
    private LocationManager locationManager;
    private int userGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        getPrefUtils();

        userGroup = preferenceUtils.getUserType();

        if (userGroup == 1) {
            //Customer
            jobRequestLayout.setVisibility(View.VISIBLE);
            generalMaintenanceLayout.setVisibility(View.GONE);
            timeCardLayout.setVisibility(View.GONE);
            InventoryCardItemLayout.setVisibility(View.GONE);
            InventoryCardItemOne.setVisibility(View.GONE);
            InventoryCardItemTwo.setVisibility(View.GONE);


        } else if (userGroup == 2) {
            //Manager
            jobRequestLayout.setVisibility(View.VISIBLE);
            generalMaintenanceLayout.setVisibility(View.VISIBLE);
            timeCardLayout.setVisibility(View.VISIBLE);
            InventoryCardItemLayout.setVisibility(View.VISIBLE);
            InventoryCardItemOne.setVisibility(View.VISIBLE);
            InventoryCardItemTwo.setVisibility(View.VISIBLE);

        }

        checkCameraPermissions();

        // mListView.setEmptyView(findViewById(R.id.recipe_list_view));

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getPrefUtils();
        // Handle item selection
        switch (item.getItemId()) {
           /* case R.id.action_refresh:
                this.makeRequest();
                return true;*/
            case R.id.action_logout:
                //Logout logic goes here
                preferenceUtils.saveAuthToken("");
                preferenceUtils.saveUserType(0);
                final Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkGpsPermissions: Permissions not available");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

            //return false;
        } else {
            checkGpsPermissions();
            Log.d(TAG, "checkCameraPermissions: Permissions available");
            //gpsIsEnabled();

        }

    }

    @OnClick(R.id.jobRequestLayout)
    public void newOrder() {
        Intent startListActivity = new Intent(getApplicationContext(), CustomerRequestForm.class);
        startListActivity.putExtra("AuthenticatedUser", true);
        startListActivity.putExtra(Constants.USER_GROUP, userGroup);
        startActivity(startListActivity);

    }

    @OnClick(R.id.generalMaintenanceLayout)
    public void generalMaintenance() {
        Intent startListActivity = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(startListActivity);

    }

    @OnClick(R.id.timeCardLayout)
    public void timeCard() {
        Intent startListActivity = new Intent(getApplicationContext(), ClockActivity.class);
        startActivity(startListActivity);
    }

    @OnClick(R.id.InventoryCardItemZero)
    public void inventoryCardItemZero() {
        Intent startListActivity = new Intent(getApplicationContext(), ScanInventoryActivity.class);
        startActivity(startListActivity);
    }

    @OnClick(R.id.InventoryCardItemOne)
    public void inventoryCardItemOne() {
        Intent startListActivity = new Intent(getApplicationContext(), MovingInventoryActivity.class);
        startActivity(startListActivity);
    }

    @OnClick(R.id.InventoryCardItemTwo)
    public void inventoryCardItemTwo() {
        Intent startListActivity = new Intent(getApplicationContext(), CountingInventoryActivity.class);
        startActivity(startListActivity);
    }

    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(DashboardActivity.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }

    private void checkGpsPermissions() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
            builder.setMessage("Please enable GPS and select location method to High accuracy"); // Want to enable?
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
            //getLocation();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        checkGpsPermissions();

                    }

                } else {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        gpsIsEnabled();

                    }

                } else {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

                }
                return;

            }

        }
    }

}
