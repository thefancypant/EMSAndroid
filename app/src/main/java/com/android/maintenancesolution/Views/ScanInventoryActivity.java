package com.android.maintenancesolution.Views;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.maintenancesolution.Models.Asset;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.R;
import com.android.maintenancesolution.Utils.PreferenceUtils;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanInventoryActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    @BindView(R.id.scanner)
    // ZXingScannerView mScannerView;
            QRCodeReaderView mScannerView;
    @BindView(R.id.areaTextView)
    TextView areaTextView;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.codeTextView)
    TextView codeTextView;
    @BindView(R.id.typeTextView)
    TextView typeTextView;
    @BindView(R.id.assetLayout)
    ConstraintLayout assetLayout;
    private String TAG = "ScanInventoryActivity";
    private PreferenceUtils preferenceUtils;
    private String header;
    //private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_inventory);
        ButterKnife.bind(this);
        getPrefUtils();
        assetLayout.setVisibility(View.INVISIBLE);
        mScannerView.setOnQRCodeReadListener(this);

        //mScannerView = new ZXingScannerView(this);// Programmatically initialize the scanner view
        //setContentView(mScannerView);                        // Set the scanner view as the content view

    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.d(TAG, "onQRCodeRead: " + text);


        mScannerView.stopCamera();
        getAssetCode(text);
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

    private void getAssetCode(String id) {
        // mScannerView.surfaceChanged();
        NetworkService
                .getInstance()
                .getAsset(header, id)
                .enqueue(new Callback<Asset>() {
                    @Override
                    public void onResponse(Call<Asset> call, Response<Asset> response) {
                        processAssetCode(response);
                    }

                    @Override
                    public void onFailure(Call<Asset> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.toString());
                    }
                });
    }

    private void processAssetCode(Response<Asset> response) {

        if (response.code() >= 200 && response.code() < 300) {

            assetLayout.setVisibility(View.VISIBLE);
            Asset asset = response.body();
            areaTextView.setText(asset.getArea().getName());
            descriptionTextView.setText(asset.getDescription());
            codeTextView.setText(asset.getCode());
            typeTextView.setText(asset.getType().getName());
        }


    }

    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(ScanInventoryActivity.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }


}
