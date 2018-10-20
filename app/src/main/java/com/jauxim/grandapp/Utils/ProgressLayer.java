package com.jauxim.grandapp.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.jauxim.grandapp.R;

public class ProgressLayer extends ProgressDialog {

    private AnimationDrawable animation;
    private Activity act;
    private ImageView baseImageView;

    private static boolean show = false;

    public ProgressLayer(Activity act) {
        super(act, R.style.CustomDialog);
        this.act = act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.progress_layout);
        baseImageView = findViewById(R.id.ivProgress);
        baseImageView.setBackgroundResource(R.drawable.progress_animation);
        animation = (AnimationDrawable) baseImageView.getBackground();

        setCancelable(false);
        setIndeterminate(true);
    }

    @Override
    public void show() {
        super.show();
        animation.start();
    }

    @Override
    public void dismiss() {
        if (isShowing() && !act.isDestroyed())
            super.dismiss();

        if (animation != null) animation.stop();
    }

}
