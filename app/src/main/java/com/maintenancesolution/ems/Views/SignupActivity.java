package com.maintenancesolution.ems.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.maintenancesolution.R;
import com.maintenancesolution.ems.Models.SignupRequest;
import com.maintenancesolution.ems.Models.SignupResponse;
import com.maintenancesolution.ems.Network.NetworkService;
import com.maintenancesolution.ems.Utils.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.editTextUserName)
    EditText editTextUserName;
    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.buttonSignup)
    Button buttonSignup;
    @BindView(R.id.textViewPhoneNumber)
    EditText textViewPhoneNumber;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.textViewCompanyName)
    EditText textViewCompanyName;
    @BindView(R.id.editTextDescription)
    EditText editTextDescription;
    @BindView(R.id.imageViewPhone)
    ImageView imageViewPhone;
    @BindView(R.id.imageViewPassword)
    ImageView imageViewPassword;
    @BindView(R.id.imageViewDescription)
    ImageView imageViewDescription;
    @BindView(R.id.textViewLogin)
    TextView textViewLogin;
    @BindView(R.id.imageViewEmail)
    ImageView imageViewEmail;

    @BindView(R.id.imageViewUsername)
    ImageView imageViewUsername;
    private GeneralUtils generalUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        textViewPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        generalUtils = new GeneralUtils(SignupActivity.this);

        generalUtils.setEditTextUI(editTextEmail, R.drawable.gray_email, R.drawable.blue_email, imageViewEmail);
        generalUtils.setEditTextUI(textViewPhoneNumber, R.drawable.gray_phone, R.drawable.blue_phone, imageViewPhone);
        generalUtils.setEditTextUI(editTextDescription, R.drawable.gray_request, R.drawable.blue_request, imageViewDescription);
        generalUtils.setEditTextUI(editTextPassword, R.drawable.gray_password, R.drawable.blue_password, imageViewPassword);


    }

    @OnClick(R.id.buttonSignup)
    public void Signup() {
        attemptSubmit();
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

        // Check for a valid email address.
        if (editTextUserName.getText().toString().trim().equals("")) {
            editTextUserName.setError(getString(R.string.error_field_required));
            focusView = editTextUserName;
            cancel = true;
        }
        if (editTextEmail.getText().toString().trim().equals("")) {
            editTextEmail.setError(getString(R.string.error_field_required));
            focusView = editTextEmail;
            cancel = true;
        }
        if (textViewPhoneNumber.getText().toString().trim().equals("")) {
            textViewPhoneNumber.setError(getString(R.string.error_field_required));
            focusView = textViewPhoneNumber;
            cancel = true;
        }
        if (textViewCompanyName.getText().toString().trim().equals("")) {
            textViewCompanyName.setError(getString(R.string.error_field_required));
            focusView = textViewCompanyName;
            cancel = true;
        }

        if (editTextPassword.getText().toString().trim().equals("")) {
            editTextPassword.setError(getString(R.string.error_field_required));
            focusView = editTextPassword;
            cancel = true;
        }


        if (!cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
           /* if (cancel) {
                focusView.requestFocus();
            }*/
            /*if (typesFlag) {
                AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                alertDialog.setTitle("Missing Work Type");
                alertDialog.setMessage("Please select one type of Work Type by pressing on the Select Work Type button");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertDialog.show();
            }
        } else {*/
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            makeRequest();
        }

    }

    private void makeRequest() {

        SignupRequest signupRequest = new SignupRequest(editTextUserName.getText().toString().trim(),
                editTextEmail.getText().toString().trim(),
                editTextPassword.getText().toString().trim(),
                textViewPhoneNumber.getText().toString().trim(),
                textViewCompanyName.getText().toString(),
                editTextDescription.getText().toString());
        NetworkService
                .getInstance()
                .newUserSignup(signupRequest)
                .enqueue(new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                        if (response.code() >= 200 && response.code() < 300) {

                            AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                            alertDialog.setTitle("Success!");
                            alertDialog.setMessage("You have successfully signed up. We will get in touch with you soon.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent goToNextActivity = new Intent(getApplicationContext(), LoginActivity.class);
                                            goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(goToNextActivity);
                                            finish();
                                        }
                                    });
                            alertDialog.show();
                        } else {

                            AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
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
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {

                        AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
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
