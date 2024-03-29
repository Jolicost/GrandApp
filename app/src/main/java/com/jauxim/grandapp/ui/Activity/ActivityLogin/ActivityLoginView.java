package com.jauxim.grandapp.ui.Activity.ActivityLogin;

public interface ActivityLoginView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void showUserError(int user_error);

    void showPassError(int pass_error);

    void showLoginError(int login_error);

    void startMainActivity(boolean newUser);

    void resetErrors();

    void showForgotPassSuccess(int forgotpsw_success);
}
