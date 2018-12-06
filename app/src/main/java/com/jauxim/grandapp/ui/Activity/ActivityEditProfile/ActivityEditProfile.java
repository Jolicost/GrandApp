package com.jauxim.grandapp.ui.Activity.ActivityEditProfile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityProfile.ActivityProfile;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityEditProfile extends BaseActivity implements ActivityEditProfileView {
    @Inject
    public Service service;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.etCompleteName)
    EditText etCompleteName;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.ivProfilePic)
    ImageView ivProfilePic;

    @BindView(R.id.ivEdit)
    ImageView ivEdit;

    ActivityEditProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        getDeps().inject(this);
        ButterKnife.bind(this);

        presenter = new ActivityEditProfilePresenter(service, this);
        presenter.getProfileInfo();
    }

    @OnClick(R.id.ivClose)
    void closeButtonClick() {
        onBackPressed();
    }

    @OnClick(R.id.ivEdit)
    void editButtonClick() {
        UserModel userModel = new UserModel();
        userModel.setCompleteName(etCompleteName.getText().toString());
        userModel.setEmail(etEmail.getText().toString());
        userModel.setPhone(etPhone.getText().toString());
        //userModel.setProfilePic();
        presenter.editProfile(userModel);
    }

    @OnClick(R.id.ivProfilePic)
    void profilePictureClick() {


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
        Dialog.createDialog(this).title("server error int act. edit profile").description(appErrorMessage).build();
    }

    @Override
    public void showInfoUser(UserModel user) {
        etCompleteName.setText(user.getCompleteName());
        etPhone.setText(user.getPhone());
        etEmail.setText(user.getEmail());
        Glide.with(this).load(user.getProfilePic()).into(ivProfilePic);
    }

    @Override
    public void showLoginError(int edit_profile_success) {
        Dialog.createDialog(this).title(getString(edit_profile_success)).description(getString(edit_profile_success)).build();
    }

    @Override
    public void getProfileInfo(String userId) {
        Intent intent = new Intent(this, ActivityProfile.class);
        intent.putExtra(Constants.PROFILE_ID, userId);
        startActivity(intent);
        finishAffinity();
    }

}
