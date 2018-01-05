package com.android.maintenancesolution;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.maintenancesolution.Models.Job;
import com.android.maintenancesolution.Models.Order;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.Views.SelectImagesActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


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
    private Button buttonAddImages;
    private Button buttonSubmitRequest;
    private WorkTypeAdapater workTypeAdapater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_detail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        order = (Order) getIntent().getSerializableExtra("Order");


        spinnerWorkType = findViewById(R.id.spinnerWorkType);
        listViewSelectedJobs = findViewById(R.id.listViewSelectedJobs);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAddImages = findViewById(R.id.buttonAddImages);
        buttonSubmitRequest = findViewById(R.id.buttonSubmitRequest);

        spinnerWorkType.setEnabled(false);
        listViewSelectedJobs.setVisibility(View.GONE);
        getJobs();

        buttonAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetail.this, SelectImagesActivity.class);
                startActivity(intent);

            }
        });

        buttonSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OrderDetail.this, CustomerFeedbackActivity.class);
                startActivity(intent);

            }
        });


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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerWorkType.setAdapter(arrayAdapter);

        spinnerWorkType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    if (!spinnerArray.get(position).equals("Select Work Type")) {
                        jobsSelectedList.add(spinnerArray.get(position));

                        workTypeAdapater = new WorkTypeAdapater(OrderDetail.this, jobsSelectedList, null, OrderDetail.this);
                        listViewSelectedJobs.setAdapter(workTypeAdapater);

                        workTypeAdapater.notifyDataSetChanged();


                        if (jobsSelectedList.size() == 0) {
                            listViewSelectedJobs.setVisibility(View.GONE);
                        } else {
                            listViewSelectedJobs.setVisibility(View.VISIBLE);
                        }
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
    }

    public WorkTypeAdapater getWorkTypeAdapater() {
        return workTypeAdapater;
    }


    public ListView getListViewSelectedJobs() {
        return listViewSelectedJobs;
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
        /*if (progress) {
            super.onBackPressed();
        }
        */
    }
}
