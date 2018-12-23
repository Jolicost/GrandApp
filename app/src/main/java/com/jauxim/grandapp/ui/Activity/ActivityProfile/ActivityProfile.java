package com.jauxim.grandapp.ui.Activity.ActivityProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityEditProfile.ActivityEditProfile;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityProfile extends BaseActivity implements ActivityProfileView{
    @Inject
    public Service service;

    @BindView(R.id.tvPhone)
    TextView tvPhone;

    @BindView(R.id.tvCompleteName)
    TextView tvCompleteName;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    @BindView(R.id.ivProfilePic)
    ImageView ivProfilePic;

    @BindView(R.id.ivEdit)
    ImageView ivEdit;

    @BindView(R.id.swNear_activity_created)
    SwitchCompat swNearActivityCreated;

    @BindView(R.id.swUser_joined_activity)
    SwitchCompat swUserJoinedActivity;

    @BindView(R.id.swJoined_activity_ended)
    SwitchCompat swJoinedActivityEnded;

    @BindView(R.id.llNotifications)
    LinearLayout llNotifications;

    private String profileId;
    private UserModel user;

    ActivityProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getDeps().inject(this);
        ButterKnife.bind(this);

        ivEdit.setVisibility(View.GONE);
        llNotifications.setVisibility(View.GONE);

        if (getIntent() != null && getIntent().getExtras() != null) {
            profileId = getIntent().getExtras().getString(Constants.PROFILE_ID);
        }

        presenter = new ActivityProfilePresenter(service, this);

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
        Dialog.createDialog(this).title("server error int act. profile").description(appErrorMessage).build();
    }

    @OnClick(R.id.ivClose)
    void closeButtonClick() {
        onBackPressed();
    }

    @OnClick(R.id.ivEdit)
    void editButtonClick() {
        presenter.editProfile();
    }

    @Override
    public void getProfileInfo(UserModel userModel) {
        user = DataUtils.getUserInfo(this); //user logged

        tvCompleteName.setText(userModel.getCompleteName());

        if (user.getNotifications() != null) {
            swNearActivityCreated.setChecked(user.getNotifications().getNearActivityCreated());
            swUserJoinedActivity.setChecked(user.getNotifications().getUserJoinedActivity());
            swJoinedActivityEnded.setChecked(user.getNotifications().getJoinedActivityEnded());
        }

        tvPhone.setText(userModel.getPhone());
        tvEmail.setText(userModel.getEmail());
        Glide.with(this).load(userModel.getProfilePic()).into(ivProfilePic);
        if (!TextUtils.isEmpty(userModel.getId()) && userModel.getId().equals(user.getId())){
            ivEdit.setVisibility(View.VISIBLE);
            llNotifications.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void editProfile() {
        Intent intent = new Intent(this, ActivityEditProfile.class);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.getProfileInfo(profileId);
    }
}
