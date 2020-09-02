package com.helper.model.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BasePropertyModel implements Serializable {

    @SerializedName(value="is_sub_cat", alternate={"isSubCat"})
    @Expose
    private boolean isSubCat;

    @Expose
    @SerializedName(value="is_date", alternate={"isDate"})
    private boolean isDate;

    @SerializedName(value="is_web_view", alternate={"isWebView"})
    @Expose
    private boolean isWebView;

    @SerializedName(value="is_see_ans", alternate={"isSeeAns"})
    @Expose
    private boolean isSeeAns;

    @SerializedName(value="quiz_id")
    @Expose
    private int quizId;

    @Expose
    @SerializedName(value="host", alternate={"host_alias"})
    private String host;

    public boolean isSubCat() {
        return isSubCat;
    }

    public void setSubCat(boolean subCat) {
        isSubCat = subCat;
    }

    public boolean isDate() {
        return isDate;
    }

    public void setDate(boolean date) {
        isDate = date;
    }

    public boolean isWebView() {
        return isWebView;
    }

    public void setWebView(boolean webView) {
        isWebView = webView;
    }

    public boolean isSeeAns() {
        return isSeeAns;
    }

    public void setSeeAns(boolean seeAns) {
        isSeeAns = seeAns;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
