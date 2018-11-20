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

    @BindView(R.id.re_email)
    EditText email;

    @BindView(R.id.re_password)
    EditText password;

    @BindView(R.id.re_password2)
    EditText password2;

    @BindView(R.id.re_completeName)
    EditText re_completeName;

    @BindView(R.id.re_button)
    Button re_button;

    RegisterPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.register_app);
        super.onCreate(savedInstanceState);

        username = findViewById(R.id.re_username);
        email = findViewById(R.id.re_email);
        password = findViewById(R.id.re_password);
        password2 = findViewById(R.id.re_password2);
        re_completeName = findViewById(R.id.re_completeName);

        re_button = findViewById(R.id.re_button);
        re_button.setOnClickListener(this);

        presenter = new RegisterPresenter(service, this);
    }

    @Override
    public void onClick(View v) {
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
        username.setError(getString(user_error));
    }

    @Override
    public void showEmailError(int email_error) {
        removeWait();
        email.setError(getString(email_error));
    }

    @Override
    public void showPassError(int pass_error) {
        removeWait();
        password.setError(getString(pass_error));
    }

    @Override
    public void showPass2Error(int pass2_error) {
        removeWait();
        password2.setError(getString(pass2_error));
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
