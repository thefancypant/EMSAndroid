package com.maintenancesolution.ems.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.maintenancesolution.R;
import com.maintenancesolution.ems.Models.Area;
import com.maintenancesolution.ems.Models.Asset;
import com.maintenancesolution.ems.Models.UpdateAssetLocationRequest;
import com.maintenancesolution.ems.Network.NetworkService;
import com.maintenancesolution.ems.Utils.GeneralUtils;
import com.maintenancesolution.ems.Utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckingInventoryActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {


    @BindView(R.id.scanner)
    QRCodeReaderView mScannerView;
    /* @BindView(R.id.codeTextView)
     TextView codeTextView;*/
    @BindView(R.id.typeTextView)
    TextView typeTextView;
    @BindView(R.id.currentareaSpinner)
    Spinner currentareaSpinner;
    @BindView(R.id.buttonSubmitRequest)
    Button buttonSubmitRequest;
    @BindView(R.id.assetLayout)
    ConstraintLayout assetLayout;
    private String TAG = "CheckingInventoryActivity";
    private PreferenceUtils preferenceUtils;
    private String header;
    private String selectedCurrentAreaId = null;
    private String currentArea;
    private String assetId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_inventory);
        ButterKnife.bind(this);
        layoutEnable(false);
        assetLayout.setEnabled(false);
        //assetLayout.setVisibility(View.INVISIBLE);
        getPrefUtils();
        setupAreas();


        mScannerView.setOnQRCodeReadListener(this);

    }

    private void layoutEnable(boolean bool) {
        if (bool) {
            assetLayout.setVisibility(View.VISIBLE);
        } else {
            assetLayout.setVisibility(View.GONE);

        }

        currentareaSpinner.setClickable(bool);
        buttonSubmitRequest.setClickable(bool);

    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.d(TAG, "onQRCodeRead: " + text);

        mScannerView.stopCamera();
        getAssetCode(text);

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
            layoutEnable(true);
            Asset asset = response.body();
            assetId = asset.getId();
            //codeTextView.setText(asset.getCode());
            typeTextView.setText(asset.getType().getName());
            currentArea = asset.getArea().getName();
        }
    }

    private void updateAssetLocation(String assetId) {
        UpdateAssetLocationRequest updateAssetLocationRequest = new UpdateAssetLocationRequest(selectedCurrentAreaId);

        NetworkService
                .getInstance()
                .updateAssetLocation(header, assetId, updateAssetLocationRequest)
                .enqueue(new Callback<Asset>() {
                    @Override
                    public void onResponse(Call<Asset> call, Response<Asset> response) {

                        processUpdateLocation(response);

                    }

                    @Override
                    public void onFailure(Call<Asset> call, Throwable t) {
                        Log.d(TAG, "updateAssets onFailure: " + t.toString());


                    }
                });
    }

    private void processUpdateLocation(Response<Asset> response) {
        //if (updateCallNumber == assetIdList.size()) {
            /*GeneralUtils generalUtils = new GeneralUtils(this);
            Dialog dialog = generalUtils.showValidationPopup(this,getString(R.string.select_sending_to_and_current_area));
            generalUtils.getBtnOk().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            dialog.show();*/
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.popup_validation, null);
        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        TextView message = promptView.findViewById(R.id.textViewMessage);
        message.setText("Assets location update completed");
        Button btnOk = promptView.findViewById(R.id.buttonOk);
        //setBtnOk(btnOk);
        alertD.setView(promptView);
        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertD.setCanceledOnTouchOutside(false);
        alertD.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertD.dismiss();
                finish();
            }
        });
        //return alertD;

        //  }
        Log.d(TAG, "updateAssets onResponse: " + Integer.toString(response.code()));
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
                        if (!spinnerArray.get(position).equals("Select a center")) {

                            selectedCurrentAreaId = Integer.toString(response.body().get(position - 1).getId());
                            Log.d(TAG, "onItemSelected: " + response.body().get(position - 1).getName());
                            Log.d(TAG, "onItemSelected: " + selectedCurrentAreaId.toString());
                        }
                    } else {
                        selectedCurrentAreaId = null;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    selectedCurrentAreaId = null;
                    //selectedCenter = "Select Work Type";

                }
            });


        }
    }

    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(CheckingInventoryActivity.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }


    @OnClick(R.id.buttonSubmitRequest)
    public void submit() {
        if (selectedCurrentAreaId != null) {
            if (assetId != null) {
                updateAssetLocation(assetId);
            } else {
                Dialog dialog = new GeneralUtils(this).showValidationPopup(this, getString(R.string.scan_assets));
                dialog.show();

            }


        } else {
            Dialog dialog = new GeneralUtils(this).showValidationPopup(this, getString(R.string.select_a_valid_code));
            dialog.show();

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
