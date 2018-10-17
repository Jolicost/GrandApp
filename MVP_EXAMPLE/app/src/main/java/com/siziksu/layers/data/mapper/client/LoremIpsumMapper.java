package com.siziksu.layers.data.mapper.client;

import com.siziksu.layers.common.mapper.Mapper;
import com.siziksu.layers.data.client.model.LoremIpsumClientModel;
import com.siziksu.layers.data.model.LoremIpsumDataModel;

public final class LoremIpsumMapper extends Mapper<LoremIpsumClientModel, LoremIpsumDataModel> {

    @Override
    public LoremIpsumDataModel map(LoremIpsumClientModel object) {
        LoremIpsumDataModel loremIpsumDataModel = new LoremIpsumDataModel();
        loremIpsumDataModel.list.addAll(object.list);
        return loremIpsumDataModel;
    }

    @Override
    public LoremIpsumClientModel unMap(LoremIpsumDataModel mapped) {
        LoremIpsumClientModel loremIpsumClientModel = new LoremIpsumClientModel();
        loremIpsumClientModel.list.addAll(mapped.list);
        return loremIpsumClientModel;
    }
}
