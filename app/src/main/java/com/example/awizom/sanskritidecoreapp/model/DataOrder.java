package com.example.awizom.sanskritidecoreapp.model;

public class DataOrder {


    public int CustomerID;
    public String CustomerName;
    public String Address;
    public String Mobile;
    public String InteriorName;
    public String InteriorMobile;


    public int OrderID;
    public double Advance;
    public String OrderDate;
    public int SNO;
    public String RoomList;
    public String ActuRoomList;
    public String ARoomList;
    public String TelorList;
    public Double TotalAmount;
    public Double ATotalAmount;

    public String getActuRoomList() {
        return ActuRoomList;
    }

    public void setActuRoomList(String actuRoomList) {
        ActuRoomList = actuRoomList;
    }

    public Double getATotalAmount() {
        return ATotalAmount;
    }

    public void setATotalAmount(Double ATotalAmount) {
        this.ATotalAmount = ATotalAmount;
    }


    public String getTelorList() {
        return TelorList;
    }

    public void setTelorList(String telorList) {
        TelorList = telorList;
    }

    public String getARoomList() {
        return ARoomList;
    }

    public void setARoomList(String ARoomList) {
        this.ARoomList = ARoomList;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getInteriorName() {
        return InteriorName;
    }

    public void setInteriorName(String interiorName) {
        InteriorName = interiorName;
    }

    public String getInteriorMobile() {
        return InteriorMobile;
    }

    public void setInteriorMobile(String interiorMobile) {
        InteriorMobile = interiorMobile;
    }


    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public double getAdvance() {
        return Advance;
    }

    public void setAdvance(double advance) {
        Advance = advance;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public int getSNO() {
        return SNO;
    }

    public void setSNO(int SNO) {
        this.SNO = SNO;
    }

    public String getRoomList() {
        return RoomList;
    }

    public void setRoomList(String roomList) {
        RoomList = roomList;
    }

    public Double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        TotalAmount = totalAmount;
    }
}
