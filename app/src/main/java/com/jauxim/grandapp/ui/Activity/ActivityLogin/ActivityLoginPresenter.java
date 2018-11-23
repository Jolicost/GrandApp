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

    public void login(String code, String phone, String pass) {
        view.resetErrors();

        boolean error = false;
        if (phone.isEmpty()) {
            view.showUserError(R.string.user_error);
            error = true;
        }
        if (pass.isEmpty()){
            view.showPassError(R.string.pass_error);
            error = true;
        }

        if (!error) {
            getAuthToken(code+phone,pass);
        }
    }

    public void getAuthToken(String username, String password) {
        view.showWait();
        UserModel userModel = new UserModel(username, password);
        Log.i("Username = ", username);
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
