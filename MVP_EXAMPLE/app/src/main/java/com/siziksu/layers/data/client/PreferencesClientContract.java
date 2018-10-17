package com.siziksu.layers.data.client;

import com.siziksu.layers.data.client.model.DateClientModel;

import io.reactivex.Single;

public interface PreferencesClientContract {

    Single<DateClientModel> getLastVisitedDate();

    void setLastVisitedDate();
}
