package com.android.maintenancesolution.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 2/10/18.
 */

public class UpdateTimeRequest {
    @SerializedName("center")
    @Expose
    private String center;
    @SerializedName("clock_in_latitude")
    @Expose
    private String clockInLatitude;
    @SerializedName("clock_in_longitude")
    @Expose
    private String clockInLongitude;
    @SerializedName("clock_in_datetime")
    @Expose
    private String clockInDatetime;
    @SerializedName("clock_out_datetime")
    @Expose
    private String clockOutDatetime;

    @SerializedName("clock_out_latitude")
    @Expose
    private String clockOutLatitude;
    @SerializedName("clock_out_longitude")
    @Expose
    private String clockOutLongitude;
    @SerializedName("lunch_in_datetime")
    @Expose
    private String lunchInDatetime;
    @SerializedName("lunch_out_datetime")
    @Expose
    private String lunchOutDatetime;

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getClockInLatitude() {
        return clockInLatitude;
    }

    public void setClockInLatitude(String clockInLatitude) {
        this.clockInLatitude = clockInLatitude;
    }

    public String getClockInLongitude() {
        return clockInLongitude;
    }

    public void setClockInLongitude(String clockInLongitude) {
        this.clockInLongitude = clockInLongitude;
    }

    public String getClockInDatetime() {
        return clockInDatetime;
    }

    public void setClockInDatetime(String clockInDatetime) {
        this.clockInDatetime = clockInDatetime;
    }

    public String getClockOutDatetime() {
        return clockOutDatetime;
    }

    public void setClockOutDatetime(String clockOutDatetime) {
        this.clockOutDatetime = clockOutDatetime;
    }

    public String getClockOutLatitude() {
        return clockOutLatitude;
    }

    public void setClockOutLatitude(String clockOutLatitude) {
        this.clockOutLatitude = clockOutLatitude;
    }

    public String getClockOutLongitude() {
        return clockOutLongitude;
    }

    public void setClockOutLongitude(String clockOutLongitude) {
        this.clockOutLongitude = clockOutLongitude;
    }

    public String getLunchInDatetime() {
        return lunchInDatetime;
    }

    public void setLunchInDatetime(String lunchInDatetime) {
        this.lunchInDatetime = lunchInDatetime;
    }

    public String getLunchOutDatetime() {
        return lunchOutDatetime;
    }

    public void setLunchOutDatetime(String lunchOutDatetime) {
        this.lunchOutDatetime = lunchOutDatetime;
    }

    @Override
    public String toString() {
        return "UpdateTimeRequest{" +
                "center='" + center + '\'' +
                ", clockInLatitude='" + clockInLatitude + '\'' +
                ", clockInLongitude='" + clockInLongitude + '\'' +
                ", clockInDatetime='" + clockInDatetime + '\'' +
                ", clockOutDatetime='" + clockOutDatetime + '\'' +
                ", clockOutLatitude='" + clockOutLatitude + '\'' +
                ", clockOutLongitude='" + clockOutLongitude + '\'' +
                ", lunchInDatetime='" + lunchInDatetime + '\'' +
                ", lunchOutDatetime='" + lunchOutDatetime + '\'' +
                '}';
    }
}
