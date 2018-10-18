package com.jauxim.grandapp.deps;


import com.jauxim.grandapp.ActivityInfo;
import com.jauxim.grandapp.BaseApp;
import com.jauxim.grandapp.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ennur on 6/28/16.
 */
@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(BaseApp baseApp);
}
