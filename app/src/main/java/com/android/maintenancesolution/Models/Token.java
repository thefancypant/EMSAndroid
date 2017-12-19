package com.android.maintenancesolution.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 12/13/17.
 */

public class Token {

    @SerializedName("token")
    @Expose
    private String Token;

    @SerializedName("non_field_errors")
    @Expose
    private String nonFieldErrors;

    public Token(String token) {
        Token = token;
    }

    public String getNonFieldErrors() {
        return nonFieldErrors;
    }

    public void setNonFieldErrors(String nonFieldErrors) {
        this.nonFieldErrors = nonFieldErrors;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
