package com.jauxim.grandapp.ui.Activity.ActivityProfile;

import android.content.Context;
import android.text.TextUtils;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

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
}
