package com.jauxim.grandapp.ui.Activity.ActivityInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.RateModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityEdit.ActivityEditActivity;
import com.jauxim.grandapp.ui.Activity.Main.Main;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ennur on 6/25/16.
 */
public class ActivityInfoPresenter {
    private final Service service;
    private final ActivityInfoView view;
    private CompositeSubscription subscriptions;

    public ActivityInfoPresenter(Service service, ActivityInfoView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void redirect_to_chat() {
        view.startChatActivity();
    }

    public void getActivityInfo(String id) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.getActivityInfo(id, new Service.ActivityInfoCallback() {
            @Override
            public void onSuccess(ActivityModel activityModel) {
                view.removeWait();
                view.getActivityInfoSuccess(activityModel);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }

    public void editActivity(String activityId){
        Intent intent = new Intent((Context) view, ActivityEditActivity.class);
        intent.putExtra(Constants.ACTIVITY_ID, activityId);
        ((Context) view).startActivity(intent);
    }

    public void deleteActivity(String activityId) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.deleteActivity(activityId, new Service.DeleteActivityCallback() {
            @Override
            public void onSuccess() {
                view.removeWait();
                view.backToMainView();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void getProfileInfo(String id) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.getProfileInfo(id, new Service.ProfileInfoCallback() {
            @Override
            public void onSuccess(UserModel userModel) {
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

    public void viewProfile(String id) {
        view.viewProfile(id);
    }

    public void unjoin(String activityId) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.leaveActivity(activityId, new Service.UnJoinActivityCallback() {
            @Override
            public void onSuccess() {
                view.removeWait();
                view.showJoinText();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void join(String activityId) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.joinActivity(activityId, new Service.JoinActivityCallback() {
            @Override
            public void onSuccess() {
                view.removeWait();
                view.showUnjoinText();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void voteActivity(Long rate, final String activityId) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        RateModel rateModel = new RateModel(rate);
        Subscription subscription = service.voteActivity(rateModel, activityId, new Service.VoteActivityCallback() {
            @Override
            public void onSuccess() {
                getActivityInfo(activityId);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void showCapacityError() {
        view.showCapacityError(R.string.capacity_error);
    }
}
