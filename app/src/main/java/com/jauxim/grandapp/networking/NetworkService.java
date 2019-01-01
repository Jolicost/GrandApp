package com.jauxim.grandapp.networking;


import com.jauxim.grandapp.models.AchievementsModel;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.EmergencyContactsModel;
import com.jauxim.grandapp.models.LocationModel;
import com.jauxim.grandapp.models.LoginResponseModel;
import com.jauxim.grandapp.models.CityListResponse;
import com.jauxim.grandapp.models.ImageBase64Model;
import com.jauxim.grandapp.models.ImageUrlModel;
import com.jauxim.grandapp.models.PhoneModel;
import com.jauxim.grandapp.models.RateModel;
import com.jauxim.grandapp.models.RegisterModel;
import com.jauxim.grandapp.models.UserModel;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkService {

    String authKey = "x-access-token";
    @GET("v1/city")
    Observable<CityListResponse> getCityList(@Header(authKey) String auth);

    @GET("/normal/activities/{id}")
    Observable<ActivityModel> getActivityInfo(@Path("id") String activityId, @Header(authKey) String auth);

    @DELETE("/normal/activities/{id}")
    Observable<String> deleteActivity(@Path("id") String activityId, @Header(authKey) String auth);

    @POST("/normal/activities")
    Observable<Void> createActivityInfo(@Body ActivityModel activityInfo, @Header(authKey) String auth);

    @PUT("/normal/activities")
    Observable<Void> editActivityInfo(@Body ActivityModel activityInfo, @Header(authKey) String auth);

    @GET("/normal/activities")
    Observable<List<ActivityListItemModel>> getActivityList(@Header(authKey) String auth, @Query("limit") int limit, @Query("skip") int skip,
                                                            @Query("minPrice") Long minPrice, @Query("maxPrice") Long maxPrice, @Query("sort") int sort,
                                                            @Query("distMin") Long distMin, @Query("distMax") Long distMax, @Query("title") String name,
                                                            @Query("activityType") String type);

    @GET("/normal/own/activities")
    Observable<List<ActivityListItemModel>> getMyActivities(@Header(authKey) String auth);

    @POST("/login")
    Observable<LoginResponseModel> getLoginToken(@Body UserModel userModel);

    @POST("/register")
    Observable<LoginResponseModel> postNewUser(@Body RegisterModel model);

    @POST("/imagesJson")
    Observable<ImageUrlModel> postImage(@Body ImageBase64Model base64Image);

    @POST("/forgotPassword")
    Observable<Void> forgotPassword(@Body PhoneModel phone);

    @POST("/login/google")
    Observable<LoginResponseModel> getLoginGoogleToken(@Body UserModel userModel);

    @POST("/login/facebook")
    Observable<LoginResponseModel> getLoginFacebookToken(@Body UserModel userModel);

    @GET("/normal/users/{id}")
    Observable<UserModel> getProfileInfo(@Path("id") String userId, @Header(authKey) String auth);

    @PUT("/normal/users/{id}")
    Observable<Void> editProfileInfo(@Path("id") String userId, @Body UserModel user,@Header(authKey) String auth);

    @GET("/normal/users/{id}/emergency")
    Observable<List<EmergencyContactsModel>> getEmergencyContacts(@Path("id") String userId, @Header(authKey) String auth);

    @POST("/normal/users/{id}/emergency")
    Observable<Void> editEmergencyContacts(@Path("id") String userId, @Body List<EmergencyContactsModel> emergencyContactsList, @Header(authKey) String auth);

    @PUT("/geo")
    Observable<Void> sendUserPosition(@Header(authKey) String auth, @Body LocationModel locationModel);

    @POST("/normal/activities/{id}/join")
    Observable<Void> joinActivity(@Path("id") String activityId, @Header(authKey) String auth);

    @POST("/normal/activities/{id}/leave")
    Observable<Void> leaveActivity(@Path("id") String activityId, @Header(authKey) String auth);

    @POST("/normal/activities/{id}/vote")
    Observable<Void> voteActivity(@Path("id") String activityId, @Body RateModel rate, @Header(authKey) String auth);

    @POST("/normal/users/{id}/block")
    Observable<Void> blockUser(@Path("id") String userId, @Header(authKey) String auth);

    @POST("/normal/users/{id}/unblock")
    Observable<Void> unblockUser(@Path("id") String userId, @Header(authKey) String auth);

    @GET("/normal/users/{id}/achievements")
    Observable<List<AchievementsModel>> getAchievements(@Path("id") String userId, @Header(authKey) String auth);
}