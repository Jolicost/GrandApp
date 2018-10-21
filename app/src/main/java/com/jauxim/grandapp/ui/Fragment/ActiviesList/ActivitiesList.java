package com.jauxim.grandapp.ui.Fragment.ActiviesList;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.deps.Deps;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.Main.MainView;
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

    private List<ActivityListItemModel> activitiesList = new ArrayList<>();
    private ActivityAdapter mAdapter;

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

        prepareData();

        ActivityListPresenter presenter = new ActivityListPresenter(service, (MainView) getActivity());
        presenter.getActivityList();

        return view;
    }


    private void prepareData() {
        activitiesList.add(new ActivityListItemModel("https://www.masqueabuelos.com/wp-content/uploads/2018/02/la-petanca.jpg", "Petanca Aérea", "", 4.3f, "Manuél", new Pair(4, 0)));
        activitiesList.add(new ActivityListItemModel("http://www.fallaalqueriadebellver.com/blog/wp-content/uploads/Bolos-003.jpg", "Bolera extrema", "", 3.5f, "Isabel Ordóñez", new Pair(50, 0)));
        activitiesList.add(new ActivityListItemModel("https://www.diariolaprovinciasj.com/u/fotografias/m/2016/3/4/f768x0-87765_87783_22.jpg", "Cumple Geltrudis", "", 2f, "Isabel", new Pair(74, 0)));
        activitiesList.add(new ActivityListItemModel("https://www.radioactiva.cl/wp-content/uploads/2018/01/abuelos-830x400.jpg", "Baile nocturno", "", 4.3f, "Lucas & Irene", new Pair(213, 0)));
        activitiesList.add(new ActivityListItemModel("https://www.clinicadelaasuncion.com/wp-content/uploads/2013/07/abuelos_ipad.jpg", "Torneo Clash", "", 4.8f, "Ignacio", new Pair(240, 0)));
        activitiesList.add(new ActivityListItemModel("https://www.diarioderivas.es/wp-content/uploads/2018/05/paellada.gif", "Paellada popular", "", 5f, "Ayuntamiento", new Pair(257, 0)));
        activitiesList.add(new ActivityListItemModel("http://4.bp.blogspot.com/-6dNk1OPYRk4/T9jVyjf__bI/AAAAAAAAAHA/Axo4xPafOko/s640/abuela+maquillada.jpg", "Clases Maquillaje", "", 1.5f, "Gabriela", new Pair(297, 0)));
        activitiesList.add(new ActivityListItemModel("https://saposyprincesas.elmundo.es/wp-content/uploads/2017/11/relacion-abuelos-nietos.jpg", "Partido Parchís", "", 2.5f, "José Gabriel", new Pair(423, 0)));
        mAdapter.notifyDataSetChanged();
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
        Log.d("listActivities", "success: " + (activities != null));
        //TODO: inflate the list
    }
}
