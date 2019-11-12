package com.example.awizom.sanskritidecoreapp.modelnew;

import java.util.Date;

public class OrderModel {
    public long orderid;
    public long CustomerID;
    public Date date ;
    public String order_type ;
    public long room_catagory_id ;
    public String created_by ;

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public long getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(long customerID) {
        CustomerID = customerID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public long getRoom_catagory_id() {
        return room_catagory_id;
    }

    public void setRoom_catagory_id(long room_catagory_id) {
        this.room_catagory_id = room_catagory_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}
