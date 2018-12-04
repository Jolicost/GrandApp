package com.jauxim.grandapp.networking;


import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.LoginResponseModel;
import com.jauxim.grandapp.models.CityListResponse;
import com.jauxim.grandapp.models.ImageBase64Model;
import com.jauxim.grandapp.models.ImageUrlModel;
import com.jauxim.grandapp.models.PhoneModel;
import com.jauxim.grandapp.models.RegisterModel;
import com.jauxim.grandapp.models.UserModel;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface NetworkService {

    String authKey = "x-access-token";
    @GET("v1/city")
    Observable<CityListResponse> getCityList(@Header(authKey) String auth);

    @GET("/activities/{id}")
    Observable<ActivityModel> getActivityInfo(@Path("id") String activityId,@Header(authKey) String auth);

    @DELETE("/activities/{id}")
    Observable<String> deleteActivity(@Path("id") String activityId,@Header(authKey) String auth);

    @POST("/activities")
    Observable<ActivityModel> createActivityInfo(@Body ActivityModel activityInfo,@Header(authKey) String auth);

    @GET("/activities")
    Observable<List<ActivityListItemModel>> getActivityList(@Header(authKey) String auth);

    @POST("/login")
    Observable<LoginResponseModel> getLoginToken(@Body UserModel userModel);

    @POST("/register")
    Observable<LoginResponseModel> postNewUser(@Body RegisterModel model);

    @POST("/imagesJson")
    Observable<ImageUrlModel> postImage(@Body ImageBase64Model base64Image);

    @POST("/forgotPassword")
    Observable<Void> forgotPassword(@Body PhoneModel phone);
}