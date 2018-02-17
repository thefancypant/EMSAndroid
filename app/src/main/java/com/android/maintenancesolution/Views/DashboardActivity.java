package com.android.maintenancesolution.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import com.android.maintenancesolution.R;
import com.android.maintenancesolution.Views.ListActivity.ListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.generalMaintenanceLayout)
    ConstraintLayout generalMaintenanceLayout;
    @BindView(R.id.timeCardLayout)
    ConstraintLayout timeCardLayout;
    @BindView(R.id.InventoryCardLayout)
    ConstraintLayout InventoryCardLayout;
    @BindView(R.id.InventoryCardItemZero)
    ConstraintLayout InventoryCardItemLayout;
    @BindView(R.id.InventoryCardItemOne)
    ConstraintLayout InventoryCardItemOne;
    @BindView(R.id.InventoryCardItemTwo)
    ConstraintLayout InventoryCardItemTwo;
    @BindView(R.id.list_constraint_layout)
    ConstraintLayout listConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

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
        Intent startListActivity = new Intent(getApplicationContext(), CheckingInventoryActivity.class);
        startActivity(startListActivity);
    }
}
