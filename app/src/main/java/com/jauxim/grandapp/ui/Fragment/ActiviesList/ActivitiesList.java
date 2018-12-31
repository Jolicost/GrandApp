package com.jauxim.grandapp.ui.Fragment.ActiviesList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.custom.EndlessRecyclerViewScrollListener;
import com.jauxim.grandapp.deps.Deps;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.models.FilterActivityModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.Main.Main;
import com.jauxim.grandapp.ui.Fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class ActivitiesList extends BaseFragment implements ActivitiesListView {

    public static String TAG = "ActivitiesList";

    public ActivitiesList() {
    }

    @Inject
    public Service service;

    @BindView(R.id.list_activities)
    RecyclerView activityesRecyclerView;

    @BindView(R.id.srlRefresh)
    SwipeRefreshLayout srlRefresh;

    private List<ActivityListItemModel> activitiesList = new ArrayList<>();
    private ActivityAdapter mAdapter;

    private static String mode;
    private int page = 0;

    private EndlessRecyclerViewScrollListener scrollListener;
    private static FilterActivityModel filter;
    private ActivityListPresenter presenter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivitiesList.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivitiesList newInstance(String param1, String param2, FilterActivityModel filterModel) {
        ActivitiesList fragment = new ActivitiesList();
        mode = param2;
        filter = filterModel;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        Deps component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            //mPresenter.onAttach(this);
            //mBlogAdapter.setCallback(this);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), VERTICAL, false);

        mAdapter = new ActivityAdapter(getActivity(), activitiesList);
        activityesRecyclerView.setLayoutManager(mLayoutManager);
        //activityesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        activityesRecyclerView.setAdapter(mAdapter);

        presenter = new ActivityListPresenter(service, this);
        presenter.getActivityList(mode, page, filter);

        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                scrollListener.resetState();
                presenter.getActivityList(mode, page, filter);
            }
        });

        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager, 0) {
            @Override
            public void onLoadMore() {
                page+= Constants.ACTIVITIES_PAGE;
                Log.d("getActivityList", "onLoadMore "+page);
                presenter.getActivityList(mode, page, filter);
            }
        };
        activityesRecyclerView.addOnScrollListener(scrollListener);

        return view;
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
        Dialog.createDialog(getActivity()).title("server error in act. list").description(appErrorMessage).build();
    }

    @Override
    public void getActivityListSuccess(List<ActivityListItemModel> activities) {
        if (page == 0)
            activitiesList.clear();

        if (mode.equals(Constants.ACTIVITY_MINE))
            activitiesList.clear();
        activitiesList.addAll(activities);
        mAdapter.notifyDataSetChanged();
        srlRefresh.setRefreshing(false);
    }

    private void updateLocation() {
        if (getActivity() != null && getActivity() instanceof Main) {
            ((Main) getActivity()).updateLocation();
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    public void setFilter(FilterActivityModel filter){
        this.filter = filter;
        page = 0;
        scrollListener.resetState();
        presenter.getActivityList(mode, page, filter);
    }
}
