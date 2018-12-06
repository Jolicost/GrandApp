package com.jauxim.grandapp.ui.Activity.ActivityEditProfile;

import android.content.Context;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class ActivityEditProfilePresenter {
    private final Service service;
    private final ActivityEditProfileView view;
    private CompositeSubscription subscriptions;

    public ActivityEditProfilePresenter(Service service, ActivityEditProfileView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void editProfile(UserModel userModel) {
        view.showWait();
        String userId = DataUtils.getUserInfo((Context)view).getId();
        userModel.setId(userId);
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.editProfileInfo(userModel, new Service.EditProfileCallback() {
            @Override
            public void onSuccess(UserModel userModel) {
                DataUtils.saveUserModel((Context)view, userModel);
                view.removeWait();
                view.showLoginError(R.string.edit_profile_success);
                view.getProfileInfo(userModel.getId());
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void getProfileInfo() {
        UserModel user = DataUtils.getUserInfo((Context)view);
        view.showInfoUser(user);
    }

}
