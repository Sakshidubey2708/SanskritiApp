package com.example.awizom.sanskritidecoreapp.modelnew;

import java.util.Date;

public class OrderNewOne {


    public long CustomerID;
    public String CustomerName;
    public String date ;
    public String Mobile;
    public String order_type ;
    public long orderid;
    public String Address;
    public String InteriorMobile;
    public String InteriorName ;

    public boolean is_tailorStatus ;
    public boolean is_orderStatus ;
    public boolean is_amountstatus ;
    public double amount_total;
    public double amount_pending ;
    public String t_name;

    public long OrderId;
    public String OrderDate ;
    public String room_type ;
    public String SubMaterialType ;
    public String SubUMOType ;
    public String design ;
    public double quantity ;
    public double mrp;
    public double discount ;
    public double AfterDiscountAmt;

    public long getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(long customerID) {
        CustomerID = customerID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getInteriorMobile() {
        return InteriorMobile;
    }

    public void setInteriorMobile(String interiorMobile) {
        InteriorMobile = interiorMobile;
    }

    public String getInteriorName() {
        return InteriorName;
    }

    public void setInteriorName(String interiorName) {
        InteriorName = interiorName;
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

    public double getAmount_total() {
        return amount_total;
    }

    public void setAmount_total(double amount_total) {
        this.amount_total = amount_total;
    }

    public double getAmount_pending() {
        return amount_pending;
    }

    public void setAmount_pending(double amount_pending) {
        this.amount_pending = amount_pending;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public long getOrderId() {
        return OrderId;
    }

    public void setOrderId(long orderId) {
        OrderId = orderId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getSubMaterialType() {
        return SubMaterialType;
    }

    public void setSubMaterialType(String subMaterialType) {
        SubMaterialType = subMaterialType;
    }

    public String getSubUMOType() {
        return SubUMOType;
    }

    public void setSubUMOType(String subUMOType) {
        SubUMOType = subUMOType;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getAfterDiscountAmt() {
        return AfterDiscountAmt;
    }

    public void setAfterDiscountAmt(double afterDiscountAmt) {
        AfterDiscountAmt = afterDiscountAmt;
    }
}
