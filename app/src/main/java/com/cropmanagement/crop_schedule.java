package com.cropmanagement;

public class crop_schedule {
    String crop_id,title,details;
    int day;

    crop_schedule(){};

    public crop_schedule(String crop_id, String title, String details, int day) {
        this.crop_id = crop_id;
        this.title = title;
        this.details = details;
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
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
