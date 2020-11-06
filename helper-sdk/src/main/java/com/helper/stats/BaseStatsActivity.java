package com.helper.stats;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.helper.util.BaseConstants;
import com.helper.util.EncryptData;

/*  Usage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ....
        ....
        addStatistics(getStatisticsModel(id, title));

        String currentStatsLevelJson = getStatistics();
}*/
public abstract class BaseStatsActivity extends AppCompatActivity {

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

    @Override
    protected void onResume() {
        super.onResume();
        if(!isDisableOnResumeMethod)
            updateLastStats();
    }

    public BaseStatsActivity addStatistics(StatisticsLevel statisticsLevel) {
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

    private boolean isDisableOnResumeMethod = false;

    public void setDisableOnResumeMethod() {
        this.isDisableOnResumeMethod = true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isClearLastSavedData)
            LastStats.clear(this);
    }
}
