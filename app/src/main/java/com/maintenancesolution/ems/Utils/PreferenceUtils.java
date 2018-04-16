package com.maintenancesolution.ems.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kalyan on 12/12/17.
 */

public class PreferenceUtils {
    static PreferenceUtils prefrenceUtils;
    Context context;
    SharedPreferences sharedPreferences;

    /**
     * Creates the Shared Preference named "oauth_details".
     *
     * @param context is the context of the activity that called PreferenceUtils.
     */
    public PreferenceUtils(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.OAUTH_DETAILS, Context.MODE_PRIVATE);

    }

    /**
     * Returns the instance of the Shared preference.
     *
     * @param context is the context of the activity that called PreferenceUtils.
     */
    public static PreferenceUtils getInstance(Context context) {
        if (prefrenceUtils == null) {
            prefrenceUtils = new PreferenceUtils(context);
        }
        return prefrenceUtils;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveAuthToken(String oauthToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.ACCESS_TOKEN, oauthToken);
        editor.commit();
    }

    public void saveRefreshToken(String refreshToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.REFRESH_TOKEN, refreshToken);
        editor.commit();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(Constants.ACCESS_TOKEN, null);
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(Constants.REFRESH_TOKEN, null);
    }
}
