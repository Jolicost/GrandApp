package com.jauxim.grandapp.ui.Activity.ActivityEmergencyEdit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.EmergencyContactsModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityEmergency.ActivityEmergency;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityEmergencyEdit extends BaseActivity implements ActivityEmergencyEditView {
    @Inject
    public Service service;

    @BindView(R.id.list_emergency_edit)
    RecyclerView emergencyContactsRV;

    private List<EmergencyContactsModel> emergencyContactsList = new ArrayList<>();
    private ActivityEmergencyEditAdapter emergencyEditAdapter;
    private ActivityEmergencyEditPresenter presenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_edit);
        getDeps().inject(this);
        ButterKnife.bind(this);

        emergencyEditAdapter = new ActivityEmergencyEditAdapter(emergencyContactsList);
        emergencyContactsRV.setLayoutManager(new LinearLayoutManager(this));
        emergencyContactsRV.setItemAnimator(new DefaultItemAnimator());
        emergencyContactsRV.setAdapter(emergencyEditAdapter);

        presenter = new ActivityEmergencyEditPresenter(service, this);
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
        Dialog.createDialog(this).title("server error int act. edit emergency").description(appErrorMessage).build();
    }

    @Override
    public void getEmergencyContacts(List<EmergencyContactsModel> emergencyContactsModelList) {

        emergencyContactsList.clear();
        emergencyContactsList.addAll(emergencyContactsModelList);
        emergencyEditAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmergencyContacts() {
        Intent intent = new Intent(this, ActivityEmergency.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void showEditSuccess(int edit_emergency_success) {
        Dialog.createDialog(this).title(getString(edit_emergency_success)).description(getString(edit_emergency_success)).build();
    }

    @OnClick(R.id.ivClose)
    void closeButtonClick() {
        onBackPressed();
    }

    @OnClick(R.id.ivEdit)
    void editButtonClick() {
        //get de llista
        presenter.editEmergencyContacts(emergencyContactsList);
    }
}
