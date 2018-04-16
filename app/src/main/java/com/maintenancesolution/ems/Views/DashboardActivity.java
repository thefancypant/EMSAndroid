package com.maintenancesolution.ems.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.maintenancesolution.R;
import com.maintenancesolution.ems.Utils.PreferenceUtils;
import com.maintenancesolution.ems.Views.ListActivity.ListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {

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
    MenuInflater inflater;
    private String TAG = "DashboardActivity";
    private android.support.v7.widget.Toolbar toolbar;
    private Button refreshButton;
    private PreferenceUtils preferenceUtils;
    private String header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        getPrefUtils();
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
                final Intent intent = new Intent(DashboardActivity.this, UserSelectorActivity.class);
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
            Log.d(TAG, "checkCameraPermissions: Permissions available");
            //gpsIsEnabled();

        }

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

                        //Request location updates:
                        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

                        //gpsIsEnabled();
                    }

                } else {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                }
                return;
            }

        }
    }

}
