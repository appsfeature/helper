package com.helper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.helper.util.GsonParser;

public class UserProfile {

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
    @SerializedName(value="mobileNo")
    private String mobileNo;

    @Expose
    @SerializedName(value="emailId")
    private String emailId;

    @Expose
    @SerializedName(value="type")
    private int type;

    @Expose
    @SerializedName(value="mode")
    private int mode;

    @Expose
    @SerializedName(value="extras")
    private String extras;

    public String getUserId() {
        return userId;
    }

    public UserProfile setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserProfile setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserImage() {
        return userImage;
    }

    public UserProfile setUserImage(String userImage) {
        this.userImage = userImage;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public UserProfile setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public UserProfile setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public String getExtras() {
        return extras;
    }

    public UserProfile setExtras(String extras) {
        this.extras = extras;
        return this;
    }

    public int getType() {
        return type;
    }

    public UserProfile setType(int type) {
        this.type = type;
        return this;
    }

    public int getMode() {
        return mode;
    }

    public UserProfile setMode(int mode) {
        this.mode = mode;
        return this;
    }

    /**
     * @param tClass : ExtraModel.class
     */
    public <T> T getExtrasModel(Class<T> tClass) {
        return GsonParser.fromJsonAll(getExtras(), tClass);
    }
}