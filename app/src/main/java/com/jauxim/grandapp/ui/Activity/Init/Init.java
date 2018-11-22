package com.jauxim.grandapp.ui.Activity.Init;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.networking.ServiceUser;
import com.jauxim.grandapp.ui.Activity.ActivityLogin.ActivityLogin;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Register.Register;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Init extends BaseActivity implements InitView {

    @Inject
    public ServiceUser service;

    @BindView(R.id.bLoginInit)
    Button bLoginInit;

    @BindView(R.id.bRegisterInit)
    Button bRegisterInit;

    private InitPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        getDeps().inject(this);
        ButterKnife.bind(this);

        presenter = new InitPresenter(service, this);
    }

    @OnClick(R.id.bLoginInit)
    public void loginClick(){
        presenter.redirect_to_login();
    }

    @OnClick(R.id.bRegisterInit)
    public void registerClick(){
        presenter.redirect_to_register();
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
        Dialog.createDialog(this).title("server error").description(appErrorMessage).build();
    }

    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }

    @Override
    public void startRegisterActivity() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}
