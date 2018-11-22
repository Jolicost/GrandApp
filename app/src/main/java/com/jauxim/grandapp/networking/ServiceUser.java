package com.jauxim.grandapp.networking;

import com.jauxim.grandapp.models.AuthModel;
import com.jauxim.grandapp.models.UserModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ennur on 6/25/16.
 */
public class ServiceUser {
    private final NetworkServiceUser networkServiceUser;

    public ServiceUser(NetworkServiceUser networkServiceUser) {
        this.networkServiceUser = networkServiceUser;
    }



    public Subscription getLoginToken(UserModel userModel, final LoginCallback callback,String auth) {
        return networkServiceUser.getLoginToken(userModel,auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends AuthModel>>() {
                    @Override
                    public Observable<? extends AuthModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<AuthModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(AuthModel authModel) {
                        callback.onSuccess(authModel);
                    }
                });
    }


    public Subscription postNewUser(String username, String password, String email, final RegisterCallback callback) {
        return networkServiceUser.postNewUser(username, password, email)
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

    public interface LoginCallback {
        void onSuccess(AuthModel authModel);

        void onError(NetworkError networkError);
    }

    public interface RegisterCallback {
        void onSuccess(UserModel userModel);

        void onError(NetworkError networkError);
    }
}
