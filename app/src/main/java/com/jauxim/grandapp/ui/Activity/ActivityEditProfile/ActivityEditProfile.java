package com.jauxim.grandapp.ui.Activity.ActivityEditProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.Utils.Utils;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityProfile.ActivityProfile;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
    CircleImageView ivProfilePic;

    @BindView(R.id.ivEdit)
    ImageView ivEdit;

    @BindView(R.id.swNear_activity_created)
    SwitchCompat swNear_activity_created;

    @BindView(R.id.swUser_joined_activity)
    SwitchCompat swUser_joined_activity;

    @BindView(R.id.swJoined_activity_ended)
    SwitchCompat swJoined_activity_ended;

    ActivityEditProfilePresenter presenter;
    private String base64Image;

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
        showWait();
        if (!TextUtils.isEmpty(base64Image)){
            presenter.postImage(base64Image);
        }
        else callEditProfile(base64Image);
    }

    @OnClick(R.id.ivProfilePic)
    void profilePictureClick() {
        Utils.createCropCamera(true).start(ActivityEditProfile.this);
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
        //Glide.with(this).load(user.getProfilePic()).into(ivProfilePic);
        Glide.with(this)
                .load(user.getProfilePic())
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(ivProfilePic);

        if (user.getNotifications() != null) {
            swNear_activity_created.setChecked(user.getNotifications().getNearActivityCreated());
            swUser_joined_activity.setChecked(user.getNotifications().getUserJoinedActivity());
            swJoined_activity_ended.setChecked(user.getNotifications().getJoinedActivityEnded());
        }
    }

    @Override
    public void getProfileInfo() {
        onBackPressed();
    }

    @Override
    public void callEditProfile(String imageUrl) {
        UserModel userModel = new UserModel();
        userModel.setCompleteName(etCompleteName.getText().toString());
        userModel.setEmail(etEmail.getText().toString());
        userModel.setPhone(etPhone.getText().toString());
        userModel.setProfilePic(imageUrl);
        userModel.getNotifications().setNearActivityCreated(swNear_activity_created.isChecked());
        userModel.getNotifications().setUserJoinedActivity(swUser_joined_activity.isChecked());
        userModel.getNotifications().setJoinedActivityEnded(swJoined_activity_ended.isChecked());
        presenter.editProfile(userModel);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            com.theartofdev.edmodo.cropper.CropImage.ActivityResult result = com.theartofdev.edmodo.cropper.CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = Utils.getBitmapFromUri(this, result.getUri());
                    base64Image = Utils.getBase64(bitmap);
                    ivProfilePic.setImageBitmap(bitmap);

                } catch (Exception e) {
                }
            } else if (resultCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            }
        }
    }

}
