package com.android.maintenancesolution.Views;

import android.app.AlertDialog;
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
import com.android.maintenancesolution.Models.CountAssetsRequest;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.R;
import com.android.maintenancesolution.Utils.ExpandableHeightGridView;
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

import static com.android.maintenancesolution.Network.NetworkService.getInstance;

public class CountingInventoryActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    public String TAG = "CountingInventoryActivity";

    @BindView(R.id.currentareaSpinner)
    Spinner currentareaSpinner;
    @BindView(R.id.scanner)
    QRCodeReaderView scanner;
    @BindView(R.id.codeGridView)
    ExpandableHeightGridView codeGridView;
    @BindView(R.id.buttonSubmitRequest)
    Button buttonSubmitRequest;
    @BindView(R.id.numberOfItemsTextView)
    TextView numbeOfitems;
    private PreferenceUtils preferenceUtils;
    private String header;
    private String qrCode = "";
    private List<String> assetCodesList = new ArrayList<>();
    private List<String> qrCodeList = new ArrayList<>();
    private List<String> assetIdList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String selectedCurrentAreaId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_inventory);
        ButterKnife.bind(this);
        getPrefUtils();
        setupAreas();
        setupGridview();


        scanner.setOnQRCodeReadListener(this);
        scanner.setAutofocusInterval(2000L);
        //scanner.setAu


    }

    private void setupGridview() {
        adapter = new ArrayAdapter<String>(this, R.layout.gridview_item, R.id.textViewJob, assetCodesList);
        numbeOfitems.setText(Integer.toString(assetCodesList.size()));
        //adapter.
        codeGridView.setExpanded(true);
        codeGridView.setAdapter(adapter);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        Log.d(TAG, "onQRCodeRead: " + text);

        Log.d(TAG, "onQRCodeRead: scaleX" + Float.toString(scanner.getScaleX()));
        Log.d(TAG, "onQRCodeRead: scaleY" + Float.toString(scanner.getScaleY()));


        //scanner.stopCamera();

        if (!qrCode.equals(text)) {
            qrCode = text;
            if (selectedCurrentAreaId != null) {
                //qrCode = text;
                checkArea(text);
            } else {
                scanner.setQRDecodingEnabled(false);

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
                        scanner.setQRDecodingEnabled(true);
                        alertD.dismiss();
                        //finish();
                    }
                });
            }
        }
        //getAssetCode(text);


    }

    private void setupAreas() {

        getInstance()
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
                            qrCode = "";
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
                scanner.setQRDecodingEnabled(false);
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
                        scanner.setQRDecodingEnabled(true);
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
                numbeOfitems.setText(Integer.toString(assetCodesList.size()));


            }

        }

    }

    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(CountingInventoryActivity.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }


    @OnClick(R.id.buttonSubmitRequest)
    public void Submit() {
        scanner.setQRDecodingEnabled(false);
        if (selectedCurrentAreaId == null || assetIdList.size() == 0) {
            scanner.setQRDecodingEnabled(false);
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.popup_validation, null);
            final AlertDialog alertD = new AlertDialog.Builder(this).create();
            TextView message = promptView.findViewById(R.id.textViewMessage);
            message.setText("Please Select a valid area or assets");
            Button btnOk = promptView.findViewById(R.id.buttonOk);
            //setBtnOk(btnOk);
            alertD.setView(promptView);
            alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertD.setCanceledOnTouchOutside(false);
            alertD.show();
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanner.setQRDecodingEnabled(true);
                    alertD.dismiss();
                    //finish();
                }
            });

        } else {
            String asset_ids = "";
            if (assetIdList.size() == 1) {

                asset_ids = assetIdList.get(0);
            } else {
                for (int i = 0; i < assetIdList.size(); i++) {

                    asset_ids = asset_ids + "," + assetIdList.get(i);

                }
            }
            CountAssetsRequest countAssetsRequest = new CountAssetsRequest(asset_ids, selectedCurrentAreaId);


            NetworkService
                    .getInstance()
                    .countAssets(header, countAssetsRequest)
                    .enqueue(new Callback<Asset>() {
                        @Override
                        public void onResponse(Call<Asset> call, Response<Asset> response) {

                            processSubmitCount(response);


                        }

                        @Override
                        public void onFailure(Call<Asset> call, Throwable t) {
                            Log.d(TAG, "onFailure: ");
                            scanner.setQRDecodingEnabled(true);
                        }
                    });
        }


    }

    private void processSubmitCount(Response<Asset> response) {

        if (response.code() >= 200 && response.code() < 300) {

            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.popup_validation, null);
            final AlertDialog alertD = new AlertDialog.Builder(this).create();
            TextView message = promptView.findViewById(R.id.textViewMessage);
            message.setText("Inventory counting succesful");
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

            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.popup_validation, null);
            final AlertDialog alertD = new AlertDialog.Builder(this).create();
            TextView message = promptView.findViewById(R.id.textViewMessage);
            message.setText("Inventory counting failed.Please try again.");
            Button btnOk = promptView.findViewById(R.id.buttonOk);
            //setBtnOk(btnOk);
            alertD.setView(promptView);
            alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertD.setCanceledOnTouchOutside(false);
            alertD.show();
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanner.setQRDecodingEnabled(true);
                    alertD.dismiss();
                    //finish();
                }
            });


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanner.stopCamera();
    }
}
