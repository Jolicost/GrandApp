package com.siziksu.layers.domain;

public interface BaseDomainContract<D> {

    void register(D presenter);

    void unregister();
}
