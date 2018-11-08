package com.jauxim.grandapp.ui.Activity.Register;

public interface RegisterView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void showUserError(int user_error);

    void showPassError(int pass_error);

    void showRegisterError(int login_error);

    void startMainActivity();

    void showRegisterSuccess(int login_success);

    void showEmailError(int email_error);

    void showPass2Error(int pass2_error);
}
