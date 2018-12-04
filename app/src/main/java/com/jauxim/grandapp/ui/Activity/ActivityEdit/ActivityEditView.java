package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import com.jauxim.grandapp.models.ActivityModel;

/**
 * Created by ennur on 6/25/16.
 */
public interface ActivityEditView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void createActivityInfoSuccess(ActivityModel activityModel);

    void getActivityInfoSuccess(ActivityModel activityModel);
}
