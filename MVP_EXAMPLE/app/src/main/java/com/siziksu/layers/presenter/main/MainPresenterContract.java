package com.siziksu.layers.presenter.main;

import com.siziksu.layers.presenter.BasePresenterContract;
import com.siziksu.layers.presenter.BaseViewContract;

public interface MainPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void start();

    void onReloadButtonClick();
}
