package com.siziksu.layers.domain.mapper;

import com.siziksu.layers.common.mapper.Mapper;
import com.siziksu.layers.data.model.LoremIpsumDataModel;
import com.siziksu.layers.domain.model.LoremIpsumDomainModel;

public final class LoremIpsumMapper extends Mapper<LoremIpsumDataModel, LoremIpsumDomainModel> {

    @Override
    public LoremIpsumDomainModel map(LoremIpsumDataModel object) {
        LoremIpsumDomainModel loremIpsumDomainModel = new LoremIpsumDomainModel();
        loremIpsumDomainModel.list.addAll(object.list);
        return loremIpsumDomainModel;
    }

    @Override
    public LoremIpsumDataModel unMap(LoremIpsumDomainModel mapped) {
        LoremIpsumDataModel loremIpsumDataModel = new LoremIpsumDataModel();
        loremIpsumDataModel.list.addAll(mapped.list);
        return loremIpsumDataModel;
    }
}
