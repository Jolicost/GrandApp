package com.jauxim.grandapp.ui.Activity.ActivityEmergency;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.EmergencyContactsModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityEmergency extends BaseActivity implements ActivityEmergencyView {
    @Inject
    public Service service;

    @BindView(R.id.llContacts)
    LinearLayout llContacts;

    ActivityEmergencyPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency);
        getDeps().inject(this);
        ButterKnife.bind(this);

        presenter = new ActivityEmergencyPresenter(service, this);
        presenter.getEmergencyContacts();
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
        Dialog.createDialog(this).title("server error int act. emergency").description(appErrorMessage).build();
    }

    @Override
    public void getEmergencyContacts(List<EmergencyContactsModel> emergencyContactsModel) {
        int n = emergencyContactsModel.size();
        for (int i = 0; i < n; ++i){

            LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 25;
            ll.setLayoutParams(params);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            TextView tv = new TextView(this);
            tv.setText(emergencyContactsModel.get(i).alias);
            tv.setTextSize(21);
            tv.setTextColor(Color.BLACK);

            TextView tv2 = new TextView(this);
            tv.setText(emergencyContactsModel.get(i).phone);
            tv.setTextSize(21);
            tv.setTextColor(Color.BLACK);

            ll.addView(tv);
            ll.addView(tv2);
            llContacts.addView(ll);
        }
    }

    @OnClick(R.id.ivClose)
    void closeButtonClick() {
        onBackPressed();
    }

    @OnClick(R.id.ivEdit)
    void editButtonClick() {
        //presenter.editEmergencyContacts();
    }

/*
TRACTAR LLISTA

final int N = 10; // total number of textviews to add

final TextView[] myTextViews = new TextView[N]; // create an empty array;

for (int i = 0; i < N; i++) {
    // create a new textview
    final TextView rowTextView = new TextView(this);

    // set some properties of rowTextView or something
    rowTextView.setText("This is row #" + i);

    // add the textview to the linearlayout
    myLinearLayout.addView(rowTextView);

    // save a reference to the textview for later
    myTextViews[i] = rowTextView;
}

or

LinearLayout ll = (LinearLayout) findViewById(R.id.linearlayout2);

for (int i = 0; i < 5; i++)
 {
    TextView tv = new TextView(this);
    tv.setText("Dynamic TextView" + i);
    tv.setId(i + 5);
    ll.addView(tv);
}


    @Override
    public void editEmergencyContacts() {
        Intent intent = new Intent(this, ActivityEditEmergency.class);
        startActivity(intent);
    }*/
}
