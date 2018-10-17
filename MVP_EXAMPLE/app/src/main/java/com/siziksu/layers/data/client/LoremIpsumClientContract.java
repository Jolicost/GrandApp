package com.siziksu.layers.data.client;

import com.siziksu.layers.data.client.model.LoremIpsumClientModel;

import io.reactivex.Single;

public interface LoremIpsumClientContract {

    Single<LoremIpsumClientModel> getLoremIpsum(int paragraphs);
}
