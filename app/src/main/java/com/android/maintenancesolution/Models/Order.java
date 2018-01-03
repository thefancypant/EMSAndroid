package com.android.maintenancesolution.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kalyan on 1/2/18.
 */

public class Order implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("project")
    @Expose
    private Integer project;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("initial_time")
    @Expose
    private String initialTime;
    @SerializedName("leader")
    @Expose
    private Integer leader;
    @SerializedName("support1")
    @Expose
    private Object support1;
    @SerializedName("support2")
    @Expose
    private Object support2;
    @SerializedName("support3")
    @Expose
    private Object support3;
    @SerializedName("support4")
    @Expose
    private Object support4;
    @SerializedName("support5")
    @Expose
    private Object support5;
    @SerializedName("register_time")
    @Expose
    private String registerTime;
    @SerializedName("latitude_register")
    @Expose
    private Float latitudeRegister;
    @SerializedName("longitude_register")
    @Expose
    private Float longitudeRegister;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("report")
    @Expose
    private String report;
    @SerializedName("photo1")
    @Expose
    private Object photo1;
    @SerializedName("photo2")
    @Expose
    private Object photo2;
    @SerializedName("photo3")
    @Expose
    private Object photo3;
    @SerializedName("photo4")
    @Expose
    private Object photo4;
    @SerializedName("is_completed")
    @Expose
    private Boolean isCompleted;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("customer")
    @Expose
    private Integer customer;
    @SerializedName("evaluation")
    @Expose
    private Integer evaluation;
    @SerializedName("sign")
    @Expose
    private String sign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(String initialTime) {
        this.initialTime = initialTime;
    }

    public Integer getLeader() {
        return leader;
    }

    public void setLeader(Integer leader) {
        this.leader = leader;
    }

    public Object getSupport1() {
        return support1;
    }

    public void setSupport1(Object support1) {
        this.support1 = support1;
    }

    public Object getSupport2() {
        return support2;
    }

    public void setSupport2(Object support2) {
        this.support2 = support2;
    }

    public Object getSupport3() {
        return support3;
    }

    public void setSupport3(Object support3) {
        this.support3 = support3;
    }

    public Object getSupport4() {
        return support4;
    }

    public void setSupport4(Object support4) {
        this.support4 = support4;
    }

    public Object getSupport5() {
        return support5;
    }

    public void setSupport5(Object support5) {
        this.support5 = support5;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public Float getLatitudeRegister() {
        return latitudeRegister;
    }

    public void setLatitudeRegister(Float latitudeRegister) {
        this.latitudeRegister = latitudeRegister;
    }

    public Float getLongitudeRegister() {
        return longitudeRegister;
    }

    public void setLongitudeRegister(Float longitudeRegister) {
        this.longitudeRegister = longitudeRegister;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Object getPhoto1() {
        return photo1;
    }

    public void setPhoto1(Object photo1) {
        this.photo1 = photo1;
    }

    public Object getPhoto2() {
        return photo2;
    }

    public void setPhoto2(Object photo2) {
        this.photo2 = photo2;
    }

    public Object getPhoto3() {
        return photo3;
    }

    public void setPhoto3(Object photo3) {
        this.photo3 = photo3;
    }

    public Object getPhoto4() {
        return photo4;
    }

    public void setPhoto4(Object photo4) {
        this.photo4 = photo4;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
