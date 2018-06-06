package com.maintenancesolution.ems.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SignupResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("is_active")
    @Expose
    private boolean isActive;


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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
