package com.jauxim.grandapp.ui.Activity.ActivityList;

import com.jauxim.grandapp.models.ActivityListItemModel;

import java.util.List;

/**
 * Created by ennur on 6/25/16.
 */
public interface MainView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getActivityListSuccess(List<ActivityListItemModel> activities);

}