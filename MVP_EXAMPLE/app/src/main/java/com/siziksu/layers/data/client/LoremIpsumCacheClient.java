package com.siziksu.layers.data.client;

import android.text.TextUtils;

import com.siziksu.layers.App;
import com.siziksu.layers.common.utils.DatesUtils;
import com.siziksu.layers.data.client.service.PreferencesService;

import javax.inject.Inject;

public final class LoremIpsumCacheClient implements LoremIpsumCacheClientContract {

    private static final long EXPIRATION_TIME = 15 * 1000;

    private static final String LOREM_KEY = "lorem_data_model_key";
    private static final String LOREM_DATE_KEY = "lorem_data_model_cache_date_key";

    @Inject
    PreferencesService service;

    public LoremIpsumCacheClient() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void cacheJson(String json) {
        service.useDefaultSharedPreferences().applyValue(LOREM_KEY, json);
        service.useDefaultSharedPreferences().applyValue(LOREM_DATE_KEY, DatesUtils.getCurrentTimeLong());
    }

    @Override
    public String getCachedJson() {
        return service.useDefaultSharedPreferences().getValue(LOREM_KEY, "");
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = service.useDefaultSharedPreferences().getValue(LOREM_DATE_KEY, DatesUtils.getCurrentTimeLong());
        return ((currentTime - lastUpdateTime) > EXPIRATION_TIME);
    }

    @Override
    public boolean isCached() {
        String json = service.useDefaultSharedPreferences().getValue(LOREM_KEY, "");
        return !TextUtils.isEmpty(json);
    }
}
