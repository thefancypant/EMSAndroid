package com.maintenancesolution.ems.Views;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.maintenancesolution.R;
import com.maintenancesolution.ems.Misc.WorkTypeAdapater;
import com.maintenancesolution.ems.Models.Center;
import com.maintenancesolution.ems.Models.CustomerRequest;
import com.maintenancesolution.ems.Models.Job;
import com.maintenancesolution.ems.Models.Token;
import com.maintenancesolution.ems.Network.NetworkService;
import com.maintenancesolution.ems.Utils.Constants;
import com.maintenancesolution.ems.Utils.FileUtils;
import com.maintenancesolution.ems.Utils.GeneralUtils;
import com.maintenancesolution.ems.Utils.PreferenceUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerRequestForm extends AppCompatActivity {

    private static final String TAG = "CustomerRequestForm";
    private static int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    final String dir = Environment.getExternalStoragePublicDirectory(".Consulting") + "/Folder/";
    List<Job> workTypesList = new ArrayList<>();
    WorkTypeAdapater workTypeAdapater;
    ArrayList<String> spinnerArray = new ArrayList<>();
    @BindView(R.id.textViewName)
    EditText mNameEditText;
    @BindView(R.id.imageViewName)
    ImageView imageViewName;
    @BindView(R.id.textViewAddress)
    EditText mAddressEditText;
    @BindView(R.id.imageViewAddress)
    ImageView imageViewAddress;
    @BindView(R.id.textViewEmail)
    EditText mEmailEditText;
    @BindView(R.id.imageViewEmail)
    ImageView imageViewEmail;
    @BindView(R.id.editTextPhoneNumber)
    EditText mPhoneNumberEditText;
    @BindView(R.id.imageViewPhone)
    ImageView imageViewPhone;
    @BindView(R.id.imageViewPhoto1)
    ImageView imageViewPhoto1;
    @BindView(R.id.imageViewPhoto2)
    ImageView imageViewPhoto2;
    @BindView(R.id.imageViewPhoto3)
    ImageView imageViewPhoto3;
    @BindView(R.id.imageViewPhoto4)
    ImageView imageViewPhoto4;
    @BindView(R.id.imageViewPhoto1Logo)
    ImageView imageViewPhoto1Logo;
    @BindView(R.id.imageViewPhoto2Logo)
    ImageView imageViewPhoto2Logo;
    @BindView(R.id.imageViewPhoto3Logo)
    ImageView imageViewPhoto3Logo;
    @BindView(R.id.imageViewPhoto4Logo)
    ImageView imageViewPhoto4Logo;
    @BindView(R.id.spinnerWorkType)
    Spinner mJobsSpinner;
    @BindView(R.id.imageViewTools)
    ImageView imageViewTools;
    @BindView(R.id.imageViewArrow)
    ImageView imageViewArrow;
    @BindView(R.id.listViewSelectedJobs)
    ListView mWorkTypesListView;
    @BindView(R.id.editTextDescription)
    EditText mNotesEditText;
    @BindView(R.id.imageViewDescription)
    ImageView imageViewDescription;
    @BindView(R.id.buttonSubmitRequest)
    Button submitButton;
    GeneralUtils generalUtils;
    CustomerRequest customerRequest;
    @BindView(R.id.spinnerCenter)
    Spinner spinnerCenter;
    @BindView(R.id.imageViewCenter)
    ImageView imageViewCenter;
    @BindView(R.id.imageViewArrowCenters)
    ImageView imageViewArrowCenters;
    @BindView(R.id.editTextNotToExceed)
    EditText editTextNotToExceed;
    @BindView(R.id.radioButton1)
    AppCompatRadioButton radioButton1;
    @BindView(R.id.textViewRadioButton1)
    TextView textViewRadioButton1;
    @BindView(R.id.radioButton2)
    AppCompatRadioButton radioButton2;
    @BindView(R.id.textViewRadioButton2)
    TextView textViewRadioButton2;
    private ArrayList<String> jobsSelectedList = new ArrayList<>();
    private String cameraImage;
    private File cameraFile;
    private File cameraCompressedFile;
    private ImageView selectedImageView;
    private File compressedFileOne;
    private File compressedFileTwo;
    private File compressedFileThree;
    private File compressedFileFour;
    private File galleryFile;
    private File galleryCompressedFile;
    private MultipartBody.Part photoPart1;
    private MultipartBody.Part photoPart2;
    private MultipartBody.Part photoPart3;
    private MultipartBody.Part photoPart4;
    private int MY_PERMISSIONS_CAMERA = 1;
    private int MY_PERMISSIONS_GALLERY = 2;
    private boolean authenticatedUser = false;
    private PreferenceUtils preferenceUtils;
    private String header;
    private int userGroup;
    private ArrayList<String> spinnerCentersArray = new ArrayList<>();

    private int centerId = 0;
    private int notToExceed = 0;
    private int approvedFromApp = 2;

    //private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_request_form);
        ButterKnife.bind(this);

        if (getIntent().getBooleanExtra("AuthenticatedUser", false)) {
            authenticatedUser = true;
            getPrefUtils();
        }

        userGroup = getIntent().getIntExtra(Constants.USER_GROUP, 1);

        if (userGroup == 1) {
            //Customer
            radioButton1.setVisibility(View.VISIBLE);
            textViewRadioButton1.setVisibility(View.VISIBLE);
            radioButton2.setVisibility(View.VISIBLE);
            textViewRadioButton2.setVisibility(View.VISIBLE);

        } else if (userGroup == 2) {
            //Manager
            radioButton1.setVisibility(View.GONE);
            textViewRadioButton1.setVisibility(View.GONE);
            radioButton2.setVisibility(View.GONE);
            textViewRadioButton2.setVisibility(View.GONE);
        }


        generalUtils = new GeneralUtils(CustomerRequestForm.this);
        mPhoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        //generalUtils.setEditTextUI(mEmailEditText, R.drawable.gray_email, R.drawable.blue_email, imageViewEmail);
        //generalUtils.setEditTextUI(mPhoneNumberEditText, R.drawable.gray_phone, R.drawable.blue_phone, imageViewPhone);
        //generalUtils.setEditTextUI(mNotesEditText, R.drawable.gray_request, R.drawable.blue_request, imageViewDescription);
        //generalUtils.setEditTextUI(mNameEditText);
        //generalUtils.setEditTextUI(editTextNotToExceed);

        //generalUtils.setEditTextUI(mAddressEditText);
        mNotesEditText = findViewById(R.id.editTextDescription);
        mNotesEditText.setMaxLines(4);
        mNotesEditText.setHorizontallyScrolling(false);

        mJobsSpinner.setEnabled(false);
        spinnerCenter.setEnabled(false);
        imageViewArrow.setColorFilter(getResources().getColor(R.color.login_page_background));
        mWorkTypesListView.setVisibility(View.GONE);

        getJobs();
        getCenters();

        mWorkTypesListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmit();
            }
        });

       /* mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(getApplicationContext(),charSequence.toString(),Toast.LENGTH_LONG).show();


            }

            @Override
            public void afterTextChanged(Editable editable) {




            }
        });*/
        /*mEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focusBoolean) {
                if (!focusBoolean) {
                    //Toast.makeText(getApplicationContext(),mEmailEditText.getText().toString(),Toast.LENGTH_LONG).show();
                    if ((!mEmailEditText.getText().toString().trim().equals("")) &&
                            mPhoneNumberEditText.getText().toString().trim().equals("") &&
                            mNameEditText.getText().toString().trim().equals("")) {
                        getCustomerInfoByEmail(mEmailEditText.getText().toString());
                    }

                }
            }
        });

        mPhoneNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focusBoolean) {
                if (!focusBoolean) {
                    //Toast.makeText(getApplicationContext(),mEmailEditText.getText().toString(),Toast.LENGTH_LONG).show();
                    if ((mEmailEditText.getText().toString().trim().equals("")) &&
                            !mPhoneNumberEditText.getText().toString().trim().equals("") &&
                            mNameEditText.getText().toString().trim().equals("")) {

                        getCustomerInfoByPhone(mPhoneNumberEditText.getText().toString());
                    }
                }
            }
        });

        mNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focusBoolean) {
                if (!focusBoolean) {
                    //Toast.makeText(getApplicationContext(),mEmailEditText.getText().toString(),Toast.LENGTH_LONG).show();
                    if ((mEmailEditText.getText().toString().trim().equals("")) &&
                            mPhoneNumberEditText.getText().toString().trim().equals("") &&
                            !mNameEditText.getText().toString().trim().equals("")) {

                        getCustomerInfoByName(mNameEditText.getText().toString());
                    }

                }
            }
        });
*/
        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    radioButton2.setChecked(false);
                    textViewRadioButton2.setTextColor(getResources().getColor(R.color.default_hint_color));

                    textViewRadioButton1.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                    approvedFromApp = 1;
                    //radioButton1.setButtonTintMode();
                }
            }
        });

        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    radioButton1.setChecked(false);
                    textViewRadioButton1.setTextColor(getResources().getColor(R.color.default_hint_color));
                    textViewRadioButton2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    approvedFromApp = 2;

                }
            }
        });
        /*mWorkTypesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });*/

       /* if (jobsSelectedList.size()>0)
        {
            workTypeAdapater = new WorkTypeAdapater(this, jobsSelectedList);
            mWorkTypesListView.setAdapter(workTypeAdapater);
            workTypeAdapater.notifyDataSetChanged();
        }*/
       /* final ListView mWorkTypesListView = findViewById(R.id.work_types_list_view);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.global_url) + "/job_types/";
        JsonArrayRequest localJReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                final ArrayList<WorkType> workTypes = WorkType.getWorksTypesFromJsonArray(response);
                final List<String> spinnerArray = new ArrayList<String>();
                spinnerArray.add("Select Work Type");
                for (int i = 0; i < workTypes.size(); i++) {
                    spinnerArray.add(workTypes.get(i).getTitle());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CustomerRequestForm.this, android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                final Spinner sItems = findViewById(R.id.work_type_spinner);
                sItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        if (position != 0) {
                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            final WorkType workType = workTypes.get(position - 1);
                            if (!workTypesList.contains(workType) && !workType.getTitle().equals("Select Work Type")) {
                                workTypesList.add(workType);
                                final WorkTypeAdapater workTypeAdapater = new WorkTypeAdapater(CustomerRequestForm.this, workTypesList);
                                mWorkTypesListView.setAdapter(workTypeAdapater);
                                workTypeAdapater.notifyDataSetChanged();

                                mWorkTypesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                                        alertDialog.setTitle("Warning");
                                        final WorkType temp = (WorkType) mWorkTypesListView.getItemAtPosition(i);
                                        alertDialog.setMessage("Do you want to delete" + temp.getTitle() + "from the list of job types?");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        workTypesList.remove(temp);
                                                        final WorkTypeAdapater workTypeAdapater = new WorkTypeAdapater(CustomerRequestForm.this, workTypesList);
                                                        mWorkTypesListView.setAdapter(workTypeAdapater);
                                                        workTypeAdapater.notifyDataSetChanged();
                                                    }
                                                });
                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "No",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                });
                            }
                            sItems.setSelection(0);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // Do nothing
                    }
                });
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                        alertDialog.setTitle("Failure");
                        alertDialog.setMessage("We are sorry, Something went wrong, please check your connection and try again.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                    }
                                });
                        alertDialog.show();
                    }
                }) {//here before semicolon ; and use { }.
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }
        };

        requestQueue.add(localJReq);


        *//*submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    attemptSubmit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/


    }

    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(CustomerRequestForm.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }

    private void getCustomerInfoByEmail(String s) {
        NetworkService
                .getInstance()
                .getCustomerInfoByEmail(s)
                .enqueue(new Callback<List<CustomerRequest>>() {
                    @Override
                    public void onResponse(Call<List<CustomerRequest>> call, Response<List<CustomerRequest>> response) {
                        if (response.code() == 200) {
                            processCustomerInfo(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CustomerRequest>> call, Throwable t) {

                    }
                });
    }

    private void getCustomerInfoByPhone(String s) {
        NetworkService
                .getInstance()
                .getCustomerInfoByPhone(s)
                .enqueue(new Callback<List<CustomerRequest>>() {
                    @Override
                    public void onResponse(Call<List<CustomerRequest>> call, Response<List<CustomerRequest>> response) {
                        if (response.code() == 200) {
                            processCustomerInfo(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CustomerRequest>> call, Throwable t) {

                    }
                });
    }

    private void getCustomerInfoByName(String s) {
        NetworkService
                .getInstance()
                .getCustomerInfoByName(s)
                .enqueue(new Callback<List<CustomerRequest>>() {
                    @Override
                    public void onResponse(Call<List<CustomerRequest>> call, Response<List<CustomerRequest>> response) {
                        if (response.code() == 200) {
                            processCustomerInfo(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CustomerRequest>> call, Throwable t) {

                    }
                });
    }

    private void processCustomerInfo(List<CustomerRequest> body) {
        if (body.size() != 0) {
            if (body.get(0).getName() != null) {
                mNameEditText.setText(body.get(0).getName());
            }
            if (body.get(0).getEmail() != null) {
                mEmailEditText.setText(body.get(0).getEmail());
            }

            if (body.get(0).getPhone() != null) {
                mPhoneNumberEditText.setText(body.get(0).getPhone());
            }

            if (body.get(0).getAddress() != null) {
                mAddressEditText.setText(body.get(0).getAddress());
            }
        }
    }

    private void getJobs() {
        NetworkService
                .getInstance()
                .getJobTypes()
                .enqueue(new Callback<List<Job>>() {
                    @Override
                    public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                        processJobs(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Job>> call, Throwable t) {

                    }
                });


    }

    private void processJobs(List<Job> jobList) {
        workTypesList = jobList;
        spinnerArray.add("Select Work Type");
        //spinnerArray.add("Select Work Type");
        for (int i = 0; i < jobList.size(); i++) {
            spinnerArray.add(jobList.get(i).getName());
        }


        mJobsSpinner.setEnabled(true);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_background, R.id.textViewJob, spinnerArray);
        mJobsSpinner.setAdapter(arrayAdapter);

        mJobsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    if (!spinnerArray.get(position).equals("Select Work Type")) {
                        jobsSelectedList.add(spinnerArray.get(position));

                        workTypeAdapater = new WorkTypeAdapater(CustomerRequestForm.this, jobsSelectedList, CustomerRequestForm.this, null);
                        mWorkTypesListView.setAdapter(workTypeAdapater);

                        workTypeAdapater.notifyDataSetChanged();
                        setSpinnerUi();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCenters() {
        NetworkService
                .getInstance()
                .getCenters(header)
                .enqueue(new Callback<List<Center>>() {
                    @Override
                    public void onResponse(Call<List<Center>> call, Response<List<Center>> response) {
                        if (response.code() >= 200 && response.code() < 300) {

                            processCenters(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Center>> call, Throwable t) {

                    }
                });

    }

    private void processCenters(final List<Center> centerList) {

        spinnerCentersArray.add("Select Center");
        for (int i = 0; i < centerList.size(); i++) {
            spinnerCentersArray.add(centerList.get(i).getName());
        }
        spinnerCenter.setEnabled(true);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_background, R.id.textViewJob, spinnerCentersArray);
        spinnerCenter.setAdapter(arrayAdapter);

        spinnerCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    if (!spinnerCentersArray.get(position).equals("Select Center")) {
                        //jobsSelectedList.add(spinnerArray.get(position));

                        centerId = centerList.get(position - 1).getId();

                        mEmailEditText.setText(centerList.get(position - 1).getManager().getUser().getEmail());
                        mNameEditText.setText(centerList.get(position - 1).getManager().getUser().getName());
                        mPhoneNumberEditText.setText(centerList.get(position - 1).getManager().getPhone());
                        // mEmailEditText.setFocusableInTouchMode(true);


                        //mEmailEditText.clearFocus();
                        mEmailEditText.setEnabled(false);

                        mNameEditText.setEnabled(false);
                        mPhoneNumberEditText.setEnabled(false);

                        /*workTypeAdapater = new WorkTypeAdapater(CustomerRequestForm.this, jobsSelectedList, CustomerRequestForm.this, null);
                        mWorkTypesListView.setAdapter(workTypeAdapater);

                        workTypeAdapater.notifyDataSetChanged();*/
                        setSpinnerUi();
                    }

                } else {
                    mEmailEditText.setText("");
                    mNameEditText.setText("");
                    mPhoneNumberEditText.setText("");
                    centerId = 0;

                    mEmailEditText.setEnabled(false);
                    mNameEditText.setEnabled(false);
                    mPhoneNumberEditText.setEnabled(false);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public WorkTypeAdapater getWorkTypeAdapater() {
        return workTypeAdapater;
    }



    /*void makeRequest() throws JSONException {
        String url = getString(R.string.global_url) + "/request/";

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                // parse success output
                //showProgress(false);
                AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                alertDialog.setTitle("Success!");
                alertDialog.setMessage("You have successfully submitted your request!");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                alertDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //showProgress(false);
                AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                alertDialog.setTitle("Failure!");
                alertDialog.setMessage("We are sorry, something went wrong, please try again later!");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                alertDialog.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
               *//* params.put("notes", mNotesEditText.getText().toString());
                params.put("email", mEmailEditText.getText().toString());
                params.put("phone", mPhoneNumberEditText.getText().toString());*//*
                String jobTypes = "";
                for (int i = 0; i < workTypesList.size(); i++) {
                    jobTypes += workTypesList.get(i).getId() + ",";
                }
                params.put("types", jobTypes);
                return params;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }*/


    private void attemptSubmit() {

        // Reset errors.
        /*mEmailEditText.setError(null);
        mPhoneNumberEditText.setError(null);
        mNotesEditText.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailEditText.getText().toString();
        String phoneNumber = mPhoneNumberEditText.getText().toString();
        String notes = mNotesEditText.getText().toString();*/

        boolean cancel = false;
        boolean typesFlag = false;
        View focusView = null;

       /* // Check for a valid email address.
        if (mEmailEditText.getText().toString().trim().equals("")) {
            //mEmailEditText.setError(getString(R.string.error_field_required));
            focusView = mEmailEditText;
            cancel = true;
        }
        if (mNameEditText.getText().toString().trim().equals("")) {
            //mNameEditText.setError(getString(R.string.error_field_required));
            focusView = mNameEditText;
            cancel = true;
        }*/
        if (mAddressEditText.getText().toString().trim().equals("")) {
            mAddressEditText.setError(getString(R.string.error_field_required));
            focusView = mAddressEditText;
            cancel = true;
        }
        /*if (mPhoneNumberEditText.getText().toString().trim().equals("")) {
            //mPhoneNumberEditText.setError(getString(R.string.error_field_required));
            focusView = mPhoneNumberEditText;
            cancel = true;
        }*/

        /*if (mNotesEditText.getText().toString().trim().equals("")) {
            mNotesEditText.setError(getString(R.string.error_field_required));
            focusView = mNotesEditText;
            cancel = true;
        }*/
        if (jobsSelectedList.size() == 0) {
            typesFlag = true;

        }


        if (cancel || typesFlag || (centerId == 0)) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (cancel) {
                focusView.requestFocus();
            }
            if (typesFlag) {
                AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                alertDialog.setTitle("Missing Work Type");
                alertDialog.setMessage("Please select one type of Work Type by pressing on the Select Work Type button");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertDialog.show();
            }
            if (centerId == 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                alertDialog.setTitle("Missing Center");
                alertDialog.setMessage("Please select a valid center and try again.");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertDialog.show();

            }
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);

            if (userGroup == 1) {

                if (approvedFromApp == 1) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                    alertDialog.setTitle("Attention!");
                    alertDialog.setMessage("Are you sure you want to create and approve a Job Request? It will indicates EMS to generate a Work Order right away for you ?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    makeRequest();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else if (approvedFromApp == 2) {

                    final AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                    alertDialog.setTitle("Attention!");
                    alertDialog.setMessage("Are you sure you want to create a Job Request and request a proposal? EMS will receive your proposal request and contact you soon");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    makeRequest();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }


            } else {
                makeRequest();
            }
        }
    }

   /* private void sendData(CustomerRequest customerRequest){


        NetworkService
                .getInstance()
                .customerFormSubmit(customerRequest)
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {

                        Log.d("jobTypes", "Successful");
                        AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                        alertDialog.setTitle("Success!");
                        alertDialog.setMessage("You have successfully submitted your request!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent goToNextActivity = new Intent(getApplicationContext(), ThankYouActivity.class);
                                        goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(goToNextActivity);
                                        finish();
                                    }
                                });
                        alertDialog.show();

                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {

                    }
                });
    }*/

    private void makeRequest() {

        String jobTypes = new String();
        for (int i = 0; i < jobsSelectedList.size(); i++) {
            for (int j = 0; j < workTypesList.size(); j++) {
                if (workTypesList.get(j).getName().equals(jobsSelectedList.get(i))) {
                    jobTypes += workTypesList.get(j).getId() + ",";
                }
            }


        }

        Log.d("jobTypes", jobTypes);


        if (compressedFileOne != null) {
            RequestBody imageFileBody =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileOne);
            photoPart1 = MultipartBody
                    .Part
                    .createFormData("photo1", compressedFileOne.getName(), imageFileBody);

        }
        if (compressedFileTwo != null) {
            RequestBody imageFileBody =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileTwo);
            photoPart2 = MultipartBody
                    .Part
                    .createFormData("photo2", compressedFileTwo.getName(), imageFileBody);


        }

        if (compressedFileThree != null) {
            RequestBody imageFileBody =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileThree);
            photoPart3 = MultipartBody
                    .Part
                    .createFormData("photo3", compressedFileThree.getName(), imageFileBody);

        }
        if (compressedFileFour != null) {
            RequestBody imageFileBody =
                    RequestBody.create(MediaType.parse("image/*"), compressedFileFour);
            photoPart4 = MultipartBody
                    .Part
                    .createFormData("photo4", compressedFileFour.getName(), imageFileBody);


        }

        customerRequest = new CustomerRequest(
                mNameEditText.getText().toString().trim()
                , mAddressEditText.getText().toString().trim()
                , mEmailEditText.getText().toString().trim()
                , mPhoneNumberEditText.getText().toString().trim()
                , mNotesEditText.getText().toString().trim(), jobTypes, compressedFileOne, compressedFileTwo);


        Log.d("test", "makeRequest: " + mNameEditText.getText().toString().trim());

        //if (!authenticatedUser) {

        double editTextNotToExceedInteger = 0;
        if (!editTextNotToExceed.getText().toString().trim().equals("")) {
            editTextNotToExceedInteger = Double.parseDouble(editTextNotToExceed.getText().toString());
        }
        Log.d(TAG, "makeRequest: " + Double.toString(editTextNotToExceedInteger));
        NetworkService
                .getInstance()
                .customerFormSubmitnew(
                        header,
                        centerId,
                        mNameEditText.getText().toString().trim(),
                        mEmailEditText.getText().toString().trim(),
                        mPhoneNumberEditText.getText().toString().trim(),
                        mAddressEditText.getText().toString().trim(),
                        mNotesEditText.getText().toString().trim(),
                        jobTypes,
                        editTextNotToExceedInteger,
                        approvedFromApp, photoPart1, photoPart2, photoPart3, photoPart4)
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        //sendData(customerRequest);

                        Log.d("jobTypes", "Successful");
                        AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                        alertDialog.setTitle("Success!");
                        alertDialog.setMessage("You have successfully submitted your request!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent goToNextActivity = new Intent(getApplicationContext(), DashboardActivity.class);
                                        goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(goToNextActivity);
                                        finish();
                                    }
                                });
                        alertDialog.show();

                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Log.d("jobTypes", "Fail");
                        AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                        alertDialog.setTitle("Failure!");
                        alertDialog.setMessage("We are sorry, something went wrong, please try again later!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        alertDialog.show();
                    }
                });
        /*} else {

            NetworkService
                    .getInstance()
                    .customerFormSubmit(
                            header,
                            mNameEditText.getText().toString().trim(),
                            mAddressEditText.getText().toString().trim(),
                            mEmailEditText.getText().toString().trim(),
                            mPhoneNumberEditText.getText().toString().trim(),
                            mNotesEditText.getText().toString().trim(),
                            jobTypes, photoPart1, photoPart2, photoPart3, photoPart4)
                    .enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {
                            //sendData(customerRequest);

                            Log.d("jobTypes", "Successful");
                            AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                            alertDialog.setTitle("Success!");
                            alertDialog.setMessage("You have successfully submitted your request!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent goToNextActivity = new Intent(getApplicationContext(), ThankYouActivity.class);
                                            goToNextActivity.putExtra("AuthenticatedUser", true);
                                            goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(goToNextActivity);
                                            finish();
                                        }
                                    });
                            alertDialog.show();

                        }

                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            Log.d("jobTypes", "Fail");
                            AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                            alertDialog.setTitle("Failure!");
                            alertDialog.setMessage("We are sorry, something went wrong, please try again later!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
                            alertDialog.show();
                        }
                    });

        }*/
    }


    @OnClick({R.id.imageViewPhoto1,
            R.id.imageViewPhoto2,
            R.id.imageViewPhoto3,
            R.id.imageViewPhoto4
    })
    public void click(ImageView imageView) {
        Log.d("id", "doSomething: clicked ID" + Integer.toString(imageView.getId()));
        Log.d("id", "doSomething: firstitem ID" + Integer.toString(R.id.imageViewBeforeOne));

        switch (imageView.getId()) {
            case R.id.imageViewPhoto1:
                selectedImageView = imageViewPhoto1;

                showPickImageDialog();


                break;
            case R.id.imageViewPhoto2:
                selectedImageView = imageViewPhoto2;
                showPickImageDialog();

                break;
            case R.id.imageViewPhoto3:
                selectedImageView = imageViewPhoto3;

                showPickImageDialog();


                break;
            case R.id.imageViewPhoto4:
                selectedImageView = imageViewPhoto4;
                showPickImageDialog();

                break;
        }
    }

    private void showPickImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(CustomerRequestForm.this);
        builderSingle.setTitle("Select One Option");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                CustomerRequestForm.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Gallery");
        arrayAdapter.add("Camera");
        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            /*Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto , 1);//one can be replaced with any action code*/
                            /*Intent pickPhoto = new Intent();
                            pickPhoto.setType("image*//*");
                            pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(pickPhoto,"Select a picture"), 1);*/
                            //Picking image from gallery
                            if (ContextCompat
                                    .checkSelfPermission(getApplicationContext(),
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat
                                        .requestPermissions(CustomerRequestForm.this
                                                , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                                , MY_PERMISSIONS_GALLERY);
                            } else {


                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select a Picture"), 1);
                            }

                        }
                        if (which == 1) {
                            //Getting Image from camera
                            /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, 0);//zero can be replaced with any action code*/

                            if (ContextCompat
                                    .checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat
                                        .requestPermissions(CustomerRequestForm.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSIONS_CAMERA);
                            } else {

                                cameraImage = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".png";
                                File folder = new File(dir);
                                cameraFile = new File(cameraImage);
                                try {
                                    folder.mkdirs();
                                    cameraFile.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // Uri outputFileUri = Uri.fromFile(cameraFile);
                                Uri outputFileUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", cameraFile);
                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                startActivityForResult(cameraIntent, 0);
                            }
                        }
                    }
                });
        builderSingle.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode != 1) {

            Log.i("Demo Pic", Long.toString(cameraFile.getTotalSpace()));
            try {
                cameraCompressedFile = new Compressor(getApplicationContext())
                        .setQuality(50)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG).compressToFile(cameraFile);
                Log.i("Demo Pic", Long.toString(cameraCompressedFile.length()));
            } catch (IOException e) {
                e.printStackTrace();
            }


            switch (selectedImageView.getId()) {

                ////////// Camera Images////////////
                case R.id.imageViewPhoto1:
                    imageViewPhoto1Logo.setVisibility(View.GONE);

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewPhoto1);
                    compressedFileOne = cameraCompressedFile;
                    break;
                case R.id.imageViewPhoto2:
                    imageViewPhoto2Logo.setVisibility(View.GONE);
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewPhoto2);
                    compressedFileTwo = cameraCompressedFile;
                    break;
                case R.id.imageViewPhoto3:
                    imageViewPhoto3Logo.setVisibility(View.GONE);

                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewPhoto3);
                    compressedFileThree = cameraCompressedFile;
                    break;
                case R.id.imageViewPhoto4:
                    imageViewPhoto4Logo.setVisibility(View.GONE);
                    Picasso.with(getApplicationContext())
                            .load(cameraCompressedFile)
                            .into(imageViewPhoto4);
                    compressedFileFour = cameraCompressedFile;
                    break;
            }


        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            final InputStream imageStream;

            Uri uriPhoto = data.getData();
            String imageFilePath = FileUtils.getPath(CustomerRequestForm.this, uriPhoto);

            galleryFile = new File(imageFilePath);
            Log.d("OrderDetail", "onActivityResult: " + String.valueOf(galleryFile.length()));

            try {
                galleryCompressedFile = new Compressor(getApplicationContext())
                        .setQuality(50)
                        .setCompressFormat(Bitmap.CompressFormat.JPEG).compressToFile(galleryFile);
                Log.i("Demo Pic", Long.toString(galleryCompressedFile.length()));
            } catch (Exception e) {
            }
            //compressedBitmap = BitmapFactory.decodeFile(compressedFile.getAbsolutePath());

            switch (selectedImageView.getId()) {

                ////////// Gallery Images//////////
                case R.id.imageViewPhoto1:

                    imageViewPhoto1Logo.setVisibility(View.GONE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewPhoto1);
                    compressedFileOne = galleryCompressedFile;
                    break;
                case R.id.imageViewPhoto2:

                    imageViewPhoto2Logo.setVisibility(View.GONE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewPhoto2);
                    compressedFileTwo = galleryCompressedFile;
                    break;
                case R.id.imageViewPhoto3:

                    imageViewPhoto3Logo.setVisibility(View.GONE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewPhoto3);
                    compressedFileThree = galleryCompressedFile;
                    break;
                case R.id.imageViewPhoto4:

                    imageViewPhoto4Logo.setVisibility(View.GONE);
                    Picasso.with(getApplicationContext())
                            .load(galleryCompressedFile)
                            .into(imageViewPhoto4);
                    compressedFileFour = galleryCompressedFile;
                    break;
            }

            selectedImageView = null;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                //Camera Permission result
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    cameraImage = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".png";
                    File folder = new File(dir);
                    cameraFile = new File(cameraImage);
                    try {
                        folder.mkdirs();
                        cameraFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Uri outputFileUri = Uri.fromFile(cameraFile);
                    Uri outputFileUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", cameraFile);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(cameraIntent, 0);


                } else {

                    final AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("We need your permission to upload images.Do you want give this app the permissions?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    alertDialog.dismiss();
                                    if (ContextCompat
                                            .checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat
                                                .requestPermissions(CustomerRequestForm.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_CAMERA);
                                    }
                                    //
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }
                return;
            }

            case 2: {

                //Gallery Permission result
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select a Picture"), 1);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    final AlertDialog alertDialog = new AlertDialog.Builder(CustomerRequestForm.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("We need your permission to upload images.Do you want give this app the permissions?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    alertDialog.dismiss();
                                    if (ContextCompat
                                            .checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat
                                                .requestPermissions(CustomerRequestForm.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_GALLERY);
                                    }
                                    //
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void setSpinnerUi() {
        if (jobsSelectedList.size() == 0) {
            mWorkTypesListView.setVisibility(View.GONE);
            mJobsSpinner.setBackground(getResources().getDrawable(R.drawable.edittext_background_normal));
            imageViewArrow.setColorFilter(getResources().getColor(R.color.login_page_background));
            imageViewTools.setColorFilter(getResources().getColor(R.color.login_page_background));

        } else {
            mWorkTypesListView.setVisibility(View.VISIBLE);
            //mJobsSpinner.setBackground(getResources().getDrawable(R.drawable.edittext_background_dark));
            imageViewArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
            //imageViewTools.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public ListView getListViewSelectedJobs() {
        return mWorkTypesListView;
    }

}



