package com.helper.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.helper.model.base.BaseDataModel;
import com.helper.util.GsonParser;

public class BaseCategoryProperty extends BaseDataModel{

    @SerializedName(value = "id", alternate = {"child_id"})
    @Expose
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("parent_id")
    @Expose
    @ColumnInfo(name = "parent_id")
    private int parentId;

    @SerializedName(value = "title", alternate = {"name"})
    @Expose
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("image")
    @Expose
    @ColumnInfo(name = "image")
    private String image;

    @SerializedName("ranking")
    @Expose
    @ColumnInfo(name = "ranking")
    private int ranking;

    @SerializedName(value = "item_type", alternate = {"category_type"})
    @Expose
    @ColumnInfo(name = "item_type")
    private int itemType;

    @SerializedName("host_alias")
    @Expose
    @ColumnInfo(name = "host_alias")
    private String hostAlias;

    @SerializedName("is_mapping_category")
    @Expose
    @ColumnInfo(name = "is_mapping_category")
    private int isMappingCategory;

    @SerializedName("other_properties")
    @Expose
    @ColumnInfo(name = "property")
    private String propertyJson;

    @Expose
    @Ignore
    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getHostAlias() {
        return hostAlias;
    }

    public void setHostAlias(String hostAlias) {
        this.hostAlias = hostAlias;
    }

    public int getIsMappingCategory() {
        return isMappingCategory;
    }

    public void setIsMappingCategory(int isMappingCategory) {
        this.isMappingCategory = isMappingCategory;
    }

    public String getPropertyJson() {
        return propertyJson;
    }

    public void setPropertyJson(String propertyJson) {
        this.propertyJson = propertyJson;
    }


//    public ModelName getOtherProperty() {
//        return getPropertyModel(new TypeToken<ModelName>() {});
//    }

    /**
     * @param typeCast : new TypeToken<ModelName>() {}
     */
    public <T> T getPropertyModel(TypeToken<T> typeCast) {
        return GsonParser.fromJson(propertyJson, typeCast);
    }
}