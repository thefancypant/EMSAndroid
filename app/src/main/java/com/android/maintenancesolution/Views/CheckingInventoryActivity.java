package com.android.maintenancesolution.Views;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.maintenancesolution.R;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckingInventoryActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {


    @BindView(R.id.scanner)
    // ZXingScannerView mScannerView;
            QRCodeReaderView mScannerView;
    private String TAG = "CheckingInventoryActivity";
    //private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_inventory);
        ButterKnife.bind(this);
        mScannerView.setOnQRCodeReadListener(this);

        //mScannerView = new ZXingScannerView(this);// Programmatically initialize the scanner view
        //setContentView(mScannerView);                        // Set the scanner view as the content view

    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.d(TAG, "onQRCodeRead: " + text);

        mScannerView.stopCamera();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {


    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}
