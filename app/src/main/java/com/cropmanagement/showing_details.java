package com.cropmanagement;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

class sowing_details {
    String crop_id,farmer_id,farm_name;
    Date sowing_date;
    int area;



    int sowing_id;
//SETTERS
    public void setCrop_id(String crop_id) {
        this.crop_id = crop_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public void setArea(int area) {
        area = area;
    }

    public void setSowing_id(int sid) {
        sowing_id = sid;
    }

    public void setFarm_name(String farm_name) {
        this.farm_name = farm_name;
    }
    //GETTERS

    public String getCrop_id() {
        return crop_id;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public int getArea() {
        return area;
    }

    public Date getSowing_date() {
        return sowing_date;
    }

    public int getSowing_id() {
        return sowing_id;
    }

    public String getFarm_name() {
        return farm_name;
    }

    public sowing_details(int sid, String crop_id, String farmer_id, Date showing_date, int area) {
        this.crop_id = crop_id;
        this.farmer_id = farmer_id;
        this.sowing_date = showing_date;
        this.area = area;
        sowing_id=sid;
    }
    public sowing_details(){};




}
