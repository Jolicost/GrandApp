package com.siziksu.layers.dagger.module;

import android.content.Context;

import com.siziksu.layers.data.Repository;
import com.siziksu.layers.data.RepositoryContract;
import com.siziksu.layers.data.client.LoremIpsumCacheClientContract;
import com.siziksu.layers.data.client.LoremIpsumCacheClient;
import com.siziksu.layers.data.client.LoremIpsumClient;
import com.siziksu.layers.data.client.LoremIpsumClientContract;
import com.siziksu.layers.data.client.PreferencesClient;
import com.siziksu.layers.data.client.PreferencesClientContract;
import com.siziksu.layers.data.client.service.PreferencesService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DataModule {

    @Provides
    PreferencesService providesPreferencesService(Context context) {
        return new PreferencesService(context);
    }

    @Singleton
    @Provides
    PreferencesClientContract providesPreferencesClient() {
        return new PreferencesClient();
    }

    @Singleton
    @Provides
    LoremIpsumClientContract providesLoremIpsumClient() {
        return new LoremIpsumClient();
    }

    @Singleton
    @Provides
    @Named("LoremIpsumCacheClient")
    LoremIpsumCacheClientContract providesLoremIpsumCacheClient() {
        return new LoremIpsumCacheClient();
    }

    @Singleton
    @Provides
    RepositoryContract providesRepository() {
        return new Repository();
    }
}
