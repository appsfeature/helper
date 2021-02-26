package com.helper.model;

import com.helper.util.LoggerCommon;

import java.io.Serializable;


public class HistoryModelResponse implements Serializable, Cloneable {

    private int id;
    private String title;
    private String subTitle;
    private int itemType;
    private String jsonData;
    private String createdAt;

    public HistoryModelResponse() {
    }

    public static HistoryModelResponse Builder() {
        return new HistoryModelResponse();
    }

    public int getId() {
        return id;
    }

    public HistoryModelResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public HistoryModelResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public HistoryModelResponse setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public int getItemType() {
        return itemType;
    }

    public HistoryModelResponse setItemType(int itemType) {
        this.itemType = itemType;
        return this;
    }

    public String getJsonData() {
        return jsonData;
    }

    public HistoryModelResponse setJsonData(String jsonData) {
        this.jsonData = jsonData;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public HistoryModelResponse setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public HistoryModelResponse getClone() {
        try {
            return (HistoryModelResponse) clone();
        } catch (CloneNotSupportedException e) {
            LoggerCommon.d(LoggerCommon.getClassPath(this.getClass(),"getClone"),e.toString());
            return new HistoryModelResponse();
        }
    }
}
