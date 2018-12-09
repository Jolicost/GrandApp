package com.jauxim.grandapp.ui.Activity.ActivityEmergencyEdit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.models.EmergencyContactsModel;

import java.util.List;

public class ActivityEmergencyEditAdapter extends RecyclerView.Adapter<ActivityEmergencyEditAdapter.MyViewHolder> {

    private List<EmergencyContactsModel> emergencyContactsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText alias, phone;

        public MyViewHolder(View view) {
            super(view);
            alias = view.findViewById(R.id.etAlias);
            phone = view.findViewById(R.id.etPhone);
        }
    }

    public ActivityEmergencyEditAdapter(List<EmergencyContactsModel> emergencyContactsList) {
        this.emergencyContactsList = emergencyContactsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emergency_edit_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final EmergencyContactsModel emergencyContactsModel = emergencyContactsList.get(position);
        holder.alias.setText(emergencyContactsModel.getAlias());
        holder.phone.setText(emergencyContactsModel.getPhone());
    }

    @Override
    public int getItemCount() {
        return emergencyContactsList.size();
    }
}