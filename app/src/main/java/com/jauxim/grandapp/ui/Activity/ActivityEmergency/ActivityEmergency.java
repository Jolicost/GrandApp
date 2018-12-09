package com.jauxim.grandapp.ui.Activity.ActivityEmergency;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.EmergencyContactsModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityEmergency extends BaseActivity implements ActivityEmergencyView {
    @Inject
    public Service service;

    @BindView(R.id.list_emergency_contacts)
    RecyclerView emergencyContactsRV;

    private List<EmergencyContactsModel> emergencyContactsList = new ArrayList<>();
    private ActivityEmergencyAdapter emergencyAdapter;
    private ActivityEmergencyPresenter presenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency);
        getDeps().inject(this);
        ButterKnife.bind(this);

        emergencyAdapter = new ActivityEmergencyAdapter(emergencyContactsList);
        emergencyContactsRV.setLayoutManager(new LinearLayoutManager(this));
        emergencyContactsRV.setItemAnimator(new DefaultItemAnimator());
        emergencyContactsRV.setAdapter(emergencyAdapter);

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
        Dialog.createDialog(this).title("server error int act. emergency").description(appErrorMessage).build();
    }

    @Override
    public void getEmergencyContacts(List<EmergencyContactsModel> emergencyContactsModelList) {

        emergencyContactsList.clear();
        emergencyContactsList.addAll(emergencyContactsModelList);
        emergencyAdapter.notifyDataSetChanged();
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
