package com.jauxim.grandapp.ui.Activity.Init;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityEdit.ActivityEditActivity;
import com.jauxim.grandapp.ui.Activity.ActivityLogin.ActivityLogin;
import com.jauxim.grandapp.ui.Activity.ActivitySplash.SplashActivity;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Register.Register;
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

    @BindView(R.id.iv2)
    ImageView ivLogo;

    private InitPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Intent intent = new Intent(Init.this, ActivityLogin.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    Init.this,
                    ivLogo,
                    ViewCompat.getTransitionName(ivLogo));
            startActivity(intent, options.toBundle());
        }else {
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
        }
    }

    @Override
    public void startRegisterActivity() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Intent intent = new Intent(Init.this, Register.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    Init.this,
                    ivLogo,
                    ViewCompat.getTransitionName(ivLogo));
            startActivity(intent, options.toBundle());
        }else {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
    }

}
