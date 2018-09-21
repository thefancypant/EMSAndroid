package com.maintenancesolution.ems.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.maintenancesolution.R;
import com.maintenancesolution.ems.Models.Token;
import com.maintenancesolution.ems.Network.NetworkService;
import com.maintenancesolution.ems.Utils.GeneralUtils;
import com.maintenancesolution.ems.Utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 4000;
    private PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        preferenceUtils = PreferenceUtils.getInstance(getApplicationContext());

        //setUpAWS();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Create an Intent that will start the Menu-Activity.
                if (!GeneralUtils.isNetworkAvailable(getApplication())) {
                    preferenceUtils.saveAuthToken(null);
                    /*AlertDialog alertDialog = new AlertDialog.Builder(SplashScreenActivity.this).create();
                    alertDialog.setTitle("Internet Connection");
                    alertDialog.setMessage("Something went wrong.Please check your network connection.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    alertDialog.show();*/
                    Intent goToNextActivity = new Intent(getApplicationContext(), LoginActivity.class);
                    goToNextActivity.putExtra("internet", 2);
                    goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goToNextActivity);

                } else {
                    //TODO:Remove next line
                    //preferenceUtils.saveAuthToken(null);

                    checkToken();

                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    private void checkToken() {
                /*if (preferenceUtils.getRefreshToken() == null)
                    {*/
        Token token = new Token(preferenceUtils.getAuthToken());

        NetworkService
                .getInstance()
                .verify(token)
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        processVerify(response);
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Intent goToNextActivity = new Intent(getApplicationContext(), LoginActivity.class);
                        goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(goToNextActivity);
                    }
                });


    }


    private void processVerify(Response<Token> response) {
        if (response.code() >= 200 && response.code() < 300) {
            preferenceUtils.saveAuthToken(response.body().getToken());
            Intent goToNextActivity = new Intent(getApplicationContext(), DashboardActivity.class);
            goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToNextActivity);

        } else {
            preferenceUtils.saveAuthToken(null);

            Intent goToNextActivity = new Intent(getApplicationContext(), LoginActivity.class);
            goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToNextActivity);
        }


    }

    @Override
    public void onBackPressed() {

    }

}
