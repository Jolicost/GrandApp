package com.jauxim.grandapp.ui.Activity.Init;

import android.app.Activity;

import com.jauxim.grandapp.models.ActivityListItemModel;

import java.util.List;

/**
 * Created by ennur on 6/25/16.
 */
public interface InitView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void startLoginActivity();

    void startRegisterActivity();
}
