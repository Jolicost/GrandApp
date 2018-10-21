package com.jauxim.grandapp.ui.Activity.Main;

import com.jauxim.grandapp.networking.Service;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by ennur on 6/25/16.
 */
public class MainPresenter {
    private final Service service;
    private final MainView view;
    private CompositeSubscription subscriptions;

    public MainPresenter(Service service, MainView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
