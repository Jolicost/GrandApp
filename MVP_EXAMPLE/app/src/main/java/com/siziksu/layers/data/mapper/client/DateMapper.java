package com.siziksu.layers.data.mapper.client;

import com.siziksu.layers.common.mapper.Mapper;
import com.siziksu.layers.data.client.model.DateClientModel;
import com.siziksu.layers.data.model.DateDataModel;

public final class DateMapper extends Mapper<DateClientModel, DateDataModel> {

    @Override
    public DateDataModel map(DateClientModel object) {
        DateDataModel dateDataModel = new DateDataModel();
        dateDataModel.date = object.date;
        return dateDataModel;
    }

    @Override
    public DateClientModel unMap(DateDataModel mapped) {
        DateClientModel dateClientModel = new DateClientModel();
        dateClientModel.date = mapped.date;
        return dateClientModel;
    }
}
