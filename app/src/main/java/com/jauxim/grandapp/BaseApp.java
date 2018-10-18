package com.jauxim.grandapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jauxim.grandapp.Utils.ProgressLayer;
import com.jauxim.grandapp.deps.DaggerDeps;
import com.jauxim.grandapp.deps.Deps;
import com.jauxim.grandapp.networking.NetworkModule;

import java.io.File;

public class BaseApp extends AppCompatActivity {
    Deps deps;

    ProgressLayer progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();
    }

    public Deps getDeps() {
        return deps;
    }

    protected void showProgress(){
        if (progress==null)
            progress = new ProgressLayer(this);
        progress.show();
    }

    protected void hideProgress(){
        if (progress!=null)
            progress.dismiss();
    }
}
