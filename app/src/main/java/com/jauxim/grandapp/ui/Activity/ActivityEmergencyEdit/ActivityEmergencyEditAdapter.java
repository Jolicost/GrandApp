package com.jauxim.grandapp.ui.Activity.ActivityEmergencyEdit;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.hbb20.CountryCodePicker;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.models.EmergencyContactsModel;

import java.util.List;

public class ActivityEmergencyEditAdapter extends RecyclerView.Adapter<ActivityEmergencyEditAdapter.MyViewHolder> {

    private List<EmergencyContactsModel> emergencyContactsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText alias, phone;
        public ImageView delete;
        public CountryCodePicker ccp;

        public MyViewHolder(View view) {
            super(view);
            alias = view.findViewById(R.id.etAlias);
            phone = view.findViewById(R.id.etPhone);
            delete = view.findViewById(R.id.ivDelete);
            ccp = view.findViewById(R.id.ccpReg);
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final EmergencyContactsModel emergencyContactsModel = emergencyContactsList.get(position);
        holder.alias.setText(emergencyContactsModel.getAlias());
        holder.ccp.setFullNumber(emergencyContactsModel.getPhone());
        holder.phone.setText(emergencyContactsModel.getPhone().replace("+", "").replaceFirst(holder.ccp.getFormattedFullNumber(), ""));
        holder.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Log.d("phoneSelected", "Phone: "+holder.ccp.getFullNumberWithPlus() + holder.phone.getText().toString());
                emergencyContactsList.get(holder.getAdapterPosition()).setPhone(holder.ccp.getFullNumberWithPlus() + holder.phone.getText().toString());
            }
        });
        holder.alias.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                emergencyContactsList.get(holder.getAdapterPosition()).setAlias(editable.toString());
            }
        });
        holder.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                emergencyContactsList.get(holder.getAdapterPosition()).setPhone(holder.ccp.getFullNumberWithPlus() + editable.toString());
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emergencyContactsList.remove(holder.getAdapterPosition());
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return emergencyContactsList.size();
    }
}