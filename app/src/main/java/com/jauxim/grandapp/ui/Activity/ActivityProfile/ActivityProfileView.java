package com.jauxim.grandapp.ui.Activity.ActivityProfile;

import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.UserModel;

/**
 * Created by ennur on 6/25/16.
 */
public interface ActivityProfileView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getProfileInfo(UserModel userModel);

    void editProfile();
}
