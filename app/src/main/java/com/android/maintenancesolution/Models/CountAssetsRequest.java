package com.android.maintenancesolution.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 2/18/18.
 */

public class CountAssetsRequest {

    @SerializedName("asset_ids")
    @Expose
    private String asset_ids;

    @SerializedName("area")
    @Expose
    private String area;

    public CountAssetsRequest(String asset_ids, String area) {
        this.asset_ids = asset_ids;
        this.area = area;
    }

    public String getAsset_ids() {
        return asset_ids;
    }

    public void setAsset_ids(String asset_ids) {
        this.asset_ids = asset_ids;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
