package com.jauxim.grandapp.networking;

import com.jauxim.grandapp.models.AuthModel;
import com.jauxim.grandapp.models.UserModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface NetworkServiceUser {

    @POST("/login")
    Observable<AuthModel> getLoginToken(@Body UserModel userModel, @Header("authorization") String auth);

    @POST("/users?username={user_name}&password={password}&email={email}")
    Observable<UserModel> postNewUser(@Path("user_name") String user_name, @Path("password") String password, @Path("email") String email);

}