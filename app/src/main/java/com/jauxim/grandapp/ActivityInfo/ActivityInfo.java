package com.jauxim.grandapp.ActivityInfo;

import android.os.Bundle;
import android.util.Log;

import com.jauxim.grandapp.BaseApp;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.ProgressLayer;
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

        ActivityInfoPresenter presenter = new ActivityInfoPresenter(service, this);
        presenter.getCityList();
    }

    @Override
    public void showWait() {
        Log.d("activityThings", "show wait");
        showProgress();
    }

    @Override
    public void removeWait() {
        Log.d("activityThings", "remove wait");
        hideProgress();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Log.d("activityThings", "call fail "+appErrorMessage);

    }

    @Override
    public void getCityListSuccess(CityListResponse cityListResponse) {
        Log.d("activityThings", "call  "+cityListResponse.getData().size());

    }
}
