package com.wdhhr.wswsvipnew.model.dataBase;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by felear on 2017/8/24 0024.
 */

public class CityBean extends DataSupport implements Parcelable {

    /**
     * AREA_ID : 62
     * NAME : 路南区
     * FID : 61
     * SORT : 0
     * LONGITUDE : 118.15431
     * LATITUDE : 39.62505
     * PINYIN : lunan
     * FIRST : L
     */

    private int AREA_ID;
    private String NAME;
    private String softName;
    private int FID;
    private int SORT;
    private double LONGITUDE;
    private double LATITUDE;
    private String PINYIN;
    private String FIRST;

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }

    public int getAREA_ID() {
        return AREA_ID;
    }

    public void setAREA_ID(int AREA_ID) {
        this.AREA_ID = AREA_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getFID() {
        return FID;
    }

    public void setFID(int FID) {
        this.FID = FID;
    }

    public int getSORT() {
        return SORT;
    }

    public void setSORT(int SORT) {
        this.SORT = SORT;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getPINYIN() {
        return PINYIN;
    }

    public void setPINYIN(String PINYIN) {
        this.PINYIN = PINYIN;
    }

    public String getFIRST() {
        return FIRST;
    }

    public void setFIRST(String FIRST) {
        this.FIRST = FIRST;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.AREA_ID);
        dest.writeString(this.NAME);
        dest.writeString(this.softName);
        dest.writeInt(this.FID);
        dest.writeInt(this.SORT);
        dest.writeDouble(this.LONGITUDE);
        dest.writeDouble(this.LATITUDE);
        dest.writeString(this.PINYIN);
        dest.writeString(this.FIRST);
    }

    public CityBean() {
    }

    protected CityBean(Parcel in) {
        this.AREA_ID = in.readInt();
        this.NAME = in.readString();
        this.softName = in.readString();
        this.FID = in.readInt();
        this.SORT = in.readInt();
        this.LONGITUDE = in.readDouble();
        this.LATITUDE = in.readDouble();
        this.PINYIN = in.readString();
        this.FIRST = in.readString();
    }

    public static final Parcelable.Creator<CityBean> CREATOR = new Parcelable.Creator<CityBean>() {
        @Override
        public CityBean createFromParcel(Parcel source) {
            return new CityBean(source);
        }

        @Override
        public CityBean[] newArray(int size) {
            return new CityBean[size];
        }
    };
}
