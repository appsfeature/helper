package com.helper.stats;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.adssdk.PageAdsAppCompactActivity;
import com.helper.util.BaseConstants;

/*  Usage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ....
        ....
        addStatistics(getStatisticsModel(id, title));

        String currentStatsLevelJson = getStatistics();
}*/
public class BaseStatsAdsActivity extends PageAdsAppCompactActivity {

    private StatisticsModel statisticsModel;

    public StatisticsModel getStatisticsModel() {
        return statisticsModel != null ? statisticsModel.getClone() : new StatisticsModel();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent() != null && getIntent().getSerializableExtra(BaseConstants.STATISTICS) instanceof StatisticsModel) {
            statisticsModel = (StatisticsModel) ((StatisticsModel) getIntent().getSerializableExtra(BaseConstants.STATISTICS)).getClone();
            updateLastStats();
        }else if (statisticsModel == null) {
            statisticsModel = LastStats.getLastStats(this).getClone();
        }
    }

    public StatisticsLevel getStatisticsLevel(int id, String title) {
        return getStatisticsLevel(id + "", title);
    }
    public StatisticsLevel getStatisticsLevel(String id, String title) {
        return new StatisticsLevel(id, title, statisticsModel != null ? statisticsModel.getLevels().size() + 1 : 0);
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

    public BaseStatsAdsActivity addStatistics(StatisticsLevel statisticsLevel) {
        if (statisticsModel != null) {
            statisticsModel.getLevels().add(statisticsLevel);
        }
        updateLastStats();
        return this;
    }

    public void removeLastStatistics() {
        if (statisticsModel != null && statisticsModel.getLevels().size() > 0) {
            statisticsModel.getLevels().remove(statisticsModel.getLevels().size() - 1);
        }
        updateLastStats();
    }

    private void updateLastStats() {
        if (statisticsModel != null) {
            LastStats.setLastStats(this, statisticsModel.getClone());
        }
    }
    private boolean isClearLastSavedData = false;

    public void setEnableOnDestroyMethod() {
        this.isClearLastSavedData = true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isClearLastSavedData)
            LastStats.clear(this);
    }
}
