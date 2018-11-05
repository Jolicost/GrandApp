package com.jauxim.grandapp.ui.Activity.Register;

import android.content.Intent;
import android.os.Bundle;
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

public class Register extends BaseActivity implements View.OnClickListener, RegisterView {

    @Inject
    public Service service;

    @BindView(R.id.re_username)
    EditText username;

    @BindView(R.id.re_password)
    EditText password;

    @BindView(R.id.re_password2)
    EditText password2;

    @BindView(R.id.re_button)
    Button re_button;

    @BindView(R.id.re_completeName)
    EditText re_completeName;

    @BindView(R.id.re_age)
    EditText re_age;

    @BindView(R.id.re_birth)
    EditText re_birth;

    RegisterPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_app);

        username = findViewById(R.id.re_username);
        password = findViewById(R.id.re_password);
        password2 = findViewById(R.id.re_password2);
        re_completeName = findViewById(R.id.re_completeName);
        re_age = findViewById(R.id.re_age);
        re_birth = findViewById(R.id.re_birth);

        re_button = findViewById(R.id.re_button);
        re_button.setOnClickListener(this);

        presenter = new RegisterPresenter(service, this);
    }

    @Override
    public void onClick(View v) {
        showWait();
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String pass2 = password2.getText().toString();
        String compName = re_completeName.getText().toString();
        String age = re_age.getText().toString();
        String birthdate = re_birth.getText().toString();
        presenter.register(user,pass,pass2, compName, age, birthdate);
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
        username.setError(getString(user_error));
    }

    @Override
    public void showPassError(int pass_error) {
        removeWait();
        password.setError(getString(pass_error));
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
