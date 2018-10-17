package com.siziksu.layers.data;

import com.siziksu.layers.data.model.DateDataModel;
import com.siziksu.layers.data.model.LoremIpsumDataModel;

import io.reactivex.Single;

public interface RepositoryContract {

    Single<LoremIpsumDataModel> getLoremIpsum(int paragraphs, boolean usingCache);

    Single<DateDataModel> getLastVisitedDate();

    void setLastVisitedDate();
}
