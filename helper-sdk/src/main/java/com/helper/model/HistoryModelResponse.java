package com.helper.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.reflect.TypeToken;
import com.helper.util.GsonParser;
import com.helper.util.LoggerCommon;

import java.io.Serializable;


public class HistoryModelResponse implements Serializable, Cloneable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "autoId")
    private int autoId;
    private int id;
    private String title;
    private String subTitle;
    private int itemType;
    private String jsonData;
    private int catId;
    private int subCatId;
    private String createdAt;

    public HistoryModelResponse() {
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(int subCatId) {
        this.subCatId = subCatId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @param typeCast : new TypeToken<ModelName>() {}
     */
    public <T> T getJsonModel(TypeToken<T> typeCast) {
        return GsonParser.fromJsonAll(jsonData, typeCast);
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
