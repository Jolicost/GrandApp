package com.jauxim.grandapp.ui.Fragment.ActiviesList;

import com.jauxim.grandapp.models.ActivityListItemModel;

import java.util.List;

public interface ActivitiesListView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getActivityListSuccess(List<ActivityListItemModel> activities);
}
