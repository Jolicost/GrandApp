package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Main.Main;
import com.jauxim.grandapp.ui.Activity.Register.Register;
import com.jauxim.grandapp.ui.Activity.Register.RegisterPresenter;
import com.jauxim.grandapp.ui.Activity.Register.RegisterView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_SHORT;

public class ActivityLogin extends BaseActivity implements ActivityLoginView {

    @Inject
    public Service service;

    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.bLogin)
    Button bLogin;

    @BindView(R.id.bRegister)
    Button bRegister;

    ActivityLoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getDeps().inject(this);
        presenter = new ActivityLoginPresenter(service, this);
    }

    @OnClick(R.id.bLogin)
    public void loginClick(){
        if (etUsername != null && etPassword != null) {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();
            showWait();
            presenter.login(user, pass);
        }
    }

    @OnClick(R.id.bRegister)
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
        etUsername.setError(getString(user_error));
    }

    @Override
    public void showPassError(int pass_error) {
        removeWait();
        etPassword.setError(getString(pass_error));
    }

    @Override
    public void showLoginError(int login_error) {
        removeWait();
        Dialog.createDialog(this).title(getString(login_error)).description(getString(login_error)).build();
    }

    @Override
    public void showLoginSuccess(int login_success) {
        removeWait();
        Dialog.createDialog(this).title(getString(login_success)).description(getString(login_success)).build();
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}
