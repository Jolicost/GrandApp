package com.jauxim.grandapp.ui.Activity.ActivityInfo;

import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.UserModel;

/**
 * Created by ennur on 6/25/16.
 */
public interface ActivityInfoView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getActivityInfoSuccess(ActivityModel activityModel);

    void backToMainView();

    void viewProfile(String userId);

    void getProfileInfo(UserModel userModel);

    void showUnjoinText();

    void showJoinText();
}
