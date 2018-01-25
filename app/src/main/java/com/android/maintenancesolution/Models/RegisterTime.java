package com.android.maintenancesolution.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 1/24/18.
 */

class RegisterTime {
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("timestamp")
    @Expose
    private Float timestamp;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("full")
    @Expose
    private String full;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Float timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }
}
