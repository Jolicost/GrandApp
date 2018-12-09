package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hbb20.CountryCodePicker;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Main.Main;
import com.jauxim.grandapp.ui.Activity.Register.Register;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityLogin extends BaseActivity implements ActivityLoginView {

    @Inject
    public Service service;

    CallbackManager callbackManager;

    private GoogleSignInClient mGoogleSignInClient;

    protected int RC_SIGN_IN = 413;

    @BindView(R.id.etPhoneNUmber)
    EditText etPhoneNUmber;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.tilPhoneNUmber)
    TextInputLayout tilPhoneNUmber;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;

    ActivityLoginPresenter presenter;

    @BindView(R.id.ccp)
    CountryCodePicker ccp;

    @BindView(R.id.llInputContainer)
    LinearLayout llInputContainer;

    @BindView(R.id.iv2)
    ImageView ivLogo;

    @BindView(R.id.textTitle)
    TextView textTitle;

    @BindView(R.id.tv2)
    TextView textWelcome;

    @BindView(R.id.bInvisibleFb)
    LoginButton bInvisibleFb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            llInputContainer.setTransitionGroup(true);
        }
        getDeps().inject(this);

        textTitle.setText(getString(R.string.login_button));
        textWelcome.setText(getString(R.string.welcomeLoginr));

        presenter = new ActivityLoginPresenter(service, this);

        startGoogle();
        startFacebook();
    }

    @OnClick(R.id.bLogin)
    public void loginClick() {
        if (etPhoneNUmber != null && etPassword != null) {
            String code = ccp.getSelectedCountryCodeWithPlus();
            String user = etPhoneNUmber.getText().toString();
            String pass = etPassword.getText().toString();
            showWait();
            presenter.login(code, user, pass);
        }
    }

    @OnClick(R.id.bLoginGoogle)
    public void googleLoginClick() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.bLoginFacebook)
    public void facebookLoginClick() {
        bInvisibleFb.performClick();
    }

    @OnClick(R.id.tvRegister)
    public void registerClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(this, Register.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    ivLogo,
                    ViewCompat.getTransitionName(ivLogo));
            startActivity(intent, options.toBundle());
        } else {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
    }


    @OnClick(R.id.etForgotPwd)
    public void forgotPasswordClick() {
        ForgotPasswordDialog cdd = new ForgotPasswordDialog(this);
        cdd.show();
    }

    @Override
    public void showWait() {
        showProgress();
    }

    @Override
    public void removeWait() {
        hideProgress();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Dialog.createDialog(this).title("server error int act. info").description(appErrorMessage).build();
    }

    @Override
    public void showUserError(int user_error) {
        removeWait();
        tilPhoneNUmber.setError(getString(user_error));
    }

    @Override
    public void resetErrors() {
        tilPassword.setError(null);
        tilPhoneNUmber.setError(null);
    }

    @Override
    public void showForgotPassSuccess(int forgotpsw_success) {
        removeWait();
        Dialog.createDialog(this).title(getString(forgotpsw_success)).description(getString(forgotpsw_success)).build();
    }

    @Override
    public void showPassError(int pass_error) {
        removeWait();
        tilPassword.setError(getString(pass_error));
    }

    @Override
    public void showLoginError(int login_error) {
        removeWait();
        Dialog.createDialog(this).title(getString(login_error)).description(getString(login_error)).build();
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finishAffinity();
    }

    public void forgotPassword(String phone, String code) {
        showWait();
        presenter.forgotPassword(phone, code);
    }

    protected void startGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("fbLogin", "onActivityResult");

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String photoUrl = null;
            if (account.getPhotoUrl() != null)
                photoUrl = account.getPhotoUrl().toString();
            UserModel model = new UserModel(account.getId(), account.getEmail(), account.getDisplayName(), photoUrl);
            presenter.doGoogleLogin(model);

        } catch (ApiException e) {
            Log.w("signInResult", "signInResult:failed msg=" + e.getMessage());
            Dialog.createDialog(this).title(R.string.login_error).description(R.string.login_error);
        }
    }

    private void startFacebook(){
        bInvisibleFb.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        Log.d("fbLogin", "ok3: "+loginResult.getAccessToken());

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());

                                        try {
                                            // Application code
                                            String email = object.getString("email");
                                            String birthday = object.getString("birthday"); // 01/31/1980 format
                                            String name = object.getString("name"); // 01/31/1980 format
                                            String imageUrl = "http://graph.facebook.com/"+loginResult.getAccessToken().getUserId()+"/picture";
                                            Log.d("jsonFacebook", "ok: "+email+", "+birthday+", "+name);

                                            UserModel model = new UserModel(loginResult.getAccessToken().getToken(), email, name, imageUrl);
                                            presenter.doFacebookLogin(model);

                                        }catch (JSONException ex){
                                            Log.d("jsonFacebook", "exception: "+ex.getMessage());

                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("fbLogin", "canceled3: ");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("fbLogin", "error3: "+exception.getMessage());

                    }
                });
    }
}
