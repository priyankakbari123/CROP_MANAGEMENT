package com.cropmanagement;

import java.util.Date;

class sowing_details {
    String crop_id,farmer_id;
    Date sowing_date;
    int Area,sowing_id;
//SETTERS
    public void setCrop_id(String crop_id) {
        this.crop_id = crop_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public void setShowing_date(Date showing_date) {
        this.sowing_date = showing_date;
    }

    public void setArea(int area) {
        Area = area;
    }

    //GETTERS

    public String getCrop_id() {
        return crop_id;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public Date getShowing_date() {
        return sowing_date;
    }

    public int getArea() {
        return Area;
    }



    public sowing_details(int sid,String crop_id, String farmer_id, Date showing_date, int area) {
        this.crop_id = crop_id;
        this.farmer_id = farmer_id;
        this.sowing_date = showing_date;
        Area = area;
        sowing_id=sid;
    }
    public sowing_details(){};


}
