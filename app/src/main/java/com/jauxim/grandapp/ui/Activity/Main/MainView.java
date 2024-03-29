package com.jauxim.grandapp.ui.Activity.Main;

import android.app.Activity;
import android.content.Context;

import com.jauxim.grandapp.models.ActivityListItemModel;

import java.util.List;

/**
 * Created by ennur on 6/25/16.
 */
public interface MainView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void lockDrawer();

    void unlockDrawer();

    Activity getContext();

    void redirectTologin();

    void showLogoutSuccess(int logout_success);

    void viewProfile(String userId);

    void viewEmergencyContacts();
}
