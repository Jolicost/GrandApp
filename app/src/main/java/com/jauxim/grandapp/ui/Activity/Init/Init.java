package com.jauxim.grandapp.ui.Activity.Init;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityEdit.ActivityEditActivity;
import com.jauxim.grandapp.ui.Activity.ActivityLogin.ActivityLogin;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Fragment.ActiviesList.ActivitiesList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Init extends BaseActivity implements InitView {

    @Inject
    public Service service;

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
        showWait();
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
        Intent intent = new Intent(this, ActivityRegister.class);
        startActivity(intent);
    }

}
