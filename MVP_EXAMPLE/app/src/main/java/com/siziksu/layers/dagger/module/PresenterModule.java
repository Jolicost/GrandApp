package com.siziksu.layers.dagger.module;

import com.siziksu.layers.presenter.main.MainPresenter;
import com.siziksu.layers.presenter.main.MainPresenterContract;
import com.siziksu.layers.presenter.main.MainViewContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class PresenterModule {

    @Singleton
    @Provides
    MainPresenterContract<MainViewContract> providesMainPresenter() {
        return new MainPresenter();
    }
}
