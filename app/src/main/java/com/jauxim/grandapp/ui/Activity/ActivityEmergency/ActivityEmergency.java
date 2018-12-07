package com.jauxim.grandapp.ui.Activity.ActivityEmergency;

import android.content.Intent;
import android.os.Bundle;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.EmergencyContactsModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityEmergency extends BaseActivity implements ActivityEmergencyView {
    @Inject
    public Service service;

    ActivityEmergencyPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency);
        getDeps().inject(this);
        ButterKnife.bind(this);

        presenter = new ActivityEmergencyPresenter(service, this);
        presenter.getEmergencyContacts();
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

    @Override
    public void getEmergencyContacts(EmergencyContactsModel emergencyContactsModel) {
        //set a les vistes
    }

    @OnClick(R.id.ivClose)
    void closeButtonClick() {
        onBackPressed();
    }

    @OnClick(R.id.ivEdit)
    void editButtonClick() {
        //presenter.editEmergencyContacts();
    }

/*
    @Override
    public void editEmergencyContacts() {
        Intent intent = new Intent(this, ActivityEditEmergency.class);
        startActivity(intent);
    }*/
}
