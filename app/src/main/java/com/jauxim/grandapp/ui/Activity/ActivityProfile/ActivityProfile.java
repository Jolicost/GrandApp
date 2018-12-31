package com.jauxim.grandapp.ui.Activity.ActivityProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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

import java.util.List;

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

    @BindView(R.id.ivSettings)
    ImageView ivSettings;

    @BindView(R.id.swNear_activity_created)
    SwitchCompat swNearActivityCreated;

    @BindView(R.id.swUser_joined_activity)
    SwitchCompat swUserJoinedActivity;

    @BindView(R.id.swJoined_activity_ended)
    SwitchCompat swJoinedActivityEnded;

    @BindView(R.id.llNotifications)
    LinearLayout llNotifications;

    @BindView(R.id.llEmail)
    LinearLayout llEmail;

    @BindView(R.id.llPhone)
    LinearLayout llPhone;

    private String profileId; //id of the user profile
    private UserModel user; //user logged

    private PopupMenu pop;
    boolean isBlocked;

    ActivityProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getDeps().inject(this);
        ButterKnife.bind(this);

        ivEdit.setVisibility(View.GONE);
        ivSettings.setVisibility(View.GONE);
        llNotifications.setVisibility(View.GONE);
        llPhone.setVisibility(View.GONE);
        llEmail.setVisibility(View.GONE);

        pop = new PopupMenu(ActivityProfile.this, ivSettings);
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuBlock:
                        if (isBlocked) {
                            presenter.unblockUser(profileId);
                        }
                        else presenter.blockUser(profileId);
                        return true;
                    default:
                        return false;
                }
            }
        });
        pop.getMenuInflater().inflate(R.menu.profile_settings, pop.getMenu());

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

    @OnClick(R.id.ivSettings)
    void settingsButtonClick() {
        pop.show();
    }

    @Override
    public void getProfileInfo(UserModel userModel) {
        user = DataUtils.getUserInfo(this); //user logged

        tvCompleteName.setText(userModel.getCompleteName());
        Glide.with(this).load(userModel.getProfilePic()).into(ivProfilePic);

        if (!TextUtils.isEmpty(userModel.getId()) && userModel.getId().equals(user.getId())){
            ivEdit.setVisibility(View.VISIBLE);
            llNotifications.setVisibility(View.VISIBLE);
            llEmail.setVisibility(View.VISIBLE);
            llPhone.setVisibility(View.VISIBLE);
            if (user.getNotifications() != null) {
                swNearActivityCreated.setChecked(user.getNotifications().getNearActivityCreated());
                swUserJoinedActivity.setChecked(user.getNotifications().getUserJoinedActivity());
                swJoinedActivityEnded.setChecked(user.getNotifications().getJoinedActivityEnded());
            }
            tvPhone.setText(userModel.getPhone());
            tvEmail.setText(userModel.getEmail());
        }
        else {
            ivSettings.setVisibility(View.VISIBLE);
            List<String> b = user.getBlocked();

            if (blocked(b, userModel.getId())) {
                isBlocked = true;
                showUnblockText();
            }
            else{
                isBlocked = false;
                showBlockText();
            }
        }
    }

    private boolean blocked(List<String> b, String userId) {
        for (int i = 0; i < b.size(); ++i){
            if (b.get(i).equals(userId)) return true;
        }
        return false;
    }

    public void showBlockText() {
        pop.getMenu().findItem(R.id.menuBlock).setTitle(getString(R.string.block));
    }

    public void showUnblockText() {
        pop.getMenu().findItem(R.id.menuBlock).setTitle(getString(R.string.unblock));
    }

    @Override
    public void editProfile() {
        Intent intent = new Intent(this, ActivityEditProfile.class);
        startActivity(intent);
    }

    @Override
    public void showBlockSuccess(int block_success) {
        Dialog.createDialog(this).title(getString(block_success)).description(getString(block_success)).build();
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.getProfileInfo(profileId);
    }
}
