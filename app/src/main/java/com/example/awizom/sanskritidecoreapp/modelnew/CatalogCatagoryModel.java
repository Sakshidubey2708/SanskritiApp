package com.example.awizom.sanskritidecoreapp.modelnew;

public class CatalogCatagoryModel {

    public long catalog_catagory_id;
    public String design;
    public String serial_no ;
    public int page_no ;
    public int quantity ;
    public Double mrp;
    public String quality;
    public Double discount;
    public long room_catagory_id;
    public long umo_id;
    public String measurment_data;
    public String CreatedBy;
    public Double AfterDiscountAmt;
    public long SubMaterialID;
    public String SubMaterialType;
    public String SubUMOType;
    public  long catalog_item_id;


    public long getUmo_id() {
        return umo_id;
    }

    public void setUmo_id(long umo_id) {
        this.umo_id = umo_id;
    }

    public String getMeasurment_data() {
        return measurment_data;
    }

    public void setMeasurment_data(String measurment_data) {
        this.measurment_data = measurment_data;
    }

    public long getCatalog_item_id() {
        return catalog_item_id;
    }

    public void setCatalog_item_id(long catalog_item_id) {
        this.catalog_item_id = catalog_item_id;
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

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public Double getAfterDiscountAmt() {
        return AfterDiscountAmt;
    }

    public void setAfterDiscountAmt(Double afterDiscountAmt) {
        AfterDiscountAmt = afterDiscountAmt;
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

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public long getRoom_catagory_id() {
        return room_catagory_id;
    }

    public void setRoom_catagory_id(long room_catagory_id) {
        this.room_catagory_id = room_catagory_id;
    }
}
