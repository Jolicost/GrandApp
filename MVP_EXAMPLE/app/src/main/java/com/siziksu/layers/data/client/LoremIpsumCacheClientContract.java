package com.siziksu.layers.data.client;

public interface LoremIpsumCacheClientContract {

    void cacheJson(String json);

    String getCachedJson();

    boolean isExpired();

    boolean isCached();
}
