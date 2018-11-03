package com.jauxim.grandapp.ui.Activity.ActivityLogin;

public interface ActivityLoginView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void showUserError();

    void showPassError();

    void showLoginError();

    void startMainActivity();
}
