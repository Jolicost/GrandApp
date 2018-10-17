package com.siziksu.layers.dagger.module;

import com.siziksu.layers.domain.main.LoremDomain;
import com.siziksu.layers.domain.main.LoremDomainContract;
import com.siziksu.layers.domain.main.LoremDomainPresenterContract;

import dagger.Module;
import dagger.Provides;

@Module
public final class DomainModule {

    @Provides
    LoremDomainContract<LoremDomainPresenterContract> providesLoremDomain() {
        return new LoremDomain();
    }
}
