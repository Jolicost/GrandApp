package com.siziksu.layers.data.client;

import com.siziksu.layers.App;
import com.siziksu.layers.data.client.model.LoremIpsumClientModel;
import com.siziksu.layers.data.client.service.LoremIpsumService;
import com.siziksu.layers.data.client.service.RetrofitServiceFactory;

import javax.inject.Inject;

import io.reactivex.Single;

public final class LoremIpsumClient implements LoremIpsumClientContract {

    private static final String URI = "https://baconipsum.com/";

    @Inject
    RetrofitServiceFactory retrofitServiceFactory;

    private LoremIpsumService service;

    public LoremIpsumClient() {
        App.get().getApplicationComponent().inject(this);
        service = retrofitServiceFactory.createService(LoremIpsumService.class, URI);
    }

    @Override
    public Single<LoremIpsumClientModel> getLoremIpsum(int paragraphs) {
        return service.getLoremIpsum(paragraphs).map(list -> {
            LoremIpsumClientModel loremIpsumClientModel = new LoremIpsumClientModel();
            loremIpsumClientModel.list.addAll(list);
            return loremIpsumClientModel;
        });
    }
}
