package com.jauxim.grandapp.ui.Activity.ActivityProfile;

import com.jauxim.grandapp.models.AchievementsModel;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.BlockModel;
import com.jauxim.grandapp.models.UserModel;

import java.util.List;

/**
 * Created by ennur on 6/25/16.
 */
public interface ActivityProfileView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getProfileInfo(UserModel userModel);

    void editProfile();

    void showBlockSuccess(int block_success);

    void showBlockText();

    void showUnblockText();

    void showAchievements(List<AchievementsModel> achievementsList);

    void setBlockedUsers(BlockModel blockModel);
}
