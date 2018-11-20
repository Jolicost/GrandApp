package com.jauxim.grandapp.ui.Activity.Init;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.SingleShotLocationProvider;
import com.jauxim.grandapp.networking.Service;

import rx.subscriptions.CompositeSubscription;

public class InitPresenter {
    private final Service service;
    private final InitView view;
    private CompositeSubscription subscriptions;

    public InitPresenter(Service service, InitView view) {
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
