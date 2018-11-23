package com.jauxim.grandapp.ui.Activity.Register;

import com.jauxim.grandapp.R;
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

    public void register(String user, String email, String pass, String pass2, String completeName) {

        view.resetErrors();

        boolean error = false;

        if (user.isEmpty()) {
            view.showUserError(R.string.user_error);
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
        }

        if (error) return;

        view.showWait();
        Service.RegisterCallback registerCallback = new Service.RegisterCallback(){
            @Override
            public void onSuccess(UserModel userModel) {
                view.removeWait();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        };
        Subscription subscription = service.postNewUser(user, pass, email, registerCallback);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
