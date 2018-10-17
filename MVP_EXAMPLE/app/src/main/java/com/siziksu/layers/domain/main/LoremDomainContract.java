package com.siziksu.layers.domain.main;

import com.siziksu.layers.domain.BaseDomainContract;

public interface LoremDomainContract<D> extends BaseDomainContract<D> {

    void start();

    void getLoremIpsum(boolean usingCache);

    void setLastVisitedDate();
}
