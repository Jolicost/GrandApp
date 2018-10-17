package com.siziksu.layers.dagger.module;

import android.content.Context;

import com.siziksu.layers.App;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    private final App application;

    public ApplicationModule(App application) {
        this.application = application;
    }

    @Provides
    Context providesContext() {
        return application.getApplicationContext();
    }
}
