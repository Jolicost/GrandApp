package com.jauxim.grandapp.ui.Activity.ActivityEmergency;

import android.content.Context;

import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.EmergencyContactsModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class ActivityEmergencyPresenter {
    private final Service service;
    private final ActivityEmergencyView view;
    private CompositeSubscription subscriptions;

    public ActivityEmergencyPresenter(Service service, ActivityEmergencyView view) {
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

    public void editEmergencyContacts() {
        view.editEmergencyContacts();
    }
}
