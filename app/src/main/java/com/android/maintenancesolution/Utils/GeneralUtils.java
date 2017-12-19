package com.android.maintenancesolution.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.maintenancesolution.R;

/**
 * Created by kalyan on 12/18/17.
 */

public class GeneralUtils {

    Context context;
    private Button btnOk;

    public GeneralUtils(Context context) {
        this.context = context;
    }

    public GeneralUtils() {
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    public Dialog showValidationPopup(Context context, String msg) {

        //LocaleUtil.LocaleUtil(context, new PreferenceUtils(context).getLanguage());
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.popup_validation, null);
        final AlertDialog alertD = new AlertDialog.Builder(context).create();
        TextView message = promptView.findViewById(R.id.textViewMessage);
        message.setText(msg);
        btnOk = promptView.findViewById(R.id.buttonOk);
        setBtnOk(btnOk);
        alertD.setView(promptView);
        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertD.setCanceledOnTouchOutside(false);
        alertD.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
            }
        });
        return alertD;

    }

    public Button getBtnOk() {
        return btnOk;
    }

    public void setBtnOk(Button btnOk) {
        this.btnOk = btnOk;
    }

}
