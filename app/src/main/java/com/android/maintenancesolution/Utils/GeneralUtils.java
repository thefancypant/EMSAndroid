package com.android.maintenancesolution.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    public void setEditTextUI(final EditText editText, final int greyIconId, final int darkIconId, final ImageView imageview) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    editText.setBackground(context.getResources().getDrawable(R.drawable.edittext_background_dark));
                    // Picasso.with(context).load(darkIconId).into(imageview);
                    editText.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    /*Drawable drawable = context.getResources().getDrawable(darkIconId );//Your drawable image
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, Color.GREEN); // Set whatever color you want
                    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_OVER);
                    editText.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);*/
                    imageview.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else {
                    if (editText.getText().toString().equals("")) {
                        editText.setBackground(context.getResources().getDrawable(R.drawable.edittext_background_normal));
                        //Picasso.with(context).load(greyIconId).into(imageview);
                        editText.setHintTextColor(context.getResources().getColor(R.color.login_page_background));
                        imageview.setColorFilter(ContextCompat.getColor(context, R.color.login_page_background), android.graphics.PorterDuff.Mode.MULTIPLY);
                    } else {

                        editText.setBackground(context.getResources().getDrawable(R.drawable.edittext_background_dark));
                        //Picasso.with(context).load(darkIconId).into(imageview);
                        editText.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        imageview.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);


                    }
                }
            }
        });

    }


    public void setEditTextUI(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    editText.setBackground(context.getResources().getDrawable(R.drawable.edittext_background_dark));
                    // Picasso.with(context).load(darkIconId).into(imageview);
                    editText.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    /*Drawable drawable = context.getResources().getDrawable(darkIconId );//Your drawable image
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, Color.GREEN); // Set whatever color you want
                    DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_OVER);
                    editText.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);*/
                } else {
                    if (editText.getText().toString().equals("")) {
                        editText.setBackground(context.getResources().getDrawable(R.drawable.edittext_background_normal));
                        //Picasso.with(context).load(greyIconId).into(imageview);
                        editText.setHintTextColor(context.getResources().getColor(R.color.login_page_background));
                    } else {

                        editText.setBackground(context.getResources().getDrawable(R.drawable.edittext_background_dark));
                        //Picasso.with(context).load(darkIconId).into(imageview);
                        editText.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));


                    }
                }
            }
        });

    }
}
