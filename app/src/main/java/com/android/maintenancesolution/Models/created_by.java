package com.android.maintenancesolution.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 1/15/18.
 */

public class created_by {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("num_works")
    @Expose
    private Integer numWorks;
    @SerializedName("num_works_incomplete")
    @Expose
    private Integer numWorksIncomplete;
    @SerializedName("num_works_complete")
    @Expose
    private Integer numWorksComplete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getNumWorks() {
        return numWorks;
    }

    public void setNumWorks(Integer numWorks) {
        this.numWorks = numWorks;
    }

    public Integer getNumWorksIncomplete() {
        return numWorksIncomplete;
    }

    public void setNumWorksIncomplete(Integer numWorksIncomplete) {
        this.numWorksIncomplete = numWorksIncomplete;
    }

    public Integer getNumWorksComplete() {
        return numWorksComplete;
    }

    public void setNumWorksComplete(Integer numWorksComplete) {
        this.numWorksComplete = numWorksComplete;
    }


}
