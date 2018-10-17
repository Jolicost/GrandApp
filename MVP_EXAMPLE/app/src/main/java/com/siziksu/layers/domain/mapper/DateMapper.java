package com.siziksu.layers.domain.mapper;

import com.siziksu.layers.common.mapper.Mapper;
import com.siziksu.layers.data.model.DateDataModel;
import com.siziksu.layers.domain.model.DateDomainModel;

public final class DateMapper extends Mapper<DateDataModel, DateDomainModel> {

    @Override
    public DateDomainModel map(DateDataModel object) {
        DateDomainModel dateDomainModel = new DateDomainModel();
        dateDomainModel.date = object.date;
        return dateDomainModel;
    }

    @Override
    public DateDataModel unMap(DateDomainModel mapped) {
        DateDataModel dateDataModel = new DateDataModel();
        dateDataModel.date = mapped.date;
        return dateDataModel;
    }
}
