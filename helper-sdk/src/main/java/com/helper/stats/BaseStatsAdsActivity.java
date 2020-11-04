package com.helper.stats;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.adssdk.PageAdsAppCompactActivity;
/*  Usage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ....
        ....
        addStatistics(getStatisticsModel(id, title));

        String currentStatsLevelJson = getStatistics();
}*/
public abstract class BaseStatsAdsActivity extends PageAdsAppCompactActivity {

    private StatisticsModel statisticsModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (statisticsModel == null) {
            statisticsModel = LastStats.getLastStats(this);
        }
    }

    public StatisticsLevel getStatisticsLevel(int id, String title) {
        return getStatisticsLevel(id + "", title);
    }
    public StatisticsLevel getStatisticsLevel(String id, String title) {
        return new StatisticsLevel(id, title, statisticsModel.getLevels().size() + 1);
    }

    //call after addStatistics() method
    public String getStatistics() {
        if (statisticsModel == null || statisticsModel.getLevels().size() == 0) {
            return "Empty";
        }
        return StatsJsonCreator.toJson(statisticsModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLastStats();
    }

    public void addStatistics(StatisticsLevel statisticsLevel) {
        statisticsModel.getLevels().add(statisticsLevel);
        updateLastStats();
    }

    private void updateLastStats() {
        if (statisticsModel != null) {
            LastStats.setLastStats(this, statisticsModel.getClone());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LastStats.clear(this);
    }
}
