package com.helper.stats;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatisticsLevel implements Serializable, Cloneable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("level")
    @Expose
    private String level;

    public StatisticsLevel() {
    }

    public StatisticsLevel(String id, String title, int level) {
        this.id = id;
        this.title = title;
        this.level = level + "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public StatisticsLevel getClone() {
        try {
            return (StatisticsLevel) clone();
        } catch (CloneNotSupportedException e) {
            return new StatisticsLevel();
        }
    }
}
