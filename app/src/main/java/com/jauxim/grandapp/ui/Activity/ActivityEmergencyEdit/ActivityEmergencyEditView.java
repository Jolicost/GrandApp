package com.jauxim.grandapp.ui.Activity.ActivityEmergencyEdit;

import com.jauxim.grandapp.models.EmergencyContactsModel;

import java.util.List;

/**
 * Created by ennur on 6/25/16.
 */
public interface ActivityEmergencyEditView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getEmergencyContacts(List<EmergencyContactsModel> emergencyContactsModel);

    void showEmergencyContacts();

    void addContact();

    void showEmptyError(int edit_empty_error);
}
