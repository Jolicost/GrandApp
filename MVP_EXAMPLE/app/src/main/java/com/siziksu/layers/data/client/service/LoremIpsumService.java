package com.siziksu.layers.data.client.service;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoremIpsumService {

    @GET("api/?type=meat-and-filler")
    Single<List<String>> getLoremIpsum(@Query("paras") int paras);
}
