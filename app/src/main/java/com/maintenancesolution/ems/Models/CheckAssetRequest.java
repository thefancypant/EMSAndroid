package com.maintenancesolution.ems.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 2/17/18.
 */

public class CheckAssetRequest {

    @SerializedName("asset")
    @Expose
    private String asset;
    @SerializedName("area")
    @Expose
    private String area;

    public CheckAssetRequest(String asset, String area) {
        this.asset = asset;
        this.area = area;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
