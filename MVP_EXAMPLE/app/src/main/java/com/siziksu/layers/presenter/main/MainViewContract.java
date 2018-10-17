package com.siziksu.layers.presenter.main;

import com.siziksu.layers.presenter.BaseViewContract;

public interface MainViewContract extends BaseViewContract {

    void showProgress();

    void hideProgress();

    void showLastVisitedDate(String string);

    void showLoremIpsum(String string);

    void showError(String message);
}
