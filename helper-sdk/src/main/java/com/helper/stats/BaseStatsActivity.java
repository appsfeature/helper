package com.helper.stats;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
/*  Usage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ....
        ....
        addStatistics(getStatisticsModel(id, title));

        String currentStatsLevelJson = getStatistics();
}*/
public abstract class BaseStatsActivity extends AppCompatActivity {

    private ArrayList<StatisticsModel> previousLevel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (previousLevel == null) {
            previousLevel = LastStats.getLastStats();
        }
    }

    public StatisticsModel getStatisticsModel(int id, String title) {
        return getStatisticsModel(id + "", title);
    }
    public StatisticsModel getStatisticsModel(String id, String title) {
        return new StatisticsModel(id, title, previousLevel.size() + 1);
    }

    //call after addStatistics() method
    public String getStatistics() {
        if (previousLevel == null || previousLevel.size() == 0) {
            return "Empty";
        }
        return StatsJsonCreator.toJson(previousLevel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLastStats();
    }

    public void addStatistics(StatisticsModel statisticsModel) {
        previousLevel.add(statisticsModel);
        updateLastStats();
    }

    private void updateLastStats() {
        if (previousLevel != null) {
            ArrayList<StatisticsModel> clone = new ArrayList<>(previousLevel);
            LastStats.setLastStats(clone);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LastStats.clear();
    }
}
