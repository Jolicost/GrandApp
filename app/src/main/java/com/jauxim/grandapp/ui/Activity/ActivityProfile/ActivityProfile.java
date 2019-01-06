package com.jauxim.grandapp.ui.Activity.ActivityProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;

import android.util.Log;
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
import com.jauxim.grandapp.models.AchievementsModel;
import com.jauxim.grandapp.models.BlockModel;
import com.jauxim.grandapp.models.ChangePasswordModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityEditProfile.ActivityEditProfile;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import java.util.ArrayList;
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

    @BindView(R.id.llInfoUser)
    LinearLayout llInfoUser;

    @BindView(R.id.llBlocks)
    LinearLayout llBlocks;

    @BindView(R.id.list_blocked_contacts)
    RecyclerView blockedRV;

    private String profileId; //id of the user profile
    private UserModel user; //user logged

    private PopupMenu pop;
    private boolean isBlocked;
    private ChangePasswordDialog cpd;

    private List<BlockModel> blockList = new ArrayList<>();
    private ProfileBlocksAdapter blockAdapter;
    private ActivityProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getDeps().inject(this);
        ButterKnife.bind(this);

        ivEdit.setVisibility(View.GONE);
        ivSettings.setVisibility(View.GONE);
        llNotifications.setVisibility(View.GONE);
        llInfoUser.setVisibility(View.GONE);
        llBlocks.setVisibility(View.GONE);

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

        blockAdapter = new ProfileBlocksAdapter(ActivityProfile.this, blockList);
        blockedRV.setLayoutManager(new LinearLayoutManager(this));
        blockedRV.setItemAnimator(new DefaultItemAnimator());
        blockedRV.setAdapter(blockAdapter);

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

    @OnClick(R.id.tvChangePassword)
    void changePasswordClick() {
        cpd = new ChangePasswordDialog(this);
        cpd.show();
    }

    @Override
    public void getProfileInfo(UserModel userModel) {
        user = DataUtils.getUserInfo(this); //user logged

        tvCompleteName.setText(userModel.getCompleteName());
        Glide.with(this).load(userModel.getProfilePic()).into(ivProfilePic);

        List<String> b = user.getBlocked();

        if (!TextUtils.isEmpty(userModel.getId()) && userModel.getId().equals(user.getId())){
            ivEdit.setVisibility(View.VISIBLE);
            llNotifications.setVisibility(View.VISIBLE);
            llInfoUser.setVisibility(View.VISIBLE);
            llBlocks.setVisibility(View.VISIBLE);

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

            if (blocked(b, userModel.getId())) {
                isBlocked = true;
                showUnblockText();
            }
            else{
                isBlocked = false;
                showBlockText();
            }
        }
        presenter.getAchievements(profileId);

        blockList.clear();
        blockAdapter.notifyDataSetChanged();
        for (int i = 0; i < b.size(); ++i){
            presenter.getName(b.get(i));
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
    public void showAchievements(List<AchievementsModel> achievementsList) {
        int size = achievementsList.size();
        for (int i = 0; i < size; ++i){
            int titleId = getResources().getIdentifier("tvTitleAch"+ String.valueOf(i+1), "id", getPackageName());
            TextView tvTitleAch = findViewById(titleId);
            tvTitleAch.setText(achievementsList.get(i).getTitle());

            int imageId = getResources().getIdentifier("ivImageAch"+ String.valueOf(i+1), "id", getPackageName());
            ImageView ivImageAch = findViewById(imageId);
            Glide.with(this).load(achievementsList.get(i).getImage()).into(ivImageAch);
        }
    }

    @Override
    public void setBlockedUsers(BlockModel blockModel) {
        blockList.add(blockModel);
        blockAdapter.notifyDataSetChanged();
    }

    @Override
    public void resetErrors() {
        cpd.resetErrors();
    }

    @Override
    public void showOldPassError(int pass_error) {
        cpd.showOldPassError(getString(pass_error));
    }

    @Override
    public void showNewPassError(int pass_error) {
        cpd.showNewPassError(getString(pass_error));
    }

    @Override
    public void showNewRePassError(int pass_error) {
        cpd.showNewRePassError(getString(pass_error));
    }

    @Override
    public void showPass2Error(int pass2_error) {
        cpd.showPass2Error(getString(pass2_error));
    }

    @Override
    public void passwordChanged(int change_password_success) {
        cpd.dismiss();
        Dialog.createDialog(this).title(getString(change_password_success)).description(getString(change_password_success)).build();
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

    public void changePassword(String pOld, String pNew, String pNewRe) {
        presenter.changePassword(pOld, pNew, pNewRe);
    }
}
