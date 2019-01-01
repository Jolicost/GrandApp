package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.ImageBase64Model;
import com.jauxim.grandapp.models.ImageUrlModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import java.util.List;

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

    public void createActivityInfo(final ActivityModel activityInfo) {
        view.showWait();

        updateImagesRecursive(new ImagesUpdatedCallback() {
            @Override
            public void onSuccess(List<String> imagesUrl) {
                activityInfo.setImagesUrl(imagesUrl);
                Log.d("imagesResponse", "how many? "+activityInfo.getImagesUrl().size());
                String auth = DataUtils.getAuthToken((Context) view);
                Subscription subscription = service.createOrEditActivityInfo(activityInfo, new Service.ActivityCreateCallback() {
                    @Override
                    public void onSuccess() {
                        view.removeWait();
                        view.createActivityInfoSuccess();
                    }

                    @Override
                    public void onError(NetworkError networkError) {
                        view.removeWait();
                        view.onFailure(networkError.getMessage());
                    }

                },auth);
                subscriptions.add(subscription);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }
        }, activityInfo.getImagesBase64(), activityInfo.getImagesUrl());
    }

    private void updateImagesRecursive(final ImagesUpdatedCallback callback, final List<String> base64List, final List<String> urlList) {
        if (base64List==null || base64List.size()==0 ||  (base64List.size()==1 && TextUtils.isEmpty(base64List.get(0)))){
            callback.onSuccess(urlList);
            return;
        }

        final String base64 = base64List.get(0);
        view.showWait();
        ImageBase64Model imageBase64Model = new ImageBase64Model();
        imageBase64Model.setBase64(base64);

        Subscription subscription = service.postImage(imageBase64Model, new Service.ImageCallback() {
            @Override
            public void onSuccess(ImageUrlModel imageUrl) {
                view.removeWait();
                base64List.remove(0);
                urlList.add(imageUrl.getImageUrl());
                updateImagesRecursive(callback, base64List, urlList);
                Log.d("imagesResponse", "response: "+imageUrl.getImageUrl());
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                callback.onError(networkError);
                Log.d("imagesResponse", "error with image: "+networkError.getMessage());
            }
        });
        subscriptions.add(subscription);
    }

    public void getActivityInfo(String id) {
        view.showWait();
        String auth = DataUtils.getAuthToken((Context) view);
        Subscription subscription = service.getActivityInfo(id, new Service.ActivityInfoCallback() {
            @Override
            public void onSuccess(ActivityModel activityModel) {
                view.removeWait();
                view.getActivityInfoSuccess(activityModel);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }

    public interface ImagesUpdatedCallback {
        void onSuccess(List<String> imagesUrl);

        void onError(NetworkError networkError);
    }
}
