package com.helper.stats;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.helper.util.BaseConstants;

/*  Usage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ....
        ....
        addStatistics(getStatisticsModel(id, title));

        String currentStatsLevelJson = getStatistics();
}*/
public abstract class BaseStatsFragment extends Fragment {

    private StatisticsModel statisticsModel;

    public StatisticsModel getStatisticsModel() {
        return statisticsModel.getClone();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().getSerializable(BaseConstants.STATISTICS) instanceof StatisticsModel) {
            statisticsModel = (StatisticsModel) ((StatisticsModel) getArguments().getSerializable(BaseConstants.STATISTICS)).getClone();
            updateLastStats();
        }else
            if (statisticsModel == null) {
            statisticsModel = LastStats.getLastStats(getActivity()).getClone();
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
    public void onResume() {
        super.onResume();
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
            LastStats.setLastStats(getActivity(), statisticsModel.getClone());
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        LastStats.clear(getActivity());
//    }
}
