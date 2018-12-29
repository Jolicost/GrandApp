package com.jauxim.grandapp.ui.Fragment.ActiviesList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.deps.Deps;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.Main.Main;
import com.jauxim.grandapp.ui.Fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivitiesList.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivitiesList newInstance(String param1, String param2) {
        ActivitiesList fragment = new ActivitiesList();
        mode = param2;
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

        mAdapter = new ActivityAdapter(getActivity(), activitiesList);
        activityesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        activityesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        activityesRecyclerView.setAdapter(mAdapter);

        final ActivityListPresenter presenter = new ActivityListPresenter(service, this);
        presenter.getActivityList(mode);


        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getActivityList(mode);
                updateLocation();
            }
        });
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
}
