package com.example.awizom.sanskritidecoreapp.modelnew;

public class TailorcatagoryModel {

    public long t_id;
    public String t_name ;
    public String t_contact;
    public long TailorWorkID;
    public String TailorWorkType ;

    public long getTailorWorkID() {
        return TailorWorkID;
    }

    public void setTailorWorkID(long tailorWorkID) {
        TailorWorkID = tailorWorkID;
    }

    public String getTailorWorkType() {
        return TailorWorkType;
    }

    public void setTailorWorkType(String tailorWorkType) {
        TailorWorkType = tailorWorkType;
    }

    public long getT_id() {
        return t_id;
    }

    public void setT_id(long t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_contact() {
        return t_contact;
    }

    public void setT_contact(String t_contact) {
        this.t_contact = t_contact;
    }
}
