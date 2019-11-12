package com.example.awizom.sanskritidecoreapp.model;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("Status")
    private Boolean Status;

    @SerializedName("Message")
    private String Message;

    @SerializedName("catalog_catagory_id")
    private long catalog_catagory_id;

    @SerializedName("OrderId")
    private long OrderId;

    @SerializedName("amount_id")
    private long amount_id;

    @SerializedName("catalog_item_id")
    private long catalog_item_id;

    @SerializedName("karigarID")
    private long karigarID;

    @SerializedName("KarigarCalculationID")
    private long KarigarCalculationID;

    @SerializedName("TailorCalculateID")
    private long TailorCalculateID;

    @SerializedName("t_id")
    private long t_id;
    @SerializedName("OrderItemID")
    private long OrderItemID;
    public Result(String Message, Boolean Status, long catalog_catagory_id,
                  long OrderId,long amount_id,long catalog_item_id,
                  long karigarID,long KarigarCalculationID,long TailorCalculateID,long t_id,long OrderItemID) {

        this.Message = Message;
        this.Status = Status;
        this.catalog_catagory_id = catalog_catagory_id;
        this.OrderId = OrderId;
        this.amount_id = amount_id;
        this.catalog_item_id = catalog_item_id;

        this.karigarID = karigarID;
        this.KarigarCalculationID = KarigarCalculationID;
        this.TailorCalculateID = TailorCalculateID;
        this.t_id = t_id;
        this.OrderItemID = OrderItemID;
    }

    public long getOrderItemID() {
        return OrderItemID;
    }

    public Boolean getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public long getCatalog_catagory_id() {
        return catalog_catagory_id;
    }

    public long getOrderId() {
        return OrderId;
    }

    public long getAmount_id() {
        return amount_id;
    }

    public long getCatalog_item_id() {
        return catalog_item_id;
    }

    public long getKarigarID() {
        return karigarID;
    }

    public long getKarigarCalculationID() {
        return KarigarCalculationID;
    }

    public long getTailorCalculateID() {
        return TailorCalculateID;
    }

    public long getT_id() {
        return t_id;
    }
}
