package com.maintenancesolution.ems.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.maintenancesolution.R;

public class ThankYouActivity extends AppCompatActivity {

    private Button mContinueButton;
    private boolean authenticatedUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        if (getIntent().getBooleanExtra("AuthenticatedUser", false)) {
            authenticatedUser = true;
        }

        mContinueButton = findViewById(R.id.buttonContinue);

        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!authenticatedUser) {
                    Intent goToNextActivity = new Intent(getApplicationContext(), UserSelectorActivity.class);
                    goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goToNextActivity);
                } else {
                    Intent goToNextActivity = new Intent(getApplicationContext(), DashboardActivity.class);
                    goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goToNextActivity);

                }
            }
        });
    }
}
