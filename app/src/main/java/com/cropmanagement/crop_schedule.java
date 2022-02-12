package com.cropmanagement;

public class crop_schedule {
    String crop_id,Title,Details;
    int day;

    crop_schedule(){};

    public crop_schedule(String crop_id, String title, String details, int day) {
        this.crop_id = crop_id;
        this.Title = title;
        this.Details = details;
        this.day = day;
    }

    //GETTER SETTER

    public String getCrop_id() {
        return crop_id;
    }

    public void setCrop_id(String crop_id) {
        this.crop_id = crop_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        this.Details = details;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
