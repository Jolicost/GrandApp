package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.networking.Service;

import rx.subscriptions.CompositeSubscription;

public class ActivityLoginPresenter {

    private final Service service;
    private final ActivityLoginView view;
    private CompositeSubscription subscriptions;

    public ActivityLoginPresenter(Service service, ActivityLoginView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void login(String user, String pass) {
        if (user.isEmpty()){
            view.showUserError(R.string.user_error);
            return;
        }
        if (pass.isEmpty()){
            view.showPassError(R.string.pass_error);
            return;
        }
        //boolean correct = x.login(user,pass);
        boolean correct = (user.equals("raul")&& pass.equals("1234"));
        if (correct) {
            view.showLoginSuccess(R.string.login_success);
            view.startMainActivity();
            return;
        }
        view.showLoginError(R.string.login_error);
    }
}
