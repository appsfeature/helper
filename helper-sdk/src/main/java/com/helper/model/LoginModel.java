package com.helper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @Expose
    @SerializedName(value="isLoginComplete")
    private boolean isLoginComplete;
    @Expose
    @SerializedName(value="userId")
    private String userId;
    @Expose
    @SerializedName(value="userName")
    private String userName;
    @Expose
    @SerializedName(value="userImage")
    private String userImage;
    @Expose
    @SerializedName(value="emailId")
    private String emailId;
    @Expose
    @SerializedName(value="mobileNo")
    private String mobileNo;

    public boolean isLoginComplete() {
        return isLoginComplete;
    }

    public LoginModel setLoginComplete(boolean loginComplete) {
        isLoginComplete = loginComplete;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public LoginModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public LoginModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserImage() {
        return userImage;
    }

    public LoginModel setUserImage(String userImage) {
        this.userImage = userImage;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public LoginModel setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public LoginModel setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }
}