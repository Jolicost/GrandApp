package com.jauxim.grandapp.ui.Activity.ActivityInfo;

import com.jauxim.grandapp.models.ActivityModel;

/**
 * Created by ennur on 6/25/16.
 */
public interface ActivityInfoView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getActivityInfoSuccess(ActivityModel activityModel);

    void backToMainView();

    void showDeleteSuccess(int login_error);

    void viewProfile(String userId);
}
