package com.jauxim.grandapp.networking;

import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.CityListResponse;
import com.jauxim.grandapp.models.UserModel;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ennur on 6/25/16.
 */
public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getActivityInfo(String activityId, final ActivityInfoCallback callback) {
        return networkService.getActivityInfo(activityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ActivityModel>>() {
                    @Override
                    public Observable<? extends ActivityModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ActivityModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(ActivityModel activityModel) {
                        callback.onSuccess(activityModel);

                    }
                });
    }

    public Subscription createActivityInfo(ActivityModel activityInfo, final ActivityInfoCallback callback) {
        return networkService.createActivityInfo(activityInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ActivityModel>>() {
                    @Override
                    public Observable<? extends ActivityModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ActivityModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(ActivityModel activityModel) {
                        callback.onSuccess(activityModel);

                    }
                });
    }

    public Subscription getActivityList(final ActivityListCallback callback) {
        return networkService.getActivityList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<ActivityListItemModel>>>() {
                    @Override
                    public Observable<? extends List<ActivityListItemModel>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<List<ActivityListItemModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<ActivityListItemModel> activityModel) {
                        callback.onSuccess(activityModel);

                    }
                });
    }

    public Subscription getCityList(final GetCityListCallback callback) {

        return networkService.getCityList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends CityListResponse>>() {
                    @Override
                    public Observable<? extends CityListResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<CityListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(CityListResponse cityListResponse) {
                        callback.onSuccess(cityListResponse);

                    }
                });
    }

    public Subscription postNewUser(String username, String password, String email, final RegisterCallback callback) {
        return networkService.postNewUser(username, password, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends UserModel>>() {
                    @Override
                    public Observable<? extends UserModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<UserModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(UserModel userModel) {
                        callback.onSuccess(userModel);

                    }
                });
    }

    public interface GetCityListCallback {
        void onSuccess(CityListResponse cityListResponse);

        void onError(NetworkError networkError);
    }

    public interface ActivityInfoCallback {
        void onSuccess(ActivityModel activityModel);

        void onError(NetworkError networkError);
    }

    public interface ActivityListCallback {
        void onSuccess(List<ActivityListItemModel> activityModel);

        void onError(NetworkError networkError);
    }

    public interface RegisterCallback {
        void onSuccess(UserModel userModel);

        void onError(NetworkError networkError);
    }
}
