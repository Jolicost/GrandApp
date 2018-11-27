package com.jauxim.grandapp.ui.Activity.ActivityInfo;

import android.content.Context;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

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

    public void deleteActivity(String activityId) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.deleteActivity(activityId, new Service.DeleteActivityCallback() {
            @Override
            public void onSuccess(String s) {
                view.removeWait();
                view.showDeleteSuccess(R.string.delete_success);
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
}
