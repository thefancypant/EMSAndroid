package com.android.maintenancesolution.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.maintenancesolution.ListActivity;
import com.android.maintenancesolution.Models.Token;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.R;
import com.android.maintenancesolution.UserSelectorActivity;
import com.android.maintenancesolution.Utils.GeneralUtils;
import com.android.maintenancesolution.Utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

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

        preferenceUtils = new PreferenceUtils(this);

        //setUpAWS();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Create an Intent that will start the Menu-Activity.
                if (!GeneralUtils.isNetworkAvailable(getApplication())) {
                    preferenceUtils.saveAuthToken(null);
                    Toast.makeText(getApplication(), "no internet", Toast.LENGTH_LONG).show();
                    Intent goToNextActivity = new Intent(getApplicationContext(), UserSelectorActivity.class);
                    goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goToNextActivity);

                } else {
                    //TODO:Remove next line
                    preferenceUtils.saveAuthToken(null);

                    checkToken();

                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

   /* private void setUpAWS() {
        Context appContext = getApplicationContext();
        AWSConfiguration awsConfig = new AWSConfiguration(appContext);
        IdentityManager identityManager = new IdentityManager(appContext, awsConfig);
        IdentityManager.setDefaultIdentityManager(identityManager);
        //Log.i("pinpoint",identityManager.getCachedUserID());
        user_id = identityManager.getCachedUserID();
        identityManager.doStartupAuth(this, new StartupAuthResultHandler() {
            @Override
            public void onComplete(StartupAuthResult startupAuthResult) {

                Log.i("pinpoint","onComplete");
                // User identity is ready as unauthenticated user or previously signed-in user.
            }
        });

    }*/

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

                    }
                });


    }


    private void processVerify(Response<Token> response) {
        if (response.code() >= 200 && response.code() < 300) {
            preferenceUtils.saveAuthToken(response.body().getToken());
            Timber.e("Token vaild");
            Intent goToNextActivity = new Intent(getApplicationContext(), ListActivity.class);
            goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToNextActivity);

        } else {
            preferenceUtils.saveAuthToken(null);
            Timber.e("Token not vaild");
            Intent goToNextActivity = new Intent(getApplicationContext(), UserSelectorActivity.class);
            goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToNextActivity);
        }


    }

    @Override
    public void onBackPressed() {

    }

}