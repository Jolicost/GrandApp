package com.jauxim.grandapp.ActivityInfo;

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

    public void getActivityInfo() {
        view.showWait();

        Subscription subscription = service.getActivityInfo(new Service.ActivityInfoCallback() {
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

        });

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
