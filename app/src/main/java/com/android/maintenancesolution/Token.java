package com.android.maintenancesolution;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 12/13/17.
 */

public class Token {

    @SerializedName("token")
    @Expose
    private String Token;

    public Token(String token) {
        Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
