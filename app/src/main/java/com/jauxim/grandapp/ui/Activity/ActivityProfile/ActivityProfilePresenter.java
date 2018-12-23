package com.jauxim.grandapp.ui.Activity.ActivityProfile;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

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
}
