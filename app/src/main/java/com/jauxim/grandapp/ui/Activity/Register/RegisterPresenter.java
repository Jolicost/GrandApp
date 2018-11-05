package com.jauxim.grandapp.ui.Activity.Register;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.networking.Service;

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

    public void register(String user, String pass, String pass2, String completeName, String age, String birthDate) {
        if (user.isEmpty()){
            view.showUserError(R.string.user_error);
            return;
        }
        if (pass.isEmpty() || pass2.isEmpty()){
            view.showPassError(R.string.pass_error);
            return;
        } else if (!pass2.equals(pass)) {
            view.showPassError(R.string.pass2_error);
            return;
        }

        //do a GET of the username (if it already exists, trow an error)
        //do a POST of the user information
        //if it returns without an error, call the login function
        //else if it return with an error trow an error
    }
}
