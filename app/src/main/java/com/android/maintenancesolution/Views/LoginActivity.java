package com.android.maintenancesolution.Views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.maintenancesolution.Models.Token;
import com.android.maintenancesolution.Network.NetworkService;
import com.android.maintenancesolution.R;
import com.android.maintenancesolution.Utils.GeneralUtils;
import com.android.maintenancesolution.Utils.PreferenceUtils;
import com.android.maintenancesolution.Views.ListActivity.ListActivity;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    PreferenceUtils preferenceUtils;
    //private UserLoginTask mAuthTask = null;
    // UI references.
    private String TAG = "LoginActivity";
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferenceUtils = PreferenceUtils.getInstance(getApplicationContext());

        // Set up the login form.
        mEmailView = findViewById(R.id.editTextUserName);
        mPasswordView = findViewById(R.id.editTextPassword);
        mEmailSignInButton = findViewById(R.id.buttonLogin);


        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin();
                } catch (JSONException e) {
                    Log.d(TAG, e.toString());

                }
            }
        });


    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() throws JSONException {
        /*if (mAuthTask != null) {
            return;
        }
*/
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;
        if (!GeneralUtils.isNetworkAvailable(getApplication())) {
            alertDialog = new GeneralUtils().showValidationPopup(LoginActivity.this, "Network not connected.Please try later");
            alertDialog.show();
        } else {
            // Check for a valid email address.
            if (email.isEmpty()) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            }
            if (password.isEmpty()) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {

                NetworkService
                        .getInstance()
                        .authNetwork(email, password)
                        .enqueue(new Callback<Token>() {
                            @Override
                            public void onResponse(Call<Token> call, Response<Token> response) {

                                processAuthNetwork(response);
                            }

                            @Override
                            public void onFailure(Call<Token> call, Throwable t) {
                                Log.d(TAG, "authNetworkFailed");

                            }
                        });
            }
        }
    }

    private void processAuthNetwork(Response<Token> response) {
        if (response.code() >= 200 && response.code() < 299) {
            preferenceUtils.saveAuthToken(response.body().getToken());
            Intent goToNextActivity = new Intent(getApplicationContext(), ListActivity.class);
            goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToNextActivity);
        } else if (response.body().getNonFieldErrors() != null) {
            Toast.makeText(getApplication(), "Username or Password Incorrect ", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    /*@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {non_field_errors
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    /*private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                System.out.println("CONGRADULATIONS");
                Intent goToNextActivity = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(goToNextActivity);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }*/
}

