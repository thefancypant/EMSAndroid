package com.android.maintenancesolution.Views;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import com.android.maintenancesolution.Models.Area;
import com.android.maintenancesolution.Models.Asset;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.R;
import com.android.maintenancesolution.Utils.PreferenceUtils;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovingInventoryActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {


    @BindView(R.id.currentareaSpinner)
    Spinner currentareaSpinner;
    @BindView(R.id.codeGridView)
    GridView codeGridView;
    @BindView(R.id.sendingAreaSpinner)
    Spinner sendingAreaSpinner;
    @BindView(R.id.buttonSubmitRequest)
    Button buttonSubmitRequest;
    @BindView(R.id.scanner)
    QRCodeReaderView mScannerView;
    ArrayAdapter<String> adapter;
    private String TAG = "ScanInventoryActivity";
    private List<String> assetCodesList = new ArrayList<>();
    private List<String> qrCodeList = new ArrayList<>();
    //private String[] assetCodesArray = new String[] {};
    private PreferenceUtils preferenceUtils;
    private String header;
    //private ZXingScannerView mScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving_inventory);
        ButterKnife.bind(this);
        getPrefUtils();
        setupAreas();
        setupGridview();
        mScannerView.setOnQRCodeReadListener(this);
       /* mScannerView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        mScannerView.setAutofocusInterval(2000L);*/

    }

    private void setupGridview() {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, assetCodesList);
        //adapter.
        codeGridView.setAdapter(adapter);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.d(TAG, "onQRCodeRead: " + text);
        mScannerView.startCamera();
        if (!qrCodeList.contains(text)) {
            qrCodeList.add(text);
            mScannerView.stopCamera();
            getAssetCode(text);
        }
    }


    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(MovingInventoryActivity.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }


    private void setupAreas() {

        NetworkService
                .getInstance()
                .getAreas(header)
                .enqueue(new Callback<List<Area>>() {
                    @Override
                    public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                        processAreas(response);
                    }

                    @Override
                    public void onFailure(Call<List<Area>> call, Throwable t) {

                    }
                });
    }

    private void processAreas(final Response<List<Area>> response) {
        if (response.code() >= 200 && response.code() < 300) {
            final ArrayList<String> spinnerArray = new ArrayList<>();
            spinnerArray.add("Select a center");

            for (int i = 0; i < response.body().size(); i++) {
                spinnerArray.add(response.body().get(i).getName());
            }

            currentareaSpinner.setEnabled(true);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
            currentareaSpinner.setAdapter(arrayAdapter);

            currentareaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position != 0) {
                        if (!spinnerArray.get(position).equals("Select Work Type")) {
                            // selectedCenter = Integer.toString(position);

                            // for (int i = 0; i < response.body().size(); i++) {

                            if (position != 0) {
                                // selectedCenterCode = Integer.toString(response.body().get(position - 1).getId());

                            }
                            //}
                            //Log.d(TAG, "onItemSelected: " + selectedCenter);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //selectedCenter = "Select Work Type";

                }
            });


            sendingAreaSpinner.setAdapter(arrayAdapter);

            sendingAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position != 0) {
                        if (!spinnerArray.get(position).equals("Select Work Type")) {
                            // selectedCenter = Integer.toString(position);

                            // for (int i = 0; i < response.body().size(); i++) {

                            if (position != 0) {
                                // selectedCenterCode = Integer.toString(response.body().get(position - 1).getId());

                            }
                            //}
                            //Log.d(TAG, "onItemSelected: " + selectedCenter);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //selectedCenter = "Select Work Type";

                }
            });

        }
    }

    private void getAssetCode(String id) {

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
            assetCodesList.add(response.body().getCode());
            adapter.notifyDataSetChanged();
            mScannerView.startCamera();
            mScannerView.setOnQRCodeReadListener(this);

        }
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
