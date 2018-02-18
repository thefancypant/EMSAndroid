package com.android.maintenancesolution.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.maintenancesolution.Models.Area;
import com.android.maintenancesolution.Models.Asset;
import com.android.maintenancesolution.Models.CheckAssetRequest;
import com.android.maintenancesolution.Models.MoveAssetsRequest;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.R;
import com.android.maintenancesolution.Utils.ExpandableHeightGridView;
import com.android.maintenancesolution.Utils.GeneralUtils;
import com.android.maintenancesolution.Utils.PreferenceUtils;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovingInventoryActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {


    @BindView(R.id.currentareaSpinner)
    Spinner currentareaSpinner;
    @BindView(R.id.codeGridView)
    ExpandableHeightGridView codeGridView;
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
    private String currentAreaselectedPosition;
    private String selectedCurrentAreaId = null;
    private String selectedSendingAreaId = null;
    private List<String> assetIdList = new ArrayList<>();
    private int updateCallNumber = 0;
    private String qrCode = "";
    private String asset_ids = "";
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
        adapter = new ArrayAdapter<String>(this, R.layout.gridview_item, R.id.textViewJob, assetCodesList);

        //adapter.
        codeGridView.setExpanded(true);
        codeGridView.setAdapter(adapter);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.d(TAG, "onQRCodeRead: " + text);

        //getAssetCode(text);
        if (!qrCode.equals(text)) {
            qrCode = text;
            if (selectedCurrentAreaId != null) {
                //qrCode = text;
                checkArea(text);
            } else {

                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View promptView = layoutInflater.inflate(R.layout.popup_validation, null);
                final AlertDialog alertD = new AlertDialog.Builder(this).create();
                TextView message = promptView.findViewById(R.id.textViewMessage);
                message.setText("Please select a valid area");
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
                        //finish();
                    }
                });
            }
        }

    }


    private void checkArea(String text) {
        CheckAssetRequest checkAssetRequest = new CheckAssetRequest(text, selectedCurrentAreaId);

        NetworkService
                .getInstance()
                .checkAsset(header, checkAssetRequest)
                .enqueue(new Callback<Asset>() {
                    @Override
                    public void onResponse(Call<Asset> call, Response<Asset> response) {

                        processCheckAsset(response);

                    }

                    @Override
                    public void onFailure(Call<Asset> call, Throwable t) {

                    }
                });

    }

    private void processCheckAsset(Response<Asset> response) {

        if (response.code() >= 200 && response.code() < 300) {
            Asset asset = response.body();
            if (asset.getMessage() != null && asset.getMessage().equals("failure")) {
                //if(qrCode)
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View promptView = layoutInflater.inflate(R.layout.popup_validation, null);
                final AlertDialog alertD = new AlertDialog.Builder(this).create();
                TextView message = promptView.findViewById(R.id.textViewMessage);
                message.setText("Asset does not belong to this area");
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
                        //finish();
                    }
                });

            } else {
                codeGridView.setVisibility(View.VISIBLE);

                if (assetCodesList.size() == 0) {
                    assetIdList.add(response.body().getId());
                    assetCodesList.add(response.body().getCode());
                    adapter.notifyDataSetChanged();
                } else if (!assetCodesList.contains(response.body().getCode())) {
                    assetIdList.add(response.body().getId());
                    assetCodesList.add(response.body().getCode());
                    adapter.notifyDataSetChanged();
                }

            }

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
                        if (!spinnerArray.get(position).equals("Select a center")) {

                            selectedCurrentAreaId = Integer.toString(response.body().get(position - 1).getId());
                            qrCode = "";
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


            sendingAreaSpinner.setAdapter(arrayAdapter);

            sendingAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position != 0) {
                        if (!spinnerArray.get(position).equals("Select a center")) {
                            if (!spinnerArray.get(position).equals("Select a center")) {
                                selectedSendingAreaId = Integer.toString(response.body().get(position - 1).getId());
                                Log.d(TAG, "onItemSelected: " + response.body().get(position - 1).getName());
                                Log.d(TAG, "onItemSelected: " + selectedSendingAreaId.toString());
                            }
                        }
                    } else {
                        selectedSendingAreaId = null;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    selectedSendingAreaId = null;
                }
            });

        }
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

        codeGridView.setVisibility(View.VISIBLE);
        if (response.code() >= 200 && response.code() < 300) {
            if (assetCodesList.size() == 0) {
                assetIdList.add(response.body().getId());
                assetCodesList.add(response.body().getCode());
                adapter.notifyDataSetChanged();
            } else if (!assetCodesList.contains(response.body().getCode())) {
                assetIdList.add(response.body().getId());
                assetCodesList.add(response.body().getCode());
                adapter.notifyDataSetChanged();
            }
            //mScannerView.startCamera();
            //mScannerView.setActivated(true);

            //mScannerView.setOnQRCodeReadListener(this);

        }
    }

    @OnClick(R.id.buttonSubmitRequest)
    public void buttonSubmitRequest() {
        if (selectedCurrentAreaId != null && selectedSendingAreaId != null && !selectedSendingAreaId.equals(selectedCurrentAreaId)) {
            if (assetIdList.size() != 0) {
                if (assetIdList.size() == 1) {

                    asset_ids = assetIdList.get(0);
                } else {
                    for (int i = 0; i < assetIdList.size(); i++) {

                        asset_ids = asset_ids + "," + assetIdList.get(i);

                    }
                }
                MoveAssetsRequest moveAssetsRequest = new MoveAssetsRequest(asset_ids, selectedSendingAreaId);


                NetworkService
                        .getInstance()
                        .moveAssets(header, moveAssetsRequest)
                        .enqueue(new Callback<Asset>() {
                            @Override
                            public void onResponse(Call<Asset> call, Response<Asset> response) {

                                processAssetMovement(response);


                            }

                            @Override
                            public void onFailure(Call<Asset> call, Throwable t) {
                                Log.d(TAG, "onFailure: " + t.toString());
                            }
                        });
            } else {
                Dialog dialog = new GeneralUtils(this).showValidationPopup(this, getString(R.string.scan_assets));
                dialog.show();

            }


        } else {
            Dialog dialog = new GeneralUtils(this).showValidationPopup(this, getString(R.string.select_sending_to_and_current_area));
            dialog.show();

        }

    }

    private void processAssetMovement(Response<Asset> response) {
        if (response.code() >= 200 && response.code() < 300) {

            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.popup_validation, null);
            final AlertDialog alertD = new AlertDialog.Builder(this).create();
            TextView message = promptView.findViewById(R.id.textViewMessage);
            message.setText("Inventory moved succesfully");
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


        } else {


            Dialog dialog = new GeneralUtils(this).showValidationPopup(this, "Inventory moving failed.Please try again.");
            dialog.show();

        }
    }

    /*private void updateAssetLocation(String assetId, int i) {
        UpdateAssetLocationRequest updateAssetLocationRequest = new UpdateAssetLocationRequest(selectedSendingAreaId);
        final int ii = i;
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
        if (updateCallNumber == assetIdList.size()) {
            *//*GeneralUtils generalUtils = new GeneralUtils(this);
            Dialog dialog = generalUtils.showValidationPopup(this,getString(R.string.select_sending_to_and_current_area));
            generalUtils.getBtnOk().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            dialog.show();*//*
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

        }
        Log.d(TAG, "updateAssets onResponse: " + Integer.toString(response.code()));
    }

*/
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
