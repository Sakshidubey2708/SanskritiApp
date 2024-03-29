package com.example.awizom.sanskritidecoreapp.model;

public class UserModel {
    private String UserId;
    private String UserName;
    private String RoleId;
    private boolean Active;

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public UserModel(String userId, String userName, String roleId, boolean active) {
        UserId = userId;
        UserName = userName;
        RoleId = roleId;
        Active = active;
    }

    public UserModel() {
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }


    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }


}