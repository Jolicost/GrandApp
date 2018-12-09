package com.jauxim.grandapp.ui.Activity.ActivityEmergencyEdit;

import android.content.Context;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.EmergencyContactsModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class ActivityEmergencyEditPresenter {
    private final Service service;
    private final ActivityEmergencyEditView view;
    private CompositeSubscription subscriptions;

    public ActivityEmergencyEditPresenter(Service service, ActivityEmergencyEditView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getEmergencyContacts() {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        String userId = DataUtils.getUserInfo((Context)view).getId();
        Subscription subscription = service.getEmergencyContacts(userId, new Service.EmergencyContactsCallback() {
            @Override
            public void onSuccess(List<EmergencyContactsModel> emergencyContactsModel) {
                view.removeWait();
                view.getEmergencyContacts(emergencyContactsModel);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void editEmergencyContacts(List<EmergencyContactsModel> emergencyContactsModel) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        String userId = DataUtils.getUserInfo((Context)view).getId();
        Subscription subscription = service.editEmergencyContacts(userId,emergencyContactsModel, new Service.EditEmergencyContactsCallback() {
            @Override
            public void onSuccess(List<EmergencyContactsModel> emergencyContactsModel) {
                view.removeWait();
                view.showEditSuccess(R.string.edit_emergency_success);
                view.showEmergencyContacts();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }
}
