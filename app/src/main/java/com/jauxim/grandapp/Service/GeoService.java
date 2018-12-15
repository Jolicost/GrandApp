package com.jauxim.grandapp.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jauxim.grandapp.BuildConfig;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.SingleShotLocationProvider;
import com.jauxim.grandapp.deps.DaggerDeps;
import com.jauxim.grandapp.deps.Deps;
import com.jauxim.grandapp.models.LocationModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.NetworkModule;
import com.jauxim.grandapp.networking.NetworkService;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.jauxim.grandapp.BuildConfig.BASEURL;

public class GeoService extends Service {

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit;

    NetworkService service;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    private Handler mHandler;
    // default interval for syncing data
    public static final long DEFAULT_SYNC_INTERVAL = 1 * 60 * 1000;


    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            Log.d("geoService", "runnableService");

            updatePosition();
            // Repeat this runnable code block again every ... min
            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create the Handler object
        Log.d("geoService", "onStartCommand");

        Cache cache = null;
        try {
            File cacheFile = new File(getCacheDir(), "responses");
            cache = new Cache(cacheFile, 10 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .removeHeader("Pragma")
                                .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                                .build();

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(NetworkService.class);

        mHandler = new Handler();
        // Execute a runnable task as soon as possible
        mHandler.post(runnableService);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("geoService", "onBind");
        return null;
    }

    private synchronized void updatePosition() {
        Log.d("geoService", "updatePosition");

        // call your rest service here
        SingleShotLocationProvider.requestSingleUpdate(getApplication(),
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        DataUtils.saveLocation(getApplication(), location);
                        Log.d("geoService", "updated!! "+location.latitude+", "+location.longitude);


                        updateLocation(location.latitude, location.longitude, getApplication());

                        /*
                        Intent i= new Intent(view.getContext(), GeoService.class);
                        view.getContext().startService(i);
                        */
                    }
                });
    }

    private void updateLocation(Double latitude, Double longitude, Context context){
        String auth = DataUtils.getAuthToken(context);
        Log.d("geoService", "auth: "+auth);

        try {
            LocationModel model = new LocationModel();
            model.setLatitude(latitude);
            model.setLongitude(longitude);
            Log.d("geoService", "ok5 ");
            service.sendUserPosition(auth, model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends Void>>() {
                        @Override
                        public Observable<? extends Void> call(Throwable throwable) {
                            return Observable.error(throwable);
                        }
                    })
                    .subscribe(new Subscriber<Void>() {
                        @Override
                        public void onCompleted() {
                            Log.d("geoService", " service onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("geoService", "service onError " + e.getMessage());
                        }

                        @Override
                        public void onNext(Void s) {
                            Log.d("geoService", "service onNext");
                        }
                    });

        } catch (Exception e){
            Log.d("geoService", "e: "+e.getMessage());

        }
    }
}