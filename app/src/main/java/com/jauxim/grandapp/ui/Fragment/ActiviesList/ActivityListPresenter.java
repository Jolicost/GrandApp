package com.jauxim.grandapp.ui.Fragment.ActiviesList;

import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.Main.MainView;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ennur on 6/25/16.
 */
public class ActivityListPresenter {
    private final Service service;
    private final MainView view;
    private CompositeSubscription subscriptions;

    public ActivityListPresenter(Service service, MainView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getActivityList() {
        view.showWait();

        Subscription subscription = service.getActivityList(new Service.ActivityListCallback() {
            @Override
            public void onSuccess(List<ActivityListItemModel> activities) {
                view.removeWait();
                view.getActivityListSuccess(activities);
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
