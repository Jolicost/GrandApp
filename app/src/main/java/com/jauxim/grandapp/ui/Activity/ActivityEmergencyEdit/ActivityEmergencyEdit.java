package com.jauxim.grandapp.ui.Activity.ActivityEmergencyEdit;

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
        onBackPressed();
    }

    @OnClick(R.id.ivClose)
    void closeButtonClick() {
        onBackPressed();
    }

    @OnClick(R.id.ivEdit)
    void editButtonClick() {
        if (empty_params()) {
            presenter.showEmptyError();
        }
        else presenter.editEmergencyContacts(emergencyContactsList);
    }

    private boolean empty_params() {
        int n = emergencyContactsList.size();
        for (int i = 0; i < n; ++i){
            if (emergencyContactsList.get(i).getAlias().isEmpty() || emergencyContactsList.get(i).getPhone().isEmpty()){
                return true;
            }
        }
        return false;
    }

    @OnClick(R.id.add_contact)
    void addContactClick() {
        presenter.addContact();
    }

    @Override
    public void addContact() {
        EmergencyContactsModel ecm = new EmergencyContactsModel("","");
        emergencyContactsList.add(ecm);
        emergencyEditAdapter.notifyItemInserted(emergencyContactsList.size()-1);
    }

    @Override
    public void showEmptyError(int edit_empty_error) {
        Dialog.createDialog(this).title(getString(edit_empty_error)).description(getString(edit_empty_error)).build();
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.getEmergencyContacts();
    }
}
