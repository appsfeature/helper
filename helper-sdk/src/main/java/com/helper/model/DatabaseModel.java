package com.helper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.helper.model.base.BaseDataModel;

import java.io.Serializable;

public class DatabaseModel extends BaseDataModel implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
