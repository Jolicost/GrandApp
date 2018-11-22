package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import android.content.Context;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.AuthModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.ServiceUser;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ActivityLoginPresenter {

    private final ServiceUser service;
    private final ActivityLoginView view;
    private CompositeSubscription subscriptions;

    public ActivityLoginPresenter(ServiceUser service, ActivityLoginView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void login(String user, String pass) {
        if (user.isEmpty() && pass.isEmpty()) {
            view.showUserError(R.string.user_error);
            view.showPassError(R.string.pass_error);
        }
        else if (user.isEmpty()) view.showUserError(R.string.user_error);
        else if (pass.isEmpty()) view.showPassError(R.string.pass_error);
        else {
            getAuthToken(user,pass);
        }
    }

    public void getAuthToken(String username, String password) {
        view.showWait();
        UserModel userModel = new UserModel(username, password);
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.getLoginToken(userModel, new ServiceUser.LoginCallback() {
            @Override
            public void onSuccess(AuthModel authModel) {
                DataUtils.setAuthToken((Context) view,authModel.getAuthToken());
                view.removeWait();
                view.showLoginSuccess(R.string.login_success);
                view.startMainActivity();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.showLoginError(R.string.login_error);;
            }
        },auth);

        subscriptions.add(subscription);
    }
}
