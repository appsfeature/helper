package com.helper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.reflect.TypeToken;
import com.helper.util.GsonParser;
import com.helper.util.LoggerCommon;

import java.io.Serializable;


/**
 * Room columnInfo used in pdf-viewer lib
 * Don't add new variable with @ColumnInfo annotation.
 * If you need to add new variable in this class than use @Ignore annotation.
 */
public class HistoryModelResponse implements Serializable, Cloneable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "autoId")
    private int autoId;

    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "subTitle")
    private String subTitle;

    @ColumnInfo(name = "itemType")
    private int itemType;

    @ColumnInfo(name = "viewCount")
    private int viewCount;

    @ColumnInfo(name = "viewCountFormatted")
    private String viewCountFormatted;

    @ColumnInfo(name = "jsonData")
    private String jsonData;

    @ColumnInfo(name = "itemState")
    private String itemState;

    @ColumnInfo(name = "catId")
    private int catId;

    @ColumnInfo(name = "subCatId")
    private int subCatId;

    @ColumnInfo(name = "createdAt")
    private String createdAt;

    @ColumnInfo(name = "rowCount")
    private int rowCount;

    @Ignore
    private String formattedDate;

    @Ignore
    private String extraData;

    @Ignore
    private String image;

    @Ignore
    private String video;

    @Ignore
    private String url;

    @Ignore
    private String extraString1;
    @Ignore
    private String extraString2;
    @Ignore
    private int extraInteger1;
    @Ignore
    private int extraInteger2;

    public HistoryModelResponse() {
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getViewCountFormatted() {
        return viewCountFormatted;
    }

    public void setViewCountFormatted(String viewCountFormatted) {
        this.viewCountFormatted = viewCountFormatted;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getItemState() {
        return itemState;
    }

    public void setItemState(String itemState) {
        this.itemState = itemState;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
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

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getExtraString1() {
        return extraString1;
    }

    public void setExtraString1(String extraString1) {
        this.extraString1 = extraString1;
    }

    public String getExtraString2() {
        return extraString2;
    }

    public void setExtraString2(String extraString2) {
        this.extraString2 = extraString2;
    }

    public int getExtraInteger1() {
        return extraInteger1;
    }

    public void setExtraInteger1(int extraInteger1) {
        this.extraInteger1 = extraInteger1;
    }

    public int getExtraInteger2() {
        return extraInteger2;
    }

    public void setExtraInteger2(int extraInteger2) {
        this.extraInteger2 = extraInteger2;
    }

    /**
     * @param typeCast : new TypeToken<ModelName>() {}
     */
    public <T> T getJsonModel(TypeToken<T> typeCast) {
        return GsonParser.fromJsonAll(jsonData, typeCast);
    }

    @Override
    @NonNull
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
