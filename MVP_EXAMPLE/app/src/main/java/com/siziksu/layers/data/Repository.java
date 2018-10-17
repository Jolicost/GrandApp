package com.siziksu.layers.data;

import com.google.gson.Gson;
import com.siziksu.layers.App;
import com.siziksu.layers.data.client.LoremIpsumCacheClientContract;
import com.siziksu.layers.data.client.LoremIpsumClientContract;
import com.siziksu.layers.data.client.PreferencesClientContract;
import com.siziksu.layers.data.client.model.LoremIpsumClientModel;
import com.siziksu.layers.data.mapper.client.DateMapper;
import com.siziksu.layers.data.mapper.client.LoremIpsumMapper;
import com.siziksu.layers.data.model.DateDataModel;
import com.siziksu.layers.data.model.LoremIpsumDataModel;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;

public final class Repository implements RepositoryContract {

    @Inject
    PreferencesClientContract preferencesClient;
    @Inject
    LoremIpsumClientContract loremIpsumClient;
    @Inject
    @Named("LoremIpsumCacheClient")
    LoremIpsumCacheClientContract loremIpsumCacheClient;

    public Repository() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public Single<LoremIpsumDataModel> getLoremIpsum(int paragraphs, boolean usingCache) {
        if (usingCache && !loremIpsumCacheClient.isExpired() && loremIpsumCacheClient.isCached()) {
            LoremIpsumClientModel loremIpsumClientModel = new Gson().fromJson(loremIpsumCacheClient.getCachedJson(), LoremIpsumClientModel.class);
            return Single.just(new LoremIpsumMapper().map(loremIpsumClientModel));
        } else {
            return loremIpsumClient.getLoremIpsum(paragraphs).map(loremIpsumClientModel -> {
                String json = new Gson().toJson(loremIpsumClientModel);
                loremIpsumCacheClient.cacheJson(json);
                return new LoremIpsumMapper().map(loremIpsumClientModel);
            });
        }
    }

    @Override
    public Single<DateDataModel> getLastVisitedDate() {
        return preferencesClient.getLastVisitedDate().map(dateClientModel -> new DateMapper().map(dateClientModel));
    }

    @Override
    public void setLastVisitedDate() {
        preferencesClient.setLastVisitedDate();
    }
}
