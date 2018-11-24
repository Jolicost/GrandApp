package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hbb20.CountryCodePicker;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Main.Main;
import com.jauxim.grandapp.ui.Activity.Register.Register;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityLogin extends BaseActivity implements ActivityLoginView {

    @Inject
    public Service service;

    @BindView(R.id.etPhoneNUmber)
    EditText etPhoneNUmber;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.tilPhoneNUmber)
    TextInputLayout tilPhoneNUmber;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;

    ActivityLoginPresenter presenter;

    @BindView(R.id.ccp)
    CountryCodePicker ccp;

    @BindView(R.id.llInputContainer)
    LinearLayout llInputContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            llInputContainer.setTransitionGroup(true);
        }
        getDeps().inject(this);
        presenter = new ActivityLoginPresenter(service, this);
    }

    @OnClick(R.id.bLogin)
    public void loginClick(){
        if (etPhoneNUmber != null && etPassword != null) {
            String code = ccp.getSelectedCountryCodeWithPlus();
            String user = etPhoneNUmber.getText().toString();
            String pass = etPassword.getText().toString();
            showWait();
            presenter.login(code, user, pass);
        }
    }

    @OnClick(R.id.tvRegister)
    public void registerClick(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
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
        tilPhoneNUmber.setError(getString(user_error));
    }

    @Override
    public void resetErrors(){
        tilPassword.setError(null);
        tilPhoneNUmber.setError(null);
    }

    @Override
    public void showPassError(int pass_error) {
        removeWait();
        tilPassword.setError(getString(pass_error));
    }

    @Override
    public void showLoginError(int login_error) {
        removeWait();
        Dialog.createDialog(this).title(getString(login_error)).description(getString(login_error)).build();
    }

    @Override
    public void showLoginSuccess(int login_success) {
        removeWait();
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finishAffinity();
    }
}
