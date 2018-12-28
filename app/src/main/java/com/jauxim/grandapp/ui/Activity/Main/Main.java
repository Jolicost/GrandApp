package com.jauxim.grandapp.ui.Activity.Main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityEdit.ActivityEditActivity;
import com.jauxim.grandapp.ui.Activity.ActivityEmergency.ActivityEmergency;
import com.jauxim.grandapp.ui.Activity.ActivityProfile.ActivityProfile;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Init.Init;
import com.jauxim.grandapp.ui.Fragment.ActiviesList.ActivitiesList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.jauxim.grandapp.Constants.ACTIVITY_ALL;
import static com.jauxim.grandapp.Constants.ACTIVITY_MINE;

public class Main extends BaseActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    public Service service;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private MainPresenter presenter;

    private UserModel user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDeps().inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        presenter = new MainPresenter(service, this);
        presenter.updateLocation();
        chargeUserWithLogout();
        setUp();
    }

    public void chargeUserWithLogout() {
        user = DataUtils.getUserInfo(getContext());
        if (user == null) presenter.logout();
    }

    private void setUp() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, ActivityEditActivity.class);
                startActivity(intent);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        MenuItem item = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(item);

        View headerLayout = navigationView.getHeaderView(0); // 0-index header
        setUserInfo(headerLayout);
    }


    public void setUserInfo(View header) {
        if (user != null) {
            TextView tvName = header.findViewById(R.id.tvUserName);
            TextView tvInfo = header.findViewById(R.id.tvUserInfo);
            CircleImageView civ = header.findViewById(R.id.ivUserImage);

            if (tvName != null)
                tvName.setText(user.getCompleteName());

            if (tvInfo != null)
                tvInfo.setText(user.getPassword());

            if (civ != null)
                Glide.with(this).load(user.getProfilePic()).into(civ);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        if (id == R.id.nav_activities) {
            showActivitiesListFragment(ACTIVITY_ALL);
        } else if (id == R.id.account_settings) {
            showProfile();
        } else if (id == R.id.my_activities) {
            showActivitiesListFragment(ACTIVITY_MINE);
        }else if (id == R.id.emergency_contacts) {
            showEmergencyContacts();
        } else if (id == R.id.logout) {
            presenter.logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showEmergencyContacts() {
        presenter.showEmergencyContacts();
    }

    private void showProfile() {
        presenter.showProfile();
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
    public void getActivityListSuccess(List<ActivityListItemModel> activities) {
        //TODO: inflate the list
    }

    @Override
    protected void onResume() {
        super.onResume();
        unlockDrawer();
    }

    @Override
    public void lockDrawer() {
        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer() {
        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void redirectTologin() {
        Intent intent = new Intent(this, Init.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void showLogoutSuccess(int logout_success) {
        removeWait();
    }

    @Override
    public void viewProfile(String userId) {
        Intent intent = new Intent(this, ActivityProfile.class);
        intent.putExtra(Constants.PROFILE_ID, userId);
        startActivity(intent);
    }

    @Override
    public void viewEmergencyContacts() {
        Intent intent = new Intent(this, ActivityEmergency.class);
        startActivity(intent);
    }

    public void showActivitiesListFragment(String mode) {
        Log.d("listActivities", "setting fragment");

        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                //.setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .replace(R.id.contain_main, ActivitiesList.newInstance("", mode), ActivitiesList.TAG)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("checkLocation", "onRequestPermissionsResult");
        switch (requestCode) {
            case 1234: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (presenter != null)
                        presenter.updateLocation();

                } else {
                    Log.d("checkLocation", "permission not granted:");
                }
                return;
            }
        }
    }

    public void updateLocation() {
        if (presenter != null)
            presenter.updateLocation();
    }
}
