package com.jauxim.grandapp.ui.Fragment.ActiviesList;

import android.content.Context;
import android.util.Log;

import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.models.FilterActivityModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.jauxim.grandapp.Constants.ACTIVITY_ALL;
import static com.jauxim.grandapp.Constants.ACTIVITY_MINE;

public class ActivityListPresenter {
    private final Service service;
    private final ActivitiesListView view;
    private CompositeSubscription subscriptions;

    public ActivityListPresenter(Service service, ActivitiesListView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getActivityList(String mode, int page, FilterActivityModel filter) {
        Log.d("getActivityList", "page: "+page);
        view.showWait();
        String auth = DataUtils.getAuthToken(view.getContext());

        if (filter!=null) {
            Log.d("infoFilter", "price low: " + filter.getMinPrice());
            Log.d("infoFilter", "price high: " + filter.getMaxPrice());
            Log.d("infoFilter", "dist low: " + filter.getMinDistance());
            Log.d("infoFilter", "dist high: " + filter.getMaxDistance());
            Log.d("infoFilter", "name: " + filter.getName());
            Log.d("infoFilter", "sort: " + filter.getSort());
            Log.d("infoFilter", "category: " + filter.getCategory());
        }

        if (mode.equals(ACTIVITY_MINE)) {
            Subscription subscription = service.getMyActivities(new Service.MyActivitiesCallback() {
                @Override
                public void onSuccess(List<ActivityListItemModel> activities) {
                    view.removeWait();
                    Log.d("onResumeList", "size: "+String.valueOf(activities.size()));
                    view.getActivityListSuccess(activities);
                }

                @Override
                public void onError(NetworkError networkError) {
                    view.removeWait();
                    view.onFailure(networkError.getMessage());
                }

            }, auth);

            subscriptions.add(subscription);
        }

        else if (mode.equals(ACTIVITY_ALL)) {
            Subscription subscription = service.getActivityList(new Service.ActivityListCallback() {
                @Override
                public void onSuccess(List<ActivityListItemModel> activities) {
                    view.removeWait();
                    Log.d("onResumeList", "size: "+String.valueOf(activities.size()));
                    view.getActivityListSuccess(activities);
                }

                @Override
                public void onError(NetworkError networkError) {
                    view.removeWait();
                    view.onFailure(networkError.getMessage());
                }

            },auth, page, filter);

            subscriptions.add(subscription);
        }
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
