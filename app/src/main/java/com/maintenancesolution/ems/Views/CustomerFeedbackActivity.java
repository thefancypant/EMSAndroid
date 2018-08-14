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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.maintenancesolution.R;
import com.maintenancesolution.ems.Misc.AppHelper;
import com.maintenancesolution.ems.Misc.VolleyMultipartRequest;
import com.maintenancesolution.ems.Misc.VolleySingleton;
import com.maintenancesolution.ems.Models.Order;
import com.maintenancesolution.ems.Network.NetworkContract;
import com.maintenancesolution.ems.Utils.PreferenceUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;


public class CustomerFeedbackActivity extends AppCompatActivity {
    final String dir = Environment.getExternalStoragePublicDirectory(".Consulting") + "/Folder/";
    View mFormView;

    //ProgressBar mProgressBar;
    //ConstraintLayout mConstraintLayout;
    @BindView(R.id.customer_feedback_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.signature_pad)
    SignaturePad signaturePad;
    /*@BindView(R.id.buttonSubmitRequest)
    Button buttonSubmitRequest;*/
    @BindView(R.id.buttonSubmitToSupervisor)
    Button buttonSubmitToSupervisor;
    @BindView(R.id.customer_feedback_constraint_layout)
    ConstraintLayout mConstraintLayout;
    Order order;
    private PreferenceUtils preferenceUtils;
    private String header;
    private MultipartBody.Part icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_feedback);
        ButterKnife.bind(this);
        ratingBar.setRating(3);
        order = getIntent().getParcelableExtra("Order");
        Button mButton = findViewById(R.id.buttonSubmitRequest);

        getPrefUtils();


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog alertDialog = new AlertDialog.Builder(CustomerFeedbackActivity.this).create();
                alertDialog.setTitle("Attention");
                alertDialog.setMessage("Are you sure you want to complete the order?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                final Intent goToNextActivity = new Intent(getApplicationContext(), DashboardActivity.class);
                                goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                /*goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/


                                String url = NetworkContract.BASE_URL + "/gm/works/" + order.getId() + "/complete/";
                                Log.d("FinalUrl", "onClick: " + url);
                                VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.PUT, url, new Response.Listener<NetworkResponse>() {
                                    @Override
                                    public void onResponse(NetworkResponse response) {
                                        String resultResponse = new String(response.data);
                                        // parse success output
                                        showProgress(false);
                                        AlertDialog alertDialog = new AlertDialog.Builder(CustomerFeedbackActivity.this).create();
                                        alertDialog.setTitle("Thank you!");
                                        alertDialog.setMessage("Your Order has been completed");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {


                                                        startActivity(goToNextActivity);
                                                        finish();
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        AlertDialog alertDialog = new AlertDialog.Builder(CustomerFeedbackActivity.this).create();
                                        alertDialog.setTitle("Failure");
                                        alertDialog.setMessage("We are sorry, Something went wrong, please check your connection and try again.");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        showProgress(false);
                                                        //
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();
                                        Date currentTime = Calendar.getInstance().getTime();
                                        Date date = new Date();   // given date
                                        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                                        calendar.setTime(date);   // assigns calendar to given date
                                        int hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
                                        int minutes = calendar.get(Calendar.MINUTE);
                                        params.put("end_time", Integer.toString(hours) + ":" + Integer.toString(minutes));
                                        params.put("evaluation", String.valueOf(Math.round(ratingBar.getRating())));
                                        return params;
                                    }

                                    @Override
                                    protected Map<String, DataPart> getByteData() {
                                        Map<String, DataPart> params = new HashMap<>();
                                        Bitmap bmp = signaturePad.getSignatureBitmap();
                                        if (bmp != null) {
                                            params.put("sign", new DataPart("sign.png", AppHelper.getFileDataFromBitmap(getBaseContext(), bmp), "image/png"));
                                        }
                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Authorization", header);
                                        return params;
                                    }
                                };
                                VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
                                showProgress(true);


                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                               /* final Intent goToNextActivity = new Intent(getApplicationContext(), DashboardActivity.class);
                                goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(goToNextActivity);*/
                                //alertDialog.dismiss();

                            }
                        });
                alertDialog.show();


            }
        });


        buttonSubmitToSupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog alertDialog = new AlertDialog.Builder(CustomerFeedbackActivity.this).create();
                alertDialog.setTitle("Attention");
                alertDialog.setMessage("Are you sure you want to submit the order to your supervisor?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                final Intent goToNextActivity = new Intent(getApplicationContext(), DashboardActivity.class);
                                goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                /*goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/


                                String url = NetworkContract.BASE_URL + "/gm/works/" + order.getId() + "/complete/";
                                Log.d("FinalUrlSuperVisor", "onClick: " + url);
                                VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.PUT, url, new Response.Listener<NetworkResponse>() {
                                    @Override
                                    public void onResponse(NetworkResponse response) {
                                        String resultResponse = new String(response.data);
                                        Log.d("OnComplete", "onResponse: " + response.data.toString());
                                        // parse success output
                                        showProgress(false);
                                        AlertDialog alertDialog = new AlertDialog.Builder(CustomerFeedbackActivity.this).create();
                                        alertDialog.setTitle("Thank you!");
                                        alertDialog.setMessage("Your Order has been completed");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {


                                                        startActivity(goToNextActivity);
                                                        finish();
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        AlertDialog alertDialog = new AlertDialog.Builder(CustomerFeedbackActivity.this).create();
                                        alertDialog.setTitle("Failure");
                                        alertDialog.setMessage("We are sorry, Something went wrong, please check your connection and try again.");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        showProgress(false);
                                                        //
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<>();
                                        Date currentTime = Calendar.getInstance().getTime();
                                        Date date = new Date();   // given date
                                        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                                        calendar.setTime(date);   // assigns calendar to given date
                                        int hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
                                        int minutes = calendar.get(Calendar.MINUTE);
                                        params.put("end_time", Integer.toString(hours) + ":" + Integer.toString(minutes));
                                        params.put("evaluation", String.valueOf(Math.round(ratingBar.getRating())));
                                        params.put("submitted_to_supervisor", "true");
                                        return params;
                                    }

                                    @Override
                                    protected Map<String, DataPart> getByteData() {
                                        Map<String, DataPart> params = new HashMap<>();
                                        Bitmap bmp = signaturePad.getSignatureBitmap();
                                        if (bmp != null) {
                                            params.put("sign", new DataPart("sign.png", AppHelper.getFileDataFromBitmap(getBaseContext(), bmp), "image/png"));
                                        }
                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Authorization", header);
                                        return params;
                                    }
                                };
                                VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
                                showProgress(true);


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
        });

    }


    private void getPrefUtils() {
        preferenceUtils = new PreferenceUtils(CustomerFeedbackActivity.this);
        header = "JWT " + preferenceUtils.getAuthToken();
    }

   /* @OnClick(R.id.buttonSubmitRequest)
    public void submit()
    {
        Bitmap bmp = signaturePad.getSignatureBitmap();
        String evaluation =String.valueOf(Math.round(ratingBar.getRating()));

        Date currentTime = Calendar.getInstance().getTime();
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        int hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        int minutes = calendar.get(Calendar.MINUTE);

        String time =Integer.toString(hours)+":"+Integer.toString(minutes);

        File signFile=new File(dir+"sign.png");
        //signFile.
        *//*try {
            signFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        signFile=AppHelper.getFileDataFromBitmap(getBaseContext(), bmp);*//*

        *//*try {
            if (!signFile.exists()) {
                signFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream();
            fos.write(bmp);
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }*//*
        //bmp.compress(Bitmap.CompressFormat.PNG,50,bmp.ou);

        RequestBody imageFileBody =
                RequestBody.create(MediaType.parse("application/octet-stream"),AppHelper.getFileDataFromBitmap(getBaseContext(), bmp) );
        icon = MultipartBody
                .Part
                .createFormData("sign", "t.png", imageFileBody);

        NetworkService
                .getInstance()
                .postSignNetwork(header,"902",*//*evaluation,time,*//*imageFileBody)
                .enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, retrofit2.Response<GenericResponse> response) {
                        Log.d("SendSign", "onResponse: ");
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        Log.d("SendSign", "onFailure: ");
                    }
                });


    }*/

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mConstraintLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mConstraintLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                }
            });

            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mConstraintLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {

        final Intent goToNextActivity = new Intent(getApplicationContext(), DashboardActivity.class);
        goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goToNextActivity);
        super.onBackPressed();
    }
}
