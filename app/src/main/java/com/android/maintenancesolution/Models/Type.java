package com.android.maintenancesolution.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kalyan on 1/24/18.
 */

public class Type implements Parcelable {
    public static final Creator<Type> CREATOR = new Creator<Type>() {
        @Override
        public Type createFromParcel(Parcel in) {
            return new Type(in);
        }

        @Override
        public Type[] newArray(int size) {
            return new Type[size];
        }
    };
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("percentage_all_works_completed")
    @Expose
    private Integer percentageAllWorksCompleted;
    @SerializedName("num_works")
    @Expose
    private Integer numWorks;
    @SerializedName("num_works_incomplete")
    @Expose
    private Integer numWorksIncomplete;
    @SerializedName("num_works_complete")
    @Expose
    private Integer numWorksComplete;
    @SerializedName("num_assets")
    @Expose
    private Integer numAssets;

    protected Type(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            percentageAllWorksCompleted = null;
        } else {
            percentageAllWorksCompleted = in.readInt();
        }
        if (in.readByte() == 0) {
            numWorks = null;
        } else {
            numWorks = in.readInt();
        }
        if (in.readByte() == 0) {
            numWorksIncomplete = null;
        } else {
            numWorksIncomplete = in.readInt();
        }
        if (in.readByte() == 0) {
            numWorksComplete = null;
        } else {
            numWorksComplete = in.readInt();
        }
    }

    public Integer getNumAssets() {
        return numAssets;
    }

    public void setNumAssets(Integer numAssets) {
        this.numAssets = numAssets;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPercentageAllWorksCompleted() {
        return percentageAllWorksCompleted;
    }

    public void setPercentageAllWorksCompleted(Integer percentageAllWorksCompleted) {
        this.percentageAllWorksCompleted = percentageAllWorksCompleted;
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
        parcel.writeString(name);
        if (percentageAllWorksCompleted == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(percentageAllWorksCompleted);
        }
        if (numWorks == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numWorks);
        }
        if (numWorksIncomplete == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numWorksIncomplete);
        }
        if (numWorksComplete == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numWorksComplete);
        }
    }
}
