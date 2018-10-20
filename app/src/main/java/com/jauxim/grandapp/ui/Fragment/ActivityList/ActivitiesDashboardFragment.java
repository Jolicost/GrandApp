package com.jauxim.grandapp.ui.Fragment.ActivityList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jauxim.grandapp.ActivityModel;
import com.jauxim.grandapp.R;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesDashboardFragment extends Fragment {

    public ActivitiesDashboardFragment() {
        // Required empty public constructor
    }

    RecyclerView activityesRecyclerView;

    private List<ActivityModel> activitiesList = new ArrayList<>();
    private ActivityAdapter mAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivitiesDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivitiesDashboardFragment newInstance(String param1, String param2) {
        ActivitiesDashboardFragment fragment = new ActivitiesDashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        activityesRecyclerView = view.findViewById(R.id.list_activities);

        mAdapter = new ActivityAdapter(getActivity(), activitiesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        activityesRecyclerView.setLayoutManager(mLayoutManager);
        activityesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        activityesRecyclerView.setAdapter(mAdapter);

        //prepareData();

        return view;
    }

    /*
    private void prepareData(){
        activitiesList.add(new ActivityModel("https://www.masqueabuelos.com/wp-content/uploads/2018/02/la-petanca.jpg", "Petanca Aérea", "", 4.3f, "Manuél", new Pair(4, 0)));
        activitiesList.add(new ActivityModel("http://www.fallaalqueriadebellver.com/blog/wp-content/uploads/Bolos-003.jpg", "Bolera extrema", "", 3.5f, "Isabel Ordóñez", new Pair(50, 0)));
        activitiesList.add(new ActivityModel("https://www.diariolaprovinciasj.com/u/fotografias/m/2016/3/4/f768x0-87765_87783_22.jpg", "Cumple Geltrudis", "", 2f, "Isabel", new Pair(74, 0)));
        activitiesList.add(new ActivityModel("https://www.radioactiva.cl/wp-content/uploads/2018/01/abuelos-830x400.jpg", "Baile nocturno", "", 4.3f, "Lucas & Irene", new Pair(213, 0)));
        activitiesList.add(new ActivityModel("https://www.clinicadelaasuncion.com/wp-content/uploads/2013/07/abuelos_ipad.jpg", "Torneo Clash", "", 4.8f, "Ignacio", new Pair(240, 0)));
        activitiesList.add(new ActivityModel("https://www.diarioderivas.es/wp-content/uploads/2018/05/paellada.gif", "Paellada popular", "", 5f, "Ayuntamiento", new Pair(257, 0)));
        activitiesList.add(new ActivityModel("http://4.bp.blogspot.com/-6dNk1OPYRk4/T9jVyjf__bI/AAAAAAAAAHA/Axo4xPafOko/s640/abuela+maquillada.jpg", "Clases Maquillaje", "", 1.5f, "Gabriela", new Pair(297, 0)));
        activitiesList.add(new ActivityModel("https://saposyprincesas.elmundo.es/wp-content/uploads/2017/11/relacion-abuelos-nietos.jpg", "Partido Parchís", "", 2.5f, "José Gabriel", new Pair(423, 0)));
        mAdapter.notifyDataSetChanged();
    }
    */

}
