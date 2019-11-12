package com.example.awizom.sanskritidecoreapp.modelnew;

public class MeasurementModel {

    public long measur_id ;
    public long umo_id ;
    public double msubcatagory_id ;
    public String measurment_data;
    public String umo_type;

    public long getMeasur_id() {
        return measur_id;
    }

    public void setMeasur_id(long measur_id) {
        this.measur_id = measur_id;
    }

    public long getUmo_id() {
        return umo_id;
    }

    public void setUmo_id(long umo_id) {
        this.umo_id = umo_id;
    }

    public double getMsubcatagory_id() {
        return msubcatagory_id;
    }

    public void setMsubcatagory_id(double msubcatagory_id) {
        this.msubcatagory_id = msubcatagory_id;
    }

    public String getMeasurment_data() {
        return measurment_data;
    }

    public void setMeasurment_data(String measurment_data) {
        this.measurment_data = measurment_data;
    }

    public String getUmo_type() {
        return umo_type;
    }

    public void setUmo_type(String umo_type) {
        this.umo_type = umo_type;
    }
}
