package com.example.awizom.sanskritidecoreapp.modelnew;

public class StatusModel {

    public long status_id ;
    public boolean is_tailorStatus ;
    public boolean is_orderStatus ;
    public boolean is_amountstatus ;
    public long orderid ;

    public long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(long status_id) {
        this.status_id = status_id;
    }

    public boolean isIs_tailorStatus() {
        return is_tailorStatus;
    }

    public void setIs_tailorStatus(boolean is_tailorStatus) {
        this.is_tailorStatus = is_tailorStatus;
    }

    public boolean isIs_orderStatus() {
        return is_orderStatus;
    }

    public void setIs_orderStatus(boolean is_orderStatus) {
        this.is_orderStatus = is_orderStatus;
    }

    public boolean isIs_amountstatus() {
        return is_amountstatus;
    }

    public void setIs_amountstatus(boolean is_amountstatus) {
        this.is_amountstatus = is_amountstatus;
    }

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }
}
