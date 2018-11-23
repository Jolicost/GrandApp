package com.jauxim.grandapp.ui.Activity.Register;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Main.Main;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Register extends BaseActivity implements RegisterView {

    @Inject
    public Service service;

    @BindView(R.id.re_username)
    EditText username;

    @BindView(R.id.re_email)
    EditText email;

    @BindView(R.id.re_password)
    EditText password;

    @BindView(R.id.re_password2)
    EditText password2;

    @BindView(R.id.re_completeName)
    EditText re_completeName;

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;

    @BindView(R.id.tilUser)
    TextInputLayout tilUser;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;

    @BindView(R.id.tilPassword2)
    TextInputLayout tilPassword2;

    @BindView(R.id.tilName)
    TextInputLayout tilName;

    RegisterPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.register_app);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getDeps().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        presenter = new RegisterPresenter(service, this);
    }


    @OnClick(R.id.re_button)
    public void doRegister(){
        showWait();
        String user = username.getText().toString();
        String user_email = email.getText().toString();
        String pass = password.getText().toString();
        String pass2 = password2.getText().toString();
        String compName = re_completeName.getText().toString();
        presenter.register(user,user_email,pass,pass2,compName);
    }

    @Override
    public void showWait() {
        showProgress();
    }

    @Override
    public void removeWait() {
        hideProgress();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Dialog.createDialog(this).title("server error int act. info").description(appErrorMessage).build();
    }

    @Override
    public void showUserError(int user_error) {
        removeWait();
        tilUser.setError(getString(user_error));
    }

    @Override
    public void showEmailError(int email_error) {
        removeWait();
        tilEmail.setError(getString(email_error));
    }

    @Override
    public void showPassError(int pass_error) {
        removeWait();
        tilPassword.setError(getString(pass_error));
    }

    @Override
    public void showPass2Error(int pass2_error) {
        removeWait();
        tilPassword2.setError(getString(pass2_error));
    }

    @Override
    public void resetErrors() {
        tilUser.setError(null);
        tilEmail.setError(null);
        tilName.setError(null);
        tilPassword.setError(null);
        tilPassword2.setError(null);
    }

    @Override
    public void showRegisterError(int register_error) {
        removeWait();
        Dialog.createDialog(this).title(getString(register_error)).description(getString(register_error)).build();
    }

    @Override
    public void showRegisterSuccess(int register_success) {
        removeWait();
        Dialog.createDialog(this).title(getString(register_success)).description(getString(register_success)).build();
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}
