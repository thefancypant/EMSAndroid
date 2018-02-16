package com.android.maintenancesolution.Views;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.maintenancesolution.R;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
//import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanInventoryActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private static final String TAG = "ScanInventoryActivity";
    @BindView(R.id.scanner)
    // ZXingScannerView mScannerView;
            QRCodeReaderView mScannerView;
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

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

   /* @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }*/
}
