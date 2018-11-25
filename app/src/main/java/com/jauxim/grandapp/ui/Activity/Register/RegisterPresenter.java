package com.jauxim.grandapp.ui.Activity.Register;

import android.content.Context;
import android.util.Log;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.LoginResponseModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class RegisterPresenter {

    private final Service service;
    private final RegisterView view;
    private CompositeSubscription subscriptions;

    public RegisterPresenter(Service service, RegisterView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void register(String code, String phone, String email, String pass, String pass2, String completeName) {

        view.resetErrors();

        boolean error = false;

        if (phone.isEmpty()) {
            view.showUserError(R.string.phone_error);
            error = true;
        }
        if (email.isEmpty()) {
            view.showEmailError(R.string.email_error);
            error = true;
        }
        if (pass.isEmpty() || pass2.isEmpty()) {
            view.showPassError(R.string.pass_error);
            error = true;
        } else if (!pass2.equals(pass)) {
            view.showPass2Error(R.string.pass2_error);
            error = true;
        } else if (completeName.isEmpty()){
            view.showPass2Error(R.string.name_error);
            error = true;
        }

        if (error) return;

        view.showWait();
        Service.LoginCallback registerCallback = new Service.LoginCallback(){
            @Override
            public void onSuccess(LoginResponseModel userModel) {
                DataUtils.setAuthToken((Context) view,userModel.getToken());
                Log.d("authSaving", "is token?" + userModel.isAuth()+" getted token: "+userModel.getToken());
                DataUtils.saveUserModel((Context)view, userModel.getUser());
                view.removeWait();
                view.startMainActivity();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        };
        Subscription subscription = service.postNewUser(code+phone, pass, email, completeName, registerCallback);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
