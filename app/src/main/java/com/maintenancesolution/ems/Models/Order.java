package com.maintenancesolution.ems.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kalyan on 1/2/18.
 */

public class Order implements Parcelable {


    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("project")
    @Expose
    private Project project;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("date_time")
    @Expose
    private DateTime dateTime;
    @SerializedName("leader")
    @Expose
    private Leader leader;
    @SerializedName("supporters")
    @Expose
    private List<Object> supporters = null;
    @SerializedName("register_time")
    @Expose
    private RegisterTime registerTime;
    @SerializedName("latitude_register")
    @Expose
    private Float latitudeRegister;
    @SerializedName("longitude_register")
    @Expose
    private Float longitudeRegister;
    /*@SerializedName("end_time")
    @Expose
    private String endTime;*/
    @SerializedName("report")
    @Expose
    private String report;
    @SerializedName("job_types")
    @Expose
    private String jobtypes;
    @SerializedName("types")
    @Expose
    private List<Type> types;
    @SerializedName("before_photo1")
    @Expose
    private String beforePhoto1;
    @SerializedName("before_photo2")
    @Expose
    private String beforePhoto2;
    @SerializedName("before_photo3")
    @Expose
    private String beforePhoto3;
    @SerializedName("before_photo4")
    @Expose
    private String beforePhoto4;
    @SerializedName("before_photo5")
    @Expose
    private String beforePhoto5;
    @SerializedName("before_photo6")
    @Expose
    private String beforePhoto6;
    @SerializedName("before_photo7")
    @Expose
    private String beforePhoto7;
    @SerializedName("before_photo8")
    @Expose
    private String beforePhoto8;
    @SerializedName("after_photo1")
    @Expose
    private String afterPhoto1;
    @SerializedName("after_photo2")
    @Expose
    private String afterPhoto2;
    @SerializedName("after_photo3")
    @Expose
    private String afterPhoto3;
    @SerializedName("after_photo4")
    @Expose
    private String afterPhoto4;
    @SerializedName("after_photo5")
    @Expose
    private String afterPhoto5;
    @SerializedName("after_photo6")
    @Expose
    private String afterPhoto6;
    @SerializedName("after_photo7")
    @Expose
    private String afterPhoto7;
    @SerializedName("after_photo8")
    @Expose
    private String afterPhoto8;
    @SerializedName("is_completed")
    @Expose
    private Boolean isCompleted;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("evaluation")
    @Expose
    private Integer evaluation;
    @SerializedName("sign")
    @Expose
    private String sign;


    public Order() {

        //Empty Constructor
    }

    protected Order(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        address = in.readString();
        if (in.readByte() == 0) {
            latitudeRegister = null;
        } else {
            latitudeRegister = in.readFloat();
        }
        if (in.readByte() == 0) {
            longitudeRegister = null;
        } else {
            longitudeRegister = in.readFloat();
        }
        //endTime = in.readString();
        report = in.readString();
        jobtypes = in.readString();
        types = in.createTypedArrayList(Type.CREATOR);
        beforePhoto1 = in.readString();
        beforePhoto2 = in.readString();
        beforePhoto3 = in.readString();
        beforePhoto4 = in.readString();
        beforePhoto5 = in.readString();
        beforePhoto6 = in.readString();
        beforePhoto7 = in.readString();
        beforePhoto8 = in.readString();
        afterPhoto1 = in.readString();
        afterPhoto2 = in.readString();
        afterPhoto3 = in.readString();
        afterPhoto4 = in.readString();
        afterPhoto5 = in.readString();
        afterPhoto6 = in.readString();
        afterPhoto7 = in.readString();
        afterPhoto8 = in.readString();
        byte tmpIsCompleted = in.readByte();
        isCompleted = tmpIsCompleted == 0 ? null : tmpIsCompleted == 1;
        notes = in.readString();
        if (in.readByte() == 0) {
            evaluation = null;
        } else {
            evaluation = in.readInt();
        }
        sign = in.readString();
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public List<Object> getSupporters() {
        return supporters;
    }

    public void setSupporters(List<Object> supporters) {
        this.supporters = supporters;
    }

    public RegisterTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(RegisterTime registerTime) {
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

   /*public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }*/

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getBeforePhoto1() {
        return beforePhoto1;
    }

    public void setBeforePhoto1(String beforePhoto1) {
        this.beforePhoto1 = beforePhoto1;
    }

    public String getBeforePhoto2() {
        return beforePhoto2;
    }

    public void setBeforePhoto2(String beforePhoto2) {
        this.beforePhoto2 = beforePhoto2;
    }

    public String getBeforePhoto3() {
        return beforePhoto3;
    }

    public void setBeforePhoto3(String beforePhoto3) {
        this.beforePhoto3 = beforePhoto3;
    }

    public String getBeforePhoto4() {
        return beforePhoto4;
    }

    public void setBeforePhoto4(String beforePhoto4) {
        this.beforePhoto4 = beforePhoto4;
    }

    public String getBeforePhoto5() {
        return beforePhoto5;
    }

    public void setBeforePhoto5(String beforePhoto5) {
        this.beforePhoto5 = beforePhoto5;
    }

    public String getBeforePhoto6() {
        return beforePhoto6;
    }

    public void setBeforePhoto6(String beforePhoto6) {
        this.beforePhoto6 = beforePhoto6;
    }

    public String getBeforePhoto7() {
        return beforePhoto7;
    }

    public void setBeforePhoto7(String beforePhoto7) {
        this.beforePhoto7 = beforePhoto7;
    }

    public String getBeforePhoto8() {
        return beforePhoto8;
    }

    public void setBeforePhoto8(String beforePhoto8) {
        this.beforePhoto8 = beforePhoto8;
    }

    public String getAfterPhoto1() {
        return afterPhoto1;
    }

    public void setAfterPhoto1(String afterPhoto1) {
        this.afterPhoto1 = afterPhoto1;
    }

    public String getAfterPhoto2() {
        return afterPhoto2;
    }

    public void setAfterPhoto2(String afterPhoto2) {
        this.afterPhoto2 = afterPhoto2;
    }

    public String getAfterPhoto3() {
        return afterPhoto3;
    }

    public void setAfterPhoto3(String afterPhoto3) {
        this.afterPhoto3 = afterPhoto3;
    }

    public String getAfterPhoto4() {
        return afterPhoto4;
    }

    public void setAfterPhoto4(String afterPhoto4) {
        this.afterPhoto4 = afterPhoto4;
    }

    public String getAfterPhoto5() {
        return afterPhoto5;
    }

    public void setAfterPhoto5(String afterPhoto5) {
        this.afterPhoto5 = afterPhoto5;
    }

    public String getAfterPhoto6() {
        return afterPhoto6;
    }

    public void setAfterPhoto6(String afterPhoto6) {
        this.afterPhoto6 = afterPhoto6;
    }

    public String getAfterPhoto7() {
        return afterPhoto7;
    }

    public void setAfterPhoto7(String afterPhoto7) {
        this.afterPhoto7 = afterPhoto7;
    }

    public String getAfterPhoto8() {
        return afterPhoto8;
    }

    public void setAfterPhoto8(String afterPhoto8) {
        this.afterPhoto8 = afterPhoto8;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
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

    public String getJobtypes() {
        return jobtypes;
    }

    public void setJobtypes(String jobtypes) {
        this.jobtypes = jobtypes;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(address);
        if (latitudeRegister == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(latitudeRegister);
        }
        if (longitudeRegister == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(longitudeRegister);
        }
        //parcel.writeString(endTime);
        parcel.writeString(report);
        parcel.writeString(jobtypes);
        parcel.writeTypedList(types);
        parcel.writeString(beforePhoto1);
        parcel.writeString(beforePhoto2);
        parcel.writeString(beforePhoto3);
        parcel.writeString(beforePhoto4);
        parcel.writeString(beforePhoto5);
        parcel.writeString(beforePhoto6);
        parcel.writeString(beforePhoto7);
        parcel.writeString(beforePhoto8);
        parcel.writeString(afterPhoto1);
        parcel.writeString(afterPhoto2);
        parcel.writeString(afterPhoto3);
        parcel.writeString(afterPhoto4);
        parcel.writeString(afterPhoto5);
        parcel.writeString(afterPhoto6);
        parcel.writeString(afterPhoto7);
        parcel.writeString(afterPhoto8);
        parcel.writeByte((byte) (isCompleted == null ? 0 : isCompleted ? 1 : 2));
        parcel.writeString(notes);
        if (evaluation == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(evaluation);
        }
        parcel.writeString(sign);
    }
}
