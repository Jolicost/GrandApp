package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.AuthModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import rx.Subscription;
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
        Subscription subscription = service.getLoginToken(userModel, new Service.LoginCallback() {
            @Override
            public void onSuccess(AuthModel authModel) {
                DataUtils.setAuthToken((Context) view,authModel.getToken());
                Log.d("authSaving", "is token?" + authModel.isAuth()+" getted token: "+authModel.getToken());
                view.removeWait();
                view.showLoginSuccess(R.string.login_success);
                view.startMainActivity();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.showLoginError(R.string.login_error);;
            }
        });

        subscriptions.add(subscription);
    }
}
