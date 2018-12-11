package com.jauxim.grandapp.networking;

import android.util.Log;

import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.EmergencyContactsModel;
import com.jauxim.grandapp.models.LoginResponseModel;
import com.jauxim.grandapp.models.CityListResponse;
import com.jauxim.grandapp.models.ImageBase64Model;
import com.jauxim.grandapp.models.ImageUrlModel;
import com.jauxim.grandapp.models.PhoneModel;
import com.jauxim.grandapp.models.RegisterModel;
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

    public Subscription getActivityInfo(String activityId, final ActivityInfoCallback callback,String auth) {
        return networkService.getActivityInfo(activityId, auth)
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

    public Subscription createActivityInfo(ActivityModel activityInfo, final ActivityInfoCallback callback,String auth) {
        return networkService.createActivityInfo(activityInfo,auth)
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

    public Subscription getActivityList(final ActivityListCallback callback,String auth) {
        return networkService.getActivityList(auth)
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


    public Subscription getCityList(final GetCityListCallback callback,String auth) {

        return networkService.getCityList(auth)
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

    public Subscription getLoginToken(UserModel userModel, final LoginCallback callback) {
        return networkService.getLoginToken(userModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends LoginResponseModel>>() {
                    @Override
                    public Observable<? extends LoginResponseModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<LoginResponseModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(LoginResponseModel authModel) {
                        callback.onSuccess(authModel);
                    }
                });
    }

    public Subscription getLoginGoogleToken(UserModel userModel, final LoginCallback callback) {
        return networkService.getLoginGoogleToken(userModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends LoginResponseModel>>() {
                    @Override
                    public Observable<? extends LoginResponseModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<LoginResponseModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(LoginResponseModel authModel) {
                        callback.onSuccess(authModel);
                    }
                });
    }

    public Subscription getLoginFacebookToken(UserModel userModel, final LoginCallback callback) {
        return networkService.getLoginFacebookToken(userModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends LoginResponseModel>>() {
                    @Override
                    public Observable<? extends LoginResponseModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<LoginResponseModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(LoginResponseModel authModel) {
                        callback.onSuccess(authModel);
                    }
                });
    }

    public Subscription postNewUser(String phone, String password, String email, String completeName, String image, final LoginCallback callback) {
        RegisterModel registerModel = new RegisterModel();
        registerModel.setPhone(phone);
        registerModel.setPassword(password);
        registerModel.setEmail(email);
        registerModel.setCompleteName(completeName);
        registerModel.setProfilePic(image);

        return networkService.postNewUser(registerModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends LoginResponseModel>>() {
                    @Override
                    public Observable<? extends LoginResponseModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<LoginResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(LoginResponseModel loginResponseModel) {
                        callback.onSuccess(loginResponseModel);
                    }
                });
    }

    public Subscription postImage(ImageBase64Model base64, final ImageCallback callback) {
        return networkService.postImage(base64)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ImageUrlModel>>() {
                    @Override
                    public Observable<? extends ImageUrlModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ImageUrlModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(ImageUrlModel base64) {
                        callback.onSuccess(base64);

                    }
                });
    }

    public Subscription forgotPassword(PhoneModel phone, final ForgotPasswordCallback callback) {
        Log.d("phoneNumber"," phone: "+phone.getPhone());
        return networkService.forgotPassword(phone)
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

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Void s) {
                        callback.onSuccess();
                    }
                });
    }

    public Subscription deleteActivity(String activityId, final DeleteActivityCallback callback, String auth) {
        return networkService.deleteActivity(activityId, auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {
                    @Override
                    public Observable<? extends String> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(String s) {
                        callback.onSuccess(s);

                    }
                });
    }

    public Subscription getProfileInfo(String userId, final ProfileInfoCallback callback, String auth) {
        return networkService.getProfileInfo(userId, auth)
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

    public Subscription editProfileInfo(UserModel user, final EditProfileCallback callback, String auth) {
        return networkService.editProfileInfo(user.getId(),user,auth)
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
                    public void onNext(UserModel user) {
                        callback.onSuccess(user);

                    }
                });
    }

    public Subscription getEmergencyContacts(String userId, final EmergencyContactsCallback callback, String auth) {
        return networkService.getEmergencyContacts(userId, auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<EmergencyContactsModel>>>() {
                    @Override
                    public Observable<? extends List<EmergencyContactsModel>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<List<EmergencyContactsModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<EmergencyContactsModel> emergencyContactsModel) {
                        callback.onSuccess(emergencyContactsModel);

                    }
                });
    }

    public Subscription editEmergencyContacts(String userId, List<EmergencyContactsModel> emergencyContactsList, final EditEmergencyContactsCallback callback, String auth) {
        return networkService.editEmergencyContacts(userId,emergencyContactsList ,auth)
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

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Void s) {
                        callback.onSuccess();

                    }
                });
    }

    public interface GetCityListCallback {
        void onSuccess(CityListResponse cityListResponse);

        void onError(NetworkError networkError);
    }

    public interface ForgotPasswordCallback {
        void onSuccess();

        void onError(NetworkError networkError);
    }

    public interface ActivityInfoCallback {
        void onSuccess(ActivityModel activityModel);

        void onError(NetworkError networkError);
    }

    public interface DeleteActivityCallback {
        void onSuccess(String s);

        void onError(NetworkError networkError);
    }

    public interface LoginCallback {
        void onSuccess(LoginResponseModel authModel);

        void onError(NetworkError networkError);
    }

    public interface ActivityListCallback {
        void onSuccess(List<ActivityListItemModel> activityModel);

        void onError(NetworkError networkError);
    }

    public interface ImageCallback {
        void onSuccess(ImageUrlModel urlImage);

        void onError(NetworkError networkError);
    }

    public interface ProfileInfoCallback {
        void onSuccess(UserModel userModel);

        void onError(NetworkError networkError);
    }

    public interface EditProfileCallback {
        void onSuccess(UserModel userModel);

        void onError(NetworkError networkError);
    }

    public interface EmergencyContactsCallback {
        void onSuccess(List<EmergencyContactsModel> emergencyContactsModel);

        void onError(NetworkError networkError);
    }

    public interface EditEmergencyContactsCallback {
        void onSuccess();

        void onError(NetworkError networkError);
    }
}
