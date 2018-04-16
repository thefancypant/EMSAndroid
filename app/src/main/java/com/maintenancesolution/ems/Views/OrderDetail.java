package com.maintenancesolution.ems.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.maintenancesolution.R;
import com.maintenancesolution.ems.Misc.WorkTypeAdapater;
import com.maintenancesolution.ems.Models.GenericResponse;
import com.maintenancesolution.ems.Models.Job;
import com.maintenancesolution.ems.Models.Order;
import com.maintenancesolution.ems.Network.NetworkService;
import com.maintenancesolution.ems.Utils.PreferenceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetail extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static int RESULT_LOAD_IMAGE = 1;
    //final ArrayList<WorkType> workTypesList = new ArrayList<WorkType>();
    final String dir = Environment.getExternalStoragePublicDirectory(".Consulting") + "/Folder/";
    Button selectedImageButton;
    ConstraintLayout mConstraintLayout;
    EditText reportField;
    Order order;
    ImageView button1;
    Bitmap bmp1;
    ImageView button2;
    Bitmap bmp2;
    ImageView button3;
    Bitmap bmp3;
    ImageView button4;
    Bitmap bmp4;
    Bitmap compressedBitmap;
    File compressedFile = null;
    File cameraCompressedFile = null;
    String cameraImage;
    ArrayList<String> spinnerArray = new ArrayList<>();
    List<Job> workTypesList = new ArrayList<>();
    ArrayList<String> jobsSelectedList = new ArrayList<>();
    private View mProgressView;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    private File newfile;
    private File cameraFile;
    //final String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
    private boolean progress = true;
    private Spinner spinnerWorkType;
    private ListView listViewSelectedJobs;
    private EditText editTextDescription;
    private ImageView imageViewTools;
    private ImageView imageViewDownArrow;
    private Button buttonAddImages;
    private Button buttonSubmitRequest;
    private WorkTypeAdapater workTypeAdapater;
    private PreferenceUtils preferenceUtils;
    private String header;
    private boolean clickSubmit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_detail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        order = getIntent().getParcelableExtra("Order");


        spinnerWorkType = findViewById(R.id.spinnerWorkType);
        listViewSelectedJobs = findViewById(R.id.listViewSelectedJobs);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAddImages = findViewById(R.id.buttonAddImages);
        buttonSubmitRequest = findViewById(R.id.buttonSubmitRequest);
        imageViewDownArrow = findViewById(R.id.imageViewArrow);
        imageViewTools = findViewById(R.id.imageViewTools);
        imageViewDownArrow.setColorFilter(getResources().getColor(R.color.login_page_background));


        spinnerWorkType.setEnabled(false);
        listViewSelectedJobs.setVisibility(View.GONE);
        getJobs();

        buttonAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPrefUtils();
                attemptSubmit();
               /* Intent intent = new Intent(OrderDetail.this, SelectImagesActivity.class);
                intent.putExtra("Order",order);
                startActivity(intent);*/

            }
        });

        buttonSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSubmit = true;
                getPrefUtils();
                attemptSubmit();

            }
        });


    }

    private void setUI(Order order) {
        if (order.getReport() != null && !order.getReport().equals("")) {
            editTextDescription.setText(order.getReport());
        }
        if (order.getTypes().size() != 0) {

            for (int i = 0; i < order.getTypes().size(); i++) {
                jobsSelectedList.add(order.getTypes().get(i).getName());
            }

        }
        Log.d("setUI", "setUI: " + jobsSelectedList.toString());
        workTypeAdapater = new WorkTypeAdapater(OrderDetail.this, jobsSelectedList, null, OrderDetail.this);
        listViewSelectedJobs.setAdapter(workTypeAdapater);

        workTypeAdapater.notifyDataSetChanged();


        setSpinnerUi();

    }

    private void postTypeReport() {
        String jobTypes = new String();
        for (int i = 0; i < jobsSelectedList.size(); i++) {
            for (int j = 0; j < workTypesList.size(); j++) {
                if (workTypesList.get(j).getName().equals(jobsSelectedList.get(i))) {
                    jobTypes += workTypesList.get(j).getId() + ",";
                }
            }


        }
        Order item = new Order();
        item.setJobtypes(jobTypes);
        item.setReport(editTextDescription.getText().toString().trim());
        NetworkService
                .getInstance()
                .postTypesReport(header, Integer.toString(order.getId()), item/*jobTypes,editTextDescription.getText().toString().trim()*/)
                .enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        processTypeReport(response);
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        showProgress(false);

                        AlertDialog alertDialog = new AlertDialog.Builder(OrderDetail.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Something went wrong.Please try again later.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        alertDialog.show();

                    }
                });


    }

    private void processTypeReport(Response<GenericResponse> response) {
        if (response.body().getMessage() != null && response.body().getMessage().equals("Success")) {
            if (!clickSubmit) {
                showProgress(false);
                Intent intent = new Intent(OrderDetail.this, SelectImagesActivity.class);
                intent.putExtra("Order", order);
                startActivity(intent);
            } else {
                showProgress(false);
                clickSubmit = false;
                Intent intent = new Intent(OrderDetail.this, CustomerFeedbackActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("Order", order);
                startActivity(intent);

            }
        } else {
            showProgress(false);

            AlertDialog alertDialog = new AlertDialog.Builder(OrderDetail.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Something went wrong.Please try again later.");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alertDialog.show();
        }

    }

    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(OrderDetail.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }

    private void getJobs() {
        NetworkService
                .getInstance()
                .getJobTypes()
                .enqueue(new Callback<List<Job>>() {
                    @Override
                    public void onResponse(Call<List<Job>> call, retrofit2.Response<List<Job>> response) {
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


        spinnerWorkType.setEnabled(true);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_background, R.id.textViewJob, spinnerArray);
        spinnerWorkType.setAdapter(arrayAdapter);

        spinnerWorkType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    spinnerWorkType.setSelection(0);
                    if (!spinnerArray.get(position).equals("Select Work Type")) {
                        if (!jobsSelectedList.contains(spinnerArray.get(position))) {
                            jobsSelectedList.add(spinnerArray.get(position));
                        }

                        workTypeAdapater = new WorkTypeAdapater(OrderDetail.this, jobsSelectedList, null, OrderDetail.this);
                        listViewSelectedJobs.setAdapter(workTypeAdapater);

                        workTypeAdapater.notifyDataSetChanged();


                        setSpinnerUi();
                   /* mWorkTypesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            jobsSelectedList.remove(i);
                            workTypeAdapater.notifyDataSetChanged();
                        }
                    });*/

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setUI(order);
        //workTypeAdapater.notifyDataSetChanged();

    }

    public void setSpinnerUi() {
        if (jobsSelectedList.size() == 0) {
            listViewSelectedJobs.setVisibility(View.GONE);
            spinnerWorkType.setBackground(getResources().getDrawable(R.drawable.edittext_background_normal));
            imageViewDownArrow.setColorFilter(getResources().getColor(R.color.login_page_background));
            imageViewTools.setColorFilter(getResources().getColor(R.color.login_page_background));

        } else {
            listViewSelectedJobs.setVisibility(View.VISIBLE);
            spinnerWorkType.setBackground(getResources().getDrawable(R.drawable.edittext_background_dark));
            imageViewDownArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
            imageViewTools.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public WorkTypeAdapater getWorkTypeAdapater() {
        return workTypeAdapater;
    }


    public ListView getListViewSelectedJobs() {
        return listViewSelectedJobs;
    }

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

        if (editTextDescription.getText().toString().trim().equals("")) {
            editTextDescription.setError(getString(R.string.error_field_required));
            focusView = editTextDescription;
            cancel = true;
        }
        if (jobsSelectedList.size() == 0) {
            typesFlag = true;

        }


        if (cancel || typesFlag) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (cancel) {
                focusView.requestFocus();
            }
            if (typesFlag) {
                AlertDialog alertDialog = new AlertDialog.Builder(OrderDetail.this).create();
                alertDialog.setTitle("Missing Work Type");
                alertDialog.setMessage("Please select one type of Work Type by pressing on the Select Work Type button");
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
            showProgress(true);
            postTypeReport();
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        // Setup Progress View
        progress = !show;
        mConstraintLayout = findViewById(R.id.constraint_layout);
        mProgressView = findViewById(R.id.order_detail_progress);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mConstraintLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mConstraintLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mConstraintLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mConstraintLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
/*

    @OnClick(R.id.submit_button)
    public void submit()
    {

        Intent intent = new Intent(OrderDetail.this,CustomerFeedbackActivity.class);
        startActivity(intent);
    }
*/

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        OrderDetail.this.finish();
        /*if (progress) {
            super.onBackPressed();
        }
        */
    }
}
