package com.jauxim.grandapp.networking;


import com.jauxim.grandapp.models.CityListResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkService {

    @GET("v1/city")
    Observable<CityListResponse> getCityList();

}
