package com.siziksu.layers.data.client.service;

import android.support.annotation.NonNull;

import com.siziksu.layers.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public final class RetrofitServiceFactory {

    @Inject
    public RetrofitServiceFactory() {}

    public <T> T createService(final Class<T> clazz, final String endPoint) {
        OkHttpClient.Builder okHttpClient = getOkHttpClientBuilder();
        Retrofit.Builder retrofit = getRetrofitBuilder(endPoint, okHttpClient);
        return retrofit.build().create(clazz);
    }

    @NonNull
    private OkHttpClient.Builder getOkHttpClientBuilder() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(15, TimeUnit.SECONDS);
        return okHttpClient;
    }

    @NonNull
    private Retrofit.Builder getRetrofitBuilder(final String endPoint, final OkHttpClient.Builder okHttpClient) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(logging);
        }
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofit.client(okHttpClient.build());
        return retrofit;
    }
}
