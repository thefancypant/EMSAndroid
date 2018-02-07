package com.android.maintenancesolution.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 2/6/18.
 */

public class TimeObject {

    @SerializedName("time_military")
    @Expose
    private String timeMilitary;
    @SerializedName("full")
    @Expose
    private String full;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;

    public String getTimeMilitary() {
        return timeMilitary;
    }

    public void setTimeMilitary(String timeMilitary) {
        this.timeMilitary = timeMilitary;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
