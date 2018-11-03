package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Main.Main;

import javax.inject.Inject;

import butterknife.BindView;

import static android.widget.Toast.LENGTH_SHORT;

public class ActivityLogin extends BaseActivity implements View.OnClickListener, ActivityLoginView {

    @Inject
    public Service service;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.loginbutton)
    Button loginbutton;

    ActivityLoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(this);

        presenter = new ActivityLoginPresenter(service, this);
    }

    @Override
    public void onClick(View v) {
        showWait();
        String user = username.getText().toString();
        String pass = password.getText().toString();
        presenter.login(user,pass);
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
    public void showUserError() {
        removeWait();
        username.setError("Enter username");
    }

    @Override
    public void showPassError() {
        removeWait();
        password.setError("Enter password");
    }

    @Override
    public void showLoginError() {
        removeWait();
        Toast.makeText(this, "Login failed", LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        removeWait();
        Toast.makeText(this, "Login Success!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}
