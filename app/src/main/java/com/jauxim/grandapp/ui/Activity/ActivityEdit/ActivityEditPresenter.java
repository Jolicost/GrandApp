package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ActivityEditPresenter {
    private final Service service;
    private final ActivityEditView view;
    private CompositeSubscription subscriptions;

    public ActivityEditPresenter(Service service, ActivityEditView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void createActivityInfo(ActivityModel activityInfo) {
        view.showWait();

        Subscription subscription = service.createActivityInfo(activityInfo, new Service.ActivityInfoCallback() {
            @Override
            public void onSuccess(ActivityModel activityModel) {
                view.removeWait();
                view.createActivityInfoSuccess(activityModel);
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
