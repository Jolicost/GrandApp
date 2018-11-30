package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Init.Init;
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

    @BindView(R.id.iv2)
    ImageView ivLogo;

    @BindView(R.id.textTitle)
    TextView textTitle;

    @BindView(R.id.tv2)
    TextView textWelcome;

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

        textTitle.setText(getString(R.string.login_button));
        textWelcome.setText(getString(R.string.welcomeLoginr));

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Intent intent = new Intent(this, Register.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    ivLogo,
                    ViewCompat.getTransitionName(ivLogo));
            startActivity(intent, options.toBundle());
        }else {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
    }


    @OnClick(R.id.etForgotPwd)
    public void forgotPasswordClick(){
        ForgotPasswordDialog cdd=new ForgotPasswordDialog(this, getDeps());
        cdd.show();
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
    public void showForgotPassSuccess(int forgotpsw_success) {
        removeWait();
        Dialog.createDialog(this).title(getString(forgotpsw_success)).description(getString(forgotpsw_success)).build();
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
    public void startMainActivity() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finishAffinity();
    }

    public void forgotPassword(String phone) {
        showWait();
        presenter.forgotPassword(phone);
    }
}
