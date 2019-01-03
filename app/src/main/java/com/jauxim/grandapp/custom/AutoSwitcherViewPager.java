package com.jauxim.grandapp.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class AutoSwitcherViewPager extends ViewPager {

    private FixedSpeedScroller mScroller = null;
    private int TIME_DELAY_INTERVAL = 7500;

    private Runnable mSwither = new Runnable() {

        @Override
        public void run() {
            if (AutoSwitcherViewPager.this.getAdapter() != null) {
                int count = AutoSwitcherViewPager.this.getCurrentItem();
                if (count == (AutoSwitcherViewPager.this.getAdapter().getCount() - 1))
                    count = 0;
                else
                    count++;

                AutoSwitcherViewPager.this.setCurrentItem(count, true);
            }
            AutoSwitcherViewPager.this.postDelayed(this, TIME_DELAY_INTERVAL);
        }
    };

    public AutoSwitcherViewPager(Context context) {
        this(context, null);
    }

    public AutoSwitcherViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postDelayed(mSwither, TIME_DELAY_INTERVAL);
        init();
    }

    private void init() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            mScroller = new FixedSpeedScroller(getContext(),
                    new DecelerateInterpolator());
            scroller.set(this, mScroller);
        } catch (Exception ignored) {
        }
    }

    private class FixedSpeedScroller extends Scroller {

        private int mDuration = 300;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setScrollDuration(int duration) {
            mDuration = duration;
        }
    }
}
