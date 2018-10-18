package com.jauxim.grandapp.ActivityInfo;

import android.os.Bundle;

import com.jauxim.grandapp.BaseApp;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.models.CityListResponse;
import com.jauxim.grandapp.networking.Service;

import javax.inject.Inject;


public class ActivityInfo extends BaseApp implements ActivityInfoView {

    @Inject
    public Service service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        setContentView(R.layout.activity_info);
        //ButterKnife.bind(this);

        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
    }

    @Override
    public void showWait() {

    }

    @Override
    public void removeWait() {

    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getCityListSuccess(CityListResponse cityListResponse) {

    }
}
