package com.cropmanagement;

public class crop {
    String crop_name;
    String crop_id;

    public crop(String crop_name, String crop_id) {
        this.crop_name = crop_name;
        this.crop_id = crop_id;
    }
    crop(){};

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public String getCrop_id(String crop_name) {
        return crop_id;
    }

    public void setCrop_id(String crop_id) {
        this.crop_id = crop_id;
    }

    String generateCropId(String cname){
        return crop_id;
    }
}
