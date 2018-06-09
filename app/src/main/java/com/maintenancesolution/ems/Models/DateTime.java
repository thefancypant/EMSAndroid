package com.maintenancesolution.ems.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 1/6/18.
 */

public class DateTime {
    @SerializedName("full")
    @Expose
    private String full;
    @SerializedName("date")
    @Expose
    private String date;
    /*@SerializedName("timestamp")
    @Expose
    private Integer timestamp;*/
    @SerializedName("time")
    @Expose
    private String time;

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /*public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }*/

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
