package com.example.awizom.sanskritidecoreapp.modelnew;

import com.google.gson.annotations.SerializedName;

public class OrderResponseModel {


    private Boolean Status;
    private String Message;
    public long orderid;

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }
}
