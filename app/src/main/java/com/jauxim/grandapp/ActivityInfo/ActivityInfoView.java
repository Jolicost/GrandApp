package com.jauxim.grandapp.ActivityInfo;

import com.jauxim.grandapp.models.CityListResponse;

/**
 * Created by ennur on 6/25/16.
 */
public interface ActivityInfoView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getCityListSuccess(CityListResponse cityListResponse);

}
