package com.helper.stats;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/*  Usage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ....
        ....
        addStatistics(getStatisticsModel(id, title));

        String currentStatsLevelJson = getStatistics();
}*/
public abstract class BaseStatsFragment extends Fragment {

    private ArrayList<StatisticsModel> previousLevel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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

    public String getStatistics() {
        if (previousLevel == null || previousLevel.size() == 0) {
            return "Empty";
        }
        return StatsJsonCreator.toJson(previousLevel);
    }

    @Override
    public void onResume() {
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
    public void onDestroy() {
        super.onDestroy();
        LastStats.clear();
    }
}
