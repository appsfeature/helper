package com.helper.stats;

import android.app.Activity;

import com.helper.util.BaseConstants;
import com.helper.util.EncryptData;

public class AppStatistics {

    private StatisticsModel statisticsModel;
    private Activity activity;

    public StatisticsModel getStatisticsModel() {
        return statisticsModel != null ? statisticsModel.getClone() : new StatisticsModel();
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
        return new StatisticsLevel(id, title, statisticsModel != null ? statisticsModel.getLevels().size() + 1 : 0);
    }

    public String getStatistics() {
        return getStatistics(true);
    }
    //call after addStatistics() method
    public String getStatistics(boolean isEncrypt) {
        if (statisticsModel == null || statisticsModel.getLevels().size() == 0) {
            return "Empty";
        }
        if(isEncrypt) {
            return EncryptData.encode(StatsJsonCreator.toJson(statisticsModel));
        }else {
            return StatsJsonCreator.toJson(statisticsModel);
        }
    }

    public void onResume() {
        if(!isDisableOnResumeMethod)
            updateLastStats();
    }

    public AppStatistics addStatistics(StatisticsLevel statisticsLevel) {
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
            LastStats.setLastStats(activity, statisticsModel.getClone());
        }
    }
    private boolean isClearLastSavedData = false;

    public void setEnableOnDestroyMethod() {
        this.isClearLastSavedData = true;
    }

    private boolean isDisableOnResumeMethod = false;

    public void setDisableOnResumeMethod() {
        this.isDisableOnResumeMethod = true;
    }

    public void onDestroy() {
        if(isClearLastSavedData)
            LastStats.clear(activity);
    }
}
