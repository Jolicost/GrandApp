package com.jauxim.grandapp.networking;


import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.CityListResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkService {

    @GET("v1/city")
    Observable<CityListResponse> getCityList();

    @GET("/activities/{id}")
    Observable<ActivityModel> getActivityInfo(@Path("id") String activityId);

    @POST("/activities")
    Observable<ActivityModel> createActivityInfo(@Body ActivityModel activityInfo);

    @GET("/activities")
    Observable<List<ActivityListItemModel>> getActivityList();
}
