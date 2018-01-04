package com.android.maintenancesolution.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.maintenancesolution.Models.CustomerRequest;
import com.android.maintenancesolution.Models.Job;
import com.android.maintenancesolution.Models.Token;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.R;
import com.android.maintenancesolution.WorkTypeAdapater;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CustomerRequestForm extends AppCompatActivity {

    List<Job> workTypesList = new ArrayList<>();
    ListView mWorkTypesListView;
    EditText mPhoneNumberEditText;
    EditText mEmailEditText;
    EditText mNotesEditText;
    Spinner mJobsSpinner;
    Button submitButton;
    WorkTypeAdapater workTypeAdapater;

    ArrayList<String> spinnerArray = new ArrayList<>();

    ConstraintLayout mConstraintLayout;
    private ArrayList<String> jobsSelectedList = new ArrayList<>();
    //private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_request_form);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        submitButton = findViewById(R.id.buttonSubmitRequest);

        mEmailEditText = findViewById(R.id.textViewEmail);
        mPhoneNumberEditText = findViewById(R.id.editTextPhoneNumber);
        mJobsSpinner = findViewById(R.id.spinnerWorkType);
        mWorkTypesListView = findViewById(R.id.listViewSelectedJobs);
        mPhoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        mNotesEditText = findViewById(R.id.editTextDescription);
        mNotesEditText.setMaxLines(4);
        mNotesEditText.setHorizontallyScrolling(false);

        mJobsSpinner.setEnabled(false);
        getJobs();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmit();
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


        mJobsSpinner.setEnabled(true);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
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

        // Check for a valid email address.
        if (mEmailEditText.toString().trim().equals("")) {
            mEmailEditText.setError(getString(R.string.error_field_required));
            focusView = mEmailEditText;
            cancel = true;
        }
        if (mPhoneNumberEditText.toString().trim().equals("")) {
            mPhoneNumberEditText.setError(getString(R.string.error_field_required));
            focusView = mPhoneNumberEditText;
            cancel = true;
        }

        if (mNotesEditText.toString().trim().equals("")) {
            mNotesEditText.setError(getString(R.string.error_field_required));
            focusView = mNotesEditText;
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
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            makeRequest();
        }
    }

    private void makeRequest() {

        String jobTypes = "";
        for (int i = 0; i < jobsSelectedList.size(); i++) {
            for (int j = 0; j < workTypesList.size(); j++) {
                if (workTypesList.get(j).getName().equals(jobsSelectedList.get(i))) {
                    jobTypes += workTypesList.get(j).getId() + ",";
                }
            }


        }

        Log.d("jobTypes", jobTypes);
        CustomerRequest customerRequest = new CustomerRequest(mEmailEditText.getText().toString().trim()
                , mPhoneNumberEditText.getText().toString().trim()
                , mNotesEditText.getText().toString().trim(), jobTypes);

        NetworkService
                .getInstance()
                .customerFormSubmit(customerRequest)
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
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
    }


}
