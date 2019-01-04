package com.jauxim.grandapp.ui.Activity.Main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.models.FilterActivityModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityEdit.ActivityEditActivity;
import com.jauxim.grandapp.ui.Activity.ActivityEmergency.ActivityEmergency;
import com.jauxim.grandapp.ui.Activity.ActivityLogin.ForgotPasswordDialog;
import com.jauxim.grandapp.ui.Activity.ActivityProfile.ActivityProfile;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Init.Init;
import com.jauxim.grandapp.ui.Fragment.ActiviesList.ActivitiesList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.mobiwise.materialintro.prefs.PreferencesManager;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.jauxim.grandapp.Constants.ACTIVITY_ALL;
import static com.jauxim.grandapp.Constants.ACTIVITY_MINE;
import static com.jauxim.grandapp.Constants.INTRO_FOCUS_FILTER_ACT;
import static com.jauxim.grandapp.Constants.INTRO_FOCUS_MENU;
import static com.jauxim.grandapp.Constants.INTRO_FOCUS_NEW_ACT;
import static com.jauxim.grandapp.Utils.Utils.getToolbarNavigationIcon;

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

    private FilterActivityModel filterActivities;
    private ActivitiesList activitiesFragment;
    private FilterDialog filterDialog;

    MaterialIntroView materialIntroView;

    private View filter_menu;
    private View hamburger_menu;

    ActionBarDrawerToggle toggle;

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
        initHelpIfNeeded();
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

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        MenuItem item = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(item);

        View headerLayout = navigationView.getHeaderView(0); // 0-index header
        setUserInfo(headerLayout);

        hamburger_menu = getToolbarNavigationIcon(toolbar);
    }


    public void setUserInfo(View header) {
        if (user != null) {
            TextView tvName = header.findViewById(R.id.tvUserName);
            TextView tvInfo = header.findViewById(R.id.tvUserInfo);
            CircleImageView civ = header.findViewById(R.id.ivUserImage);

            if (tvName != null)
                tvName.setText(user.getCompleteName());

            if (tvInfo != null)
                tvInfo.setText(user.getPoints()+" "+getString(R.string.points));

            if (civ != null)
                Glide.with(this).load(user.getProfilePic()).into(civ);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_menu, menu);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                filter_menu = findViewById(R.id.filter);
            }
        });

        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.filter){
            if (filterDialog==null)
                filterDialog = new FilterDialog(this);
            filterDialog.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        } else if (id==R.id.help){
            openGuide();
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

        activitiesFragment =  ActivitiesList.newInstance("", mode, filterActivities);
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                //.setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .replace(R.id.contain_main, activitiesFragment, ActivitiesList.TAG)
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

    public void updateFilter(FilterActivityModel filter){
        this.filterActivities = filter;
        if (activitiesFragment!=null && activitiesFragment.isAdded()){
            activitiesFragment.setFilter(filterActivities);
        }
    }

    private void openGuide(){
        if (materialIntroView != null)
            materialIntroView.dismiss();

        showIntro(hamburger_menu, INTRO_FOCUS_MENU, getStrings(R.string.guide_menu_title, R.string.guide_menu_description), ShapeType.RECTANGLE);
    }

    public void showIntro(View view, final String id, String text, ShapeType shapeType) {

        new PreferencesManager(this).resetAll();

        if (materialIntroView != null)
            materialIntroView.dismiss();

        materialIntroView = new MaterialIntroView.Builder(this)
                .enableDotAnimation(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.ALL)
                .enableFadeAnimation(true)
                .performClick(false)
                .setShape(shapeType)
                .setDelayMillis(200)
                .setInfoText(text)
                .setTarget(view)
                .enableIcon(true)
                .setUsageId(id) //THIS SHOULD BE UNIQUE ID
                .show();

        materialIntroView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    materialIntroView.dismiss();
                    onUserClicked(id);
                }
                return false;
            }
        });

    }

    public void onUserClicked(String materialIntroViewId) {
        if (materialIntroViewId == INTRO_FOCUS_MENU)
            showIntro(fab, INTRO_FOCUS_NEW_ACT, getStrings(R.string.guide_create_title, R.string.guide_create_description), ShapeType.CIRCLE);
        else if (materialIntroViewId == INTRO_FOCUS_NEW_ACT)
            showIntro(filter_menu, INTRO_FOCUS_FILTER_ACT, getStrings(R.string.guide_filter_title, R.string.guide_filter_description), ShapeType.RECTANGLE);
        else if (materialIntroViewId == INTRO_FOCUS_FILTER_ACT)
            materialIntroView.dismiss();
    }

    private String getStrings(int id1, int id2) {
        return getString(id1) + "\n\n" + getString(id2);
    }

    private void initHelpIfNeeded(){
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean(Constants.NEW_USER)){
                openGuide();
            }
        }
    }
}
