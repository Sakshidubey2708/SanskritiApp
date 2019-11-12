package com.example.awizom.sanskritidecoreapp.modelnew;

public class RoomModel {

    public long room_catagory_id ;
    public String room_type;
    public boolean active;

    public long SubMaterialID ;
    public String SubMaterialType;
    public String SubUMOType;

    public long MaterialID ;
    public String MaterialType;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getRoom_catagory_id() {
        return room_catagory_id;
    }

    public void setRoom_catagory_id(long room_catagory_id) {
        this.room_catagory_id = room_catagory_id;
    }

    public String getSubUMOType() {
        return SubUMOType;
    }

    public void setSubUMOType(String subUMOType) {
        SubUMOType = subUMOType;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getSubMaterialID() {
        return SubMaterialID;
    }

    public void setSubMaterialID(long subMaterialID) {
        SubMaterialID = subMaterialID;
    }

    public String getSubMaterialType() {
        return SubMaterialType;
    }

    public void setSubMaterialType(String subMaterialType) {
        SubMaterialType = subMaterialType;
    }

    public long getMaterialID() {
        return MaterialID;
    }

    public void setMaterialID(long materialID) {
        MaterialID = materialID;
    }

    public String getMaterialType() {
        return MaterialType;
    }

    public void setMaterialType(String materialType) {
        MaterialType = materialType;
    }
}
