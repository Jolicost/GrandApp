package com.jauxim.grandapp.ui.Activity.ActivitySplash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.ui.Activity.ActivityLogin.ActivityLogin;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Init.Init;
import com.jauxim.grandapp.ui.Activity.Main.Main;

public class SplashActivity extends BaseActivity {

    private int TIME_SPLASH = 1500;
    private ImageView ivSplash;
    private TextView text_animation;
    private String auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        ivSplash = findViewById(R.id.ivSplash);

        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(300);
        animation1.setStartOffset(200);
        animation1.setFillAfter(true);
        ivSplash.startAnimation(animation1);

        auth = DataUtils.getAuthToken(this);

        Log.d("authSaving", "auth recovered: "+auth);

        /*
        if (auth!=null && auth.length()>0){

            AlphaAnimation animation2 = new AlphaAnimation(0.0f, 1.0f);
            animation2.setDuration(100);
            animation2.setStartOffset(200);
            animation2.setFillAfter(true);
            text_animation.startAnimation(animation2);
        }
        */

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth!=null && auth.length()>0)
                    initMain();
                else
                    initInit();
            }
        }, TIME_SPLASH);
    }

    /**
     * Inits the QR screen
     */
    private void initMain() {
        Intent intent = new Intent(SplashActivity.this, Main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Inits the login screen for all android versions
     */
    private void initInit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) initInit(true);
        else initInit(false);
    }

    /**
     * Inits the login screen
     *
     * @param effect indicates if can do a transition effect in the logo image (only for > lollipop)
     */
    private void initInit(boolean effect) {
        if (effect) {
            try {
                runOnUiThread(new Runnable() {
                    @SuppressLint("NewApi")
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, Init.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                SplashActivity.this,
                                ivSplash,
                                ViewCompat.getTransitionName(ivSplash));
                        SplashActivity.this.startActivity(intent, options.toBundle());
                    }
                });
            } catch (Exception e) {
                initInit(false);
            }
        } else {
            Intent intent = new Intent(this, Init.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        finish();
    }
}
