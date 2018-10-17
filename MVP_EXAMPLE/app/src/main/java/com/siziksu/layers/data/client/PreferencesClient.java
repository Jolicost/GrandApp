package com.siziksu.layers.data.client;

import com.siziksu.layers.App;
import com.siziksu.layers.common.utils.DatesUtils;
import com.siziksu.layers.data.client.model.DateClientModel;
import com.siziksu.layers.data.client.service.PreferencesService;

import javax.inject.Inject;

import io.reactivex.Single;

public final class PreferencesClient implements PreferencesClientContract {

    private static final String LAST_VISITED_DATE = "last_visited_date";

    @Inject
    PreferencesService service;

    public PreferencesClient() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public Single<DateClientModel> getLastVisitedDate() {
        DateClientModel dateClientModel = new DateClientModel();
        dateClientModel.date = service.useDefaultSharedPreferences().getValue(LAST_VISITED_DATE, DatesUtils.getCurrentTimeLong());
        return Single.just(dateClientModel);
    }

    @Override
    public void setLastVisitedDate() {
        service.useDefaultSharedPreferences().applyValue(LAST_VISITED_DATE, DatesUtils.getCurrentTimeLong());
    }
}
