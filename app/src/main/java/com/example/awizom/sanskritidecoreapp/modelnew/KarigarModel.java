package com.example.awizom.sanskritidecoreapp.modelnew;

public class KarigarModel {

    public long KarigarCalculationID ;
    public long karigarID ;
    public double karigarPrice ;
    public long quantity ;
    public long OrderId ;
    public String karigarName ;
    public String karigarContact;

    public long getKarigarCalculationID() {
        return KarigarCalculationID;
    }

    public void setKarigarCalculationID(long karigarCalculationID) {
        KarigarCalculationID = karigarCalculationID;
    }

    public long getKarigarID() {
        return karigarID;
    }

    public void setKarigarID(long karigarID) {
        this.karigarID = karigarID;
    }

    public double getKarigarPrice() {
        return karigarPrice;
    }

    public void setKarigarPrice(double karigarPrice) {
        this.karigarPrice = karigarPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getOrderId() {
        return OrderId;
    }

    public void setOrderId(long orderId) {
        OrderId = orderId;
    }

    public String getKarigarName() {
        return karigarName;
    }

    public void setKarigarName(String karigarName) {
        this.karigarName = karigarName;
    }

    public String getKarigarContact() {
        return karigarContact;
    }

    public void setKarigarContact(String karigarContact) {
        this.karigarContact = karigarContact;
    }
}
