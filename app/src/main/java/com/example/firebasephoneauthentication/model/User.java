package com.example.firebasephoneauthentication.model;

public class User {

    String phone;
    String UserName;
    String Status;

    public User() {
    }

    public User(String phone, String userName, String status) {
        this.phone = phone;
        UserName = userName;
        Status = status;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
