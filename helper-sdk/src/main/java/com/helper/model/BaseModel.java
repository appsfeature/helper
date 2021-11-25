package com.helper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.helper.model.base.BaseHelperAdModelClass;
import com.helper.util.GsonParser;

import java.io.Serializable;

public class BaseModel extends BaseHelperAdModelClass implements Serializable{

    @Expose
    @SerializedName(value="id")
    private int id;

    @Expose
    @SerializedName(value="title")
    private String title;

    @Expose
    @SerializedName(value="sub_title", alternate={"description"})
    private String subTitle;

    @Expose
    @SerializedName(value="image", alternate={"image_url"})
    private String image;

    @Expose
    @SerializedName(value="item_type", alternate={"type"})
    private int itemType;

    @Expose
    @SerializedName(value="view_count")
    private int viewCount;

    private String viewCountFormatted;

    @Expose
    @SerializedName(value="json_data")
    private String jsonData;

    @Expose
    @SerializedName(value="cat_id")
    private int catId;

    @Expose
    @SerializedName(value="sub_cat_id")
    private int subCatId;

    @Expose
    @SerializedName(value="updated_at")
    private String updatedAt;

    @Expose
    @SerializedName(value="created_at")
    private String createdAt;

    @Expose
    @SerializedName(value="row_count")
    private int rowCount;

    @Expose
    @SerializedName(value="ranking", alternate={"rank"})
    private int ranking;

    public BaseModel() {
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

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getViewCountFormatted() {
        return viewCountFormatted;
    }

    public void setViewCountFormatted(String viewCountFormatted) {
        this.viewCountFormatted = viewCountFormatted;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @param typeCast : new TypeToken<ModelName>() {}
     */
    public <T> T getJsonModel(TypeToken<T> typeCast) {
        return GsonParser.fromJsonAll(jsonData, typeCast);
    }

}
