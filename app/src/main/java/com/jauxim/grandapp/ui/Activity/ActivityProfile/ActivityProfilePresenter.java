package com.jauxim.grandapp.ui.Activity.ActivityProfile;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.AchievementsModel;
import com.jauxim.grandapp.models.BlockModel;
import com.jauxim.grandapp.models.ChangePasswordModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class ActivityProfilePresenter {
    private final Service service;
    private final ActivityProfileView view;
    private CompositeSubscription subscriptions;

    public ActivityProfilePresenter(Service service, ActivityProfileView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getProfileInfo(String id) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Log.d("userId", "auth: "+auth);
        Log.d("userId", "user: "+id);
        Subscription subscription = service.getProfileInfo(id, new Service.ProfileInfoCallback() {
            @Override
            public void onSuccess(UserModel userModel) {
                String userid = DataUtils.getUserInfo((Context)view).getId();
                if (!TextUtils.isEmpty(userModel.getId()) && userModel.getId().equals(userid)){
                    DataUtils.saveUserModel((Context)view, userModel);
                }
                view.removeWait();
                view.getProfileInfo(userModel);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void editProfile() {
        view.editProfile();
    }

    public void blockUser(final String profileId) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.blockUser(profileId, new Service.BlockUserCallback() {
            @Override
            public void onSuccess() {
                UserModel user = DataUtils.getUserInfo((Context) view);
                user.getBlocked().add(profileId);
                DataUtils.saveUserModel((Context)view, user);
                view.removeWait();
                view.showUnblockText();
                view.showBlockSuccess(R.string.block_success);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void unblockUser(final String profileId) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.unblockUser(profileId, new Service.UnblockUserCallback() {
            @Override
            public void onSuccess() {
                UserModel user = DataUtils.getUserInfo((Context) view);
                user.getBlocked().remove(profileId);
                DataUtils.saveUserModel((Context)view, user);
                view.removeWait();
                view.showBlockText();
                view.showBlockSuccess(R.string.unblock_success);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void getAchievements(String profileId) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.getAchievements(profileId, new Service.AchievementsCallback() {
            @Override
            public void onSuccess(List<AchievementsModel> achievementsList) {
                view.removeWait();
                view.showAchievements(achievementsList);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }


    public void getName(String id) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.getProfileInfo(id, new Service.ProfileInfoCallback() {
            @Override
            public void onSuccess(UserModel userModel) {
                view.removeWait();
                view.setBlockedUsers(new BlockModel(userModel.getId(), userModel.getCompleteName(), userModel.getProfilePic()));
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void changePassword(String pOld, String pNew, String pNewRe) {

        view.resetErrors();

        boolean error = false;

        if (pOld.isEmpty()) {
            view.showOldPassError(R.string.pass_error);
            error = true;
        }
        if (pNew.isEmpty()) {
            view.showNewPassError(R.string.pass_error);
            error = true;
        }
        else if (pNewRe.isEmpty()) {
            view.showNewRePassError(R.string.pass_error);
            error = true;
        }
        else if (!pNew.equals(pNewRe)) {
            view.showPass2Error(R.string.pass2_error);
            error = true;
        }

        if (error) return;

        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        ChangePasswordModel cpm = new ChangePasswordModel(pOld, pNew);
        Log.d("blocked old", pOld);
        Log.d("blocked new", pNew);
        Subscription subscription = service.changePassword(cpm, new Service.ChangePasswordCallback() {
            @Override
            public void onSuccess() {
                view.removeWait();
                view.passwordChanged(R.string.change_password_success);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }
}
