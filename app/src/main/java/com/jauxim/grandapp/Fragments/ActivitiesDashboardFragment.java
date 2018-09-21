package com.jauxim.grandapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jauxim.grandapp.R;

public class ActivitiesDashboardFragment extends Fragment {

    public ActivitiesDashboardFragment() {
        // Required empty public constructor
    }

    RecyclerView activityesRecyclerView;

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




        return view;
    }

}
