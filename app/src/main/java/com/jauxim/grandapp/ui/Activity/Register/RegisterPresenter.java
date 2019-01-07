package com.jauxim.grandapp.ui.Activity.Register;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.Utils;
import com.jauxim.grandapp.models.ImageBase64Model;
import com.jauxim.grandapp.models.ImageUrlModel;
import com.jauxim.grandapp.models.LoginResponseModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class RegisterPresenter {

    private final Service service;
    private final RegisterView view;
    private CompositeSubscription subscriptions;

    public RegisterPresenter(Service service, RegisterView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void register(final String code, final String phone, final String email, final String pass, final String pass2, final String completeName, final String base64Image) {

        view.resetErrors();

        boolean error = false;

        if (phone.isEmpty()) {
            view.showUserError(R.string.phone_error);
            error = true;
        }
        if (!Utils.isValidEmailAddress(email)) {
            view.showEmailError(R.string.email_error);
            error = true;
        }
        if (pass.isEmpty() || pass2.isEmpty()) {
            view.showPassError(R.string.pass_error);
            error = true;
        } else if (!pass2.equals(pass)) {
            view.showPass2Error(R.string.pass2_error);
            error = true;
        } else if (completeName.isEmpty()){
            view.showPass2Error(R.string.name_error);
            error = true;
        }

        if (error) return;

        view.showWait();

        if (!TextUtils.isEmpty(base64Image)){
            view.showWait();
            ImageBase64Model imageBase64Model = new ImageBase64Model();
            imageBase64Model.setBase64(base64Image);

            Subscription subscription = service.postImage(imageBase64Model, new Service.ImageCallback() {
                @Override
                public void onSuccess(ImageUrlModel imageUrl) {
                    doSignUp(code+phone, pass, email, completeName, imageUrl.getImageUrl());
                    Log.d("imagesResponse", "response: "+imageUrl.getImageUrl());
                }

                @Override
                public void onError(NetworkError networkError) {
                    doSignUp(code+phone, pass, email, completeName, "");
                    Log.d("imagesResponse", "error with image: "+networkError.getMessage());
                }
            });
            subscriptions.add(subscription);
        }else{
            doSignUp(code+phone, pass, email, completeName, "");
        }



    }

    private void doSignUp(String phone, String pass, String email, String completeName, String image){
        Service.LoginCallback registerCallback = new Service.LoginCallback(){
            @Override
            public void onSuccess(LoginResponseModel userModel) {
                DataUtils.setAuthToken((Context) view,userModel.getToken());
                Log.d("authSaving", "is token?" + userModel.isAuth()+" getted token: "+userModel.getToken());
                DataUtils.saveUserModel((Context)view, userModel.getUser());
                view.removeWait();
                view.startMainActivity();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getMessage());
            }

        };
        Subscription subscription = service.postNewUser(phone, pass, email, completeName, image, registerCallback);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
