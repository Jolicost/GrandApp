package com.jauxim.grandapp.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.SingleShotLocationProvider;
import com.jauxim.grandapp.deps.DaggerDeps;
import com.jauxim.grandapp.deps.Deps;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.NetworkModule;

import java.io.File;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class GeoService extends Service {

    @Inject
    public com.jauxim.grandapp.networking.Service service;

    private CompositeSubscription subscriptions;

    NetworkModule networkModule;
    private Handler mHandler;
    // default interval for syncing data
    public static final long DEFAULT_SYNC_INTERVAL = 1* 30 * 1000;

    Deps deps;

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

        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();
        deps.inject(this);
        subscriptions = new CompositeSubscription();

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
                        Log.d("geoService", "updated!! " + location.latitude + ", " + location.longitude);


                        updateLocation(location.latitude, location.longitude, getApplication());
                    }
                });
    }

    private void updateLocation(Double latitude, Double longitude, Context context) {

        try {
            String auth = DataUtils.getAuthToken(getApplication());
            Log.d("geoService", "auth: " + auth);
            Subscription subscription = service.sendUserPosition(new com.jauxim.grandapp.networking.Service.BasicCallback() {
                @Override
                public void onSuccess() {
                    Log.d("geoService", "OKKKK!!!!");
                }

                @Override
                public void onError(NetworkError networkError) {
                    Log.d("geoService", "BAAADD!! " + networkError.getMessage());
                }

            }, auth, latitude, longitude);
            subscriptions.add(subscription);

        } catch (Exception e) {
            Log.d("geoService", "e: " + e.getMessage());

        }
    }

    @Override
    public void onDestroy() {
        Log.d("geoService", "onDestroy");

        if(mHandler!=null)
            mHandler.removeCallbacks(runnableService);

        super.onDestroy();
    }
}