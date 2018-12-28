package com.jauxim.grandapp.custom;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 5;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startTimeStamp = 0;
    private int oldDy = 0;
    private FloatingActionButton floatingActionButton;
    private int overallYScrol = 0;
    private boolean fabShowing = false;
    private static final int FAB_DISTANCE = 500;


    RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(RecyclerView.LayoutManager layoutManager, int initialNumElements) {
        this.mLayoutManager = layoutManager;
        previousTotalItemCount = initialNumElements;
    }

    public EndlessRecyclerViewScrollListener(RecyclerView.LayoutManager layoutManager, int initialNumElements, FloatingActionButton floatingActionButton) {
        this.mLayoutManager = layoutManager;
        previousTotalItemCount = initialNumElements;
        this.floatingActionButton = floatingActionButton;
        floatingActionButton.hide();
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager, int initialNumElements) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        previousTotalItemCount = initialNumElements;
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager, int initialNumElements) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        previousTotalItemCount = initialNumElements;
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        Log.d("EndlessScroll", "onScrolled totalItemCount = " + totalItemCount);

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        Log.d("EndlessScroll", "onScrolled loading1 = " + loading);

        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        Log.d("EndlessScroll", "onScrolled loading2 = " + loading);

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }
        Log.d("EndlessScroll", "onScrolled loading3 = " + loading);
        Log.d("EndlessScroll", "onScrolled lastVisibleItemPosition = " + lastVisibleItemPosition);
        Log.d("EndlessScroll", "onScrolled totalItemCount = " + totalItemCount);
        Log.d("EndlessScroll", "onScrolled (lastVisibleItemPosition + 1) == totalItemCount : " + ((lastVisibleItemPosition + 1) == totalItemCount));

        if (!loading && (lastVisibleItemPosition + 1) == totalItemCount) {
            Log.d("EndlessScroll", "onLoadMore = " + loading);

            onLoadMore();
            loading = true;
        }

        if (floatingActionButton != null) {
            overallYScrol -= dy;
            if (overallYScrol > FAB_DISTANCE && !fabShowing) {
                floatingActionButton.show();
                fabShowing = true;
            } else if (overallYScrol < FAB_DISTANCE && fabShowing){
                floatingActionButton.hide();
                fabShowing = false;
            }
        }
    }

    // Call this method whenever performing new searches
    public void resetState() {
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore();
}
