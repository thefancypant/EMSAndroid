package com.android.maintenancesolution.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 2/17/18.
 */

public class UpdateAssetLocationRequest {

    @SerializedName("area")
    @Expose
    private String name;

    public UpdateAssetLocationRequest(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
