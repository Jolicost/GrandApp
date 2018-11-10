package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import android.util.Log;

import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.ImageBase64Model;
import com.jauxim.grandapp.models.ImageUrlModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ActivityEditPresenter {
    private final Service service;
    private final ActivityEditView view;
    private CompositeSubscription subscriptions;

    public ActivityEditPresenter(Service service, ActivityEditView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void createActivityInfo(ActivityModel activityInfo) {
        view.showWait();

        Subscription subscription = service.createActivityInfo(activityInfo, new Service.ActivityInfoCallback() {
            @Override
            public void onSuccess(ActivityModel activityModel) {
                view.removeWait();
                view.createActivityInfoSuccess(activityModel);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void updateImage(String base64) {
        Log.d("imagesResponse", "base64: "+base64);
        view.showWait();
        ImageBase64Model imageBase64Model = new ImageBase64Model();
        imageBase64Model.setBase64(base64);

        Subscription subscription = service.postImage(imageBase64Model, new Service.ImageCallback() {
            @Override
            public void onSuccess(ImageUrlModel imageUrl) {
                view.removeWait();
                //view.createActivityInfoSuccess(activityModel);
                Log.d("imagesResponse", "response: "+imageUrl.getImageUrl());
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
                Log.d("imagesResponse", "error with image: "+networkError.getMessage());
            }
        });

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
