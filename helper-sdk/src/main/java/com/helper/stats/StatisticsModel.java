package com.helper.stats;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StatisticsModel implements Serializable, Cloneable {

    @SerializedName("levels")
    @Expose
    private ArrayList<StatisticsLevel> levels;

    public ArrayList<StatisticsLevel> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<StatisticsLevel> levels) {
        this.levels = levels;
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
