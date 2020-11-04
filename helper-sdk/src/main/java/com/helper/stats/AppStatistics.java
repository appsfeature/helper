package com.helper.stats;

import android.app.Activity;

import com.helper.util.BaseConstants;

public class AppStatistics {

    private StatisticsModel statisticsModel;
    private Activity activity;

    public StatisticsModel getStatisticsModel() {
        return statisticsModel.getClone();
    }

    public void setStatisticsModel(StatisticsModel statisticsModel) {
        this.statisticsModel = statisticsModel;
    }

    public void onCreate(Activity activity) {
        this.activity = activity;
        if(activity.getIntent() != null && activity.getIntent().getExtras().getSerializable(BaseConstants.STATISTICS) instanceof StatisticsModel) {
            statisticsModel = (StatisticsModel) ((StatisticsModel) activity.getIntent().getExtras().getSerializable(BaseConstants.STATISTICS)).getClone();
            updateLastStats();
        }else if (statisticsModel == null) {
            statisticsModel = LastStats.getLastStats(activity).getClone();
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

    public void onResume() {
        updateLastStats();
    }

    public void addStatistics(StatisticsLevel statisticsLevel) {
        if (statisticsModel != null) {
            statisticsModel.getLevels().add(statisticsLevel);
        }
        updateLastStats();
    }

    public void removeLastStatistics() {
        if (statisticsModel != null && statisticsModel.getLevels().size() > 0) {
            statisticsModel.getLevels().remove(statisticsModel.getLevels().size() - 1);
        }
        updateLastStats();
    }

    private void updateLastStats() {
        if (statisticsModel != null) {
            LastStats.setLastStats(activity, statisticsModel.getClone());
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        LastStats.clear(this);
//    }
}
