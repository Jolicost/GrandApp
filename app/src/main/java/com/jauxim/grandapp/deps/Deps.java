package com.jauxim.grandapp.deps;


import com.jauxim.grandapp.networking.NetworkModule;
import com.jauxim.grandapp.ui.Activity.ActivityList.Main;
import com.jauxim.grandapp.ui.Activity.ActivityInfo.ActivityInfo;

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
}
