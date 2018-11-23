package com.jauxim.grandapp.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.ProgressLayer;
import com.jauxim.grandapp.deps.DaggerDeps;
import com.jauxim.grandapp.deps.Deps;
import com.jauxim.grandapp.networking.NetworkModule;

import java.io.File;

public class BaseActivity extends AppCompatActivity {
    Deps deps;

    ProgressLayer progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

        View back = findViewById(R.id.backImageButton);
        if (back!=null)
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
    }

    public Deps getDeps() {
        return deps;
    }

    public void showProgress(){
        if (progress==null)
            progress = new ProgressLayer(this);
        progress.show();
    }

    public void hideProgress(){
        if (progress!=null && progress.isShowing())
            progress.cancel();
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
