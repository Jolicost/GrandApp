package com.jauxim.grandapp.ui.Activity.Init;

import com.jauxim.grandapp.networking.ServiceUser;

import rx.subscriptions.CompositeSubscription;

public class InitPresenter {
    private final ServiceUser service;
    private final InitView view;
    private CompositeSubscription subscriptions;

    public InitPresenter(ServiceUser service, InitView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void redirect_to_login() {
        view.startLoginActivity();
    }

    public void redirect_to_register() {
        view.startRegisterActivity();
    }
}
