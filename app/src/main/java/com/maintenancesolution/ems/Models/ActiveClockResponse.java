package com.maintenancesolution.ems.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 2/5/18.
 */

public class ActiveClockResponse {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("center")
    @Expose
    private Center center;

    @SerializedName("clock_in_latitude")
    @Expose
    private String clockInLatitude;
    @SerializedName("clock_in_longitude")
    @Expose
    private String clockInLongitude;
    @SerializedName("clock_in_datetime")
    @Expose
    private TimeObject clockInDatetime;
   /* @SerializedName("clock_out_datetime")
    @Expose
    private TimeObject clockOutDatetime;*/


   /* @Nullable
    @SerializedName("clock_out_datetime")
    @Expose
    private TimeObject clockOutDatetime;*/

    @SerializedName("clock_out_latitude")
    @Expose
    private String clockOutLatitude;
    @SerializedName("clock_out_longitude")
    @Expose
    private String clockOutLongitude;
    @SerializedName("lunch_in_datetime")
    @Expose
    private TimeObject lunchInDatetime;
    @SerializedName("lunch_out_datetime")
    @Expose
    private TimeObject lunchOutDatetime;
    @SerializedName("hours")
    @Expose
    private Integer hours;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
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

   /* public TimeObject getClockOutDatetime() {
        return clockOutDatetime;
    }

    public void setClockOutDatetime(TimeObject clockOutDatetime) {
        this.clockOutDatetime = clockOutDatetime;
    }*/

    public Object getClockOutLatitude() {
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

    public TimeObject getLunchInDatetime() {
        return lunchInDatetime;
    }

    public void setLunchInDatetime(TimeObject lunchInDatetime) {
        this.lunchInDatetime = lunchInDatetime;
    }

    public TimeObject getLunchOutDatetime() {
        return lunchOutDatetime;
    }

    public void setLunchOutDatetime(TimeObject lunchOutDatetime) {
        this.lunchOutDatetime = lunchOutDatetime;
    }

    public TimeObject getClockInDatetime() {
        return clockInDatetime;
    }

    public void setClockInDatetime(TimeObject clockInDatetime) {
        this.clockInDatetime = clockInDatetime;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

}
