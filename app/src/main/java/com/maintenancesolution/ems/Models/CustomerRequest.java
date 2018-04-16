package com.maintenancesolution.ems.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

/**
 * Created by kalyan on 12/21/17.
 */

public class CustomerRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("types")
    @Expose
    private String types;
    @SerializedName("message")
    @Expose
    private String message;

   /* @SerializedName("photo1")
    @Expose
    private File photoPart1;

    @SerializedName("photo2")
    @Expose
    private File photoPart2;*/

    public CustomerRequest(String name, String address, String email, String phone, String notes, String types, File photoPart1, File photoPart2) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.notes = notes;
        this.types = types;
        /*this.photoPart1 = photoPart1;
        this.photoPart2 = photoPart2;*/
    }


   /*public CustomerRequest(String name, String address, String email, String phone, String notes, String types) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.notes = notes;
        this.types = types;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
