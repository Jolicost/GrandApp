package com.jauxim.grandapp.ActivityInfo;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jauxim.grandapp.BaseApp;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.networking.Service;

import javax.inject.Inject;


public class ActivityInfo extends BaseApp implements ActivityInfoView {

    @Inject
    public Service service;

    private TextView tvTitle, tvPrice, tvDescription, tvRatingValue, idDirection;
    private RatingBar rbValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        getDeps().inject(this);

        renderView();

        ActivityInfoPresenter presenter = new ActivityInfoPresenter(service, this);
        presenter.getActivityInfo();
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
        Dialog.createDialog(this).title("server error").description(appErrorMessage).build();
    }

    public void renderView() {
        setContentView(R.layout.activity_info);

        tvTitle = findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        rbValue = findViewById(R.id.rbValue);
        tvRatingValue = findViewById(R.id.tvRatingValue);
        idDirection = findViewById(R.id.idDirection);
    }

    @Override
    public void getActivityInfoSuccess(ActivityModel activityModel) {
        /*
        tvTitle.setText(activityModel.getTitle());
        tvDescription.setText(activityModel.getDescription());
        tvPrice.setText(Utils.getPriceFormated(activityModel.getPrice()));
        */
    }
}
