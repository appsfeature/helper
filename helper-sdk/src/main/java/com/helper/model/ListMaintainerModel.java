package com.helper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class ListMaintainerModel implements Serializable {

    @Expose
    @SerializedName(value="id")
    private int id;

    @Expose
    @SerializedName(value="title")
    private String title;

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

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        ListMaintainerModel otherItem = (ListMaintainerModel) other;
        return id == otherItem.id && Objects.equals(title, otherItem.title); // Assuming 'id' is unique for each user
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
