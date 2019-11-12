package com.example.awizom.sanskritidecoreapp.modelnew;

public class CustomerOrderModel {

    public long OrderItemID;
    public long OrderId;
    public long room_catagory_id;
    public long SubMaterialID;
    public long catalog_catagory_id;
    public String OrderDate;
    public long CustomerID;
    public String CreatedBy;
    public String SubMaterialType;
    public String room_type;
    public String design;
    public double mrp;
    public double discount;
    public double quantity;
    public double AfterDiscountAmt;
    public String CustomerName;
    public float amount_pending;
    public  float amount_advance;

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public float getAmount_pending() {
        return amount_pending;
    }

    public void setAmount_pending(float amount_pending) {
        this.amount_pending = amount_pending;
    }

    public float getAmount_advance() {
        return amount_advance;
    }

    public void setAmount_advance(float amount_advance) {
        this.amount_advance = amount_advance;
    }

    public String getSubMaterialType() {
        return SubMaterialType;
    }

    public void setSubMaterialType(String subMaterialType) {
        SubMaterialType = subMaterialType;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
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

    public double getQuantity(

    ) {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getOrderItemID() {
        return OrderItemID;
    }

    public void setOrderItemID(long orderItemID) {
        OrderItemID = orderItemID;
    }

    public long getOrderId() {
        return OrderId;
    }

    public void setOrderId(long orderId) {
        OrderId = orderId;
    }

    public long getRoom_catagory_id() {
        return room_catagory_id;
    }

    public void setRoom_catagory_id(long room_catagory_id) {
        this.room_catagory_id = room_catagory_id;
    }

    public long getSubMaterialID() {
        return SubMaterialID;
    }

    public void setSubMaterialID(long subMaterialID) {
        SubMaterialID = subMaterialID;
    }

    public long getCatalog_catagory_id() {
        return catalog_catagory_id;
    }

    public void setCatalog_catagory_id(long catalog_catagory_id) {
        this.catalog_catagory_id = catalog_catagory_id;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public long getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(long customerID) {
        CustomerID = customerID;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public double getAfterDiscountAmt() {
        return AfterDiscountAmt;
    }

    public void setAfterDiscountAmt(double afterDiscountAmt) {
        AfterDiscountAmt = afterDiscountAmt;
    }
}
