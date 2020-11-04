package com.helper.stats;


import java.io.Serializable;

public class StatisticsModel implements Serializable, Cloneable {

    private String id;
    private String title;
    private String level;

    public StatisticsModel() {
    }

    public StatisticsModel(String id, String title, int level) {
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

    public StatisticsModel getClone() {
        try {
            return (StatisticsModel) clone();
        } catch (CloneNotSupportedException e) {
            return new StatisticsModel();
        }
    }
}
