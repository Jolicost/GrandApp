package com.jauxim.grandapp.deps;


import com.jauxim.grandapp.networking.NetworkModule;
import com.jauxim.grandapp.ui.Activity.ActivityEdit.ActivityEditActivity;
import com.jauxim.grandapp.ui.Activity.ActivityLogin.ActivityLogin;
import com.jauxim.grandapp.ui.Activity.ActivityLogin.ForgotPasswordDialog;
import com.jauxim.grandapp.ui.Activity.Init.Init;
import com.jauxim.grandapp.ui.Activity.Main.Main;
import com.jauxim.grandapp.ui.Activity.ActivityInfo.ActivityInfo;
import com.jauxim.grandapp.ui.Activity.Register.Register;
import com.jauxim.grandapp.ui.Fragment.ActiviesList.ActivitiesList;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ennur on 6/28/16.
 */
@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(Main activityInfo);
    void inject(ActivityInfo activityInfo);
    void inject(ActivitiesList activityInfo);
    void inject(ActivityEditActivity activityEditActivity);
    void inject(ActivityLogin activityLogin);
    void inject(Register register);
    void inject(Init init);
    void inject(ForgotPasswordDialog forgotPasswordDialog);
}
