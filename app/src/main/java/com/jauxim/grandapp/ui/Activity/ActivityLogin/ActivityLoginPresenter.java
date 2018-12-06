package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import android.content.Context;
import android.util.Log;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.LoginResponseModel;
import com.jauxim.grandapp.models.PhoneModel;
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
            view.showUserError(R.string.phone_error);
            error = true;
        }
        if (pass.isEmpty()){
            view.showPassError(R.string.pass_error);
            error = true;
        }

        if (!error) {
            doLogin(code+phone,pass);
        }
    }

    public void doLogin(String username, String password) {
        view.showWait();
        UserModel userModel = new UserModel(username, password);
        Log.i("Username = ", username);
        Subscription subscription = service.getLoginToken(userModel, new Service.LoginCallback() {
            @Override
            public void onSuccess(LoginResponseModel loginResponseModel) {
                DataUtils.setAuthToken((Context) view,loginResponseModel.getToken());
                Log.d("authSaving", "is token?" + loginResponseModel.isAuth()+" getted token: "+loginResponseModel.getToken());
                DataUtils.saveUserModel((Context)view, loginResponseModel.getUser());
                view.removeWait();
                view.startMainActivity();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.showLoginError(R.string.login_error);
            }
        });

        subscriptions.add(subscription);
    }

    public void forgotPassword(String phone, String code) {
        PhoneModel phoneModel = new PhoneModel(code+phone);
        Subscription subscription = service.forgotPassword(phoneModel, new Service.ForgotPasswordCallback() {
            @Override
            public void onSuccess() {
                view.removeWait();
                view.showForgotPassSuccess(R.string.forgotpsw_success);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }
        });

        subscriptions.add(subscription);
    }
}
