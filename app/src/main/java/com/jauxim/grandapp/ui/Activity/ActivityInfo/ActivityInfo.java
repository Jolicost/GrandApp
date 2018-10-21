package com.jauxim.grandapp.ui.Activity.ActivityInfo;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jauxim.grandapp.BaseApp;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.networking.Service;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityInfo extends BaseApp implements ActivityInfoView {

    @Inject
    public Service service;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvPrice)
    TextView tvPrice;

    @BindView(R.id.tvDescription)
    TextView tvDescription;

    @BindView(R.id.rbValue)
    RatingBar rbValue;

    @BindView(R.id.tvRatingValue)
    TextView tvRatingValue;

    @BindView(R.id.idDirection)
    TextView idDirection;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        setContentView(R.layout.activity_info);
        getDeps().inject(this);
        ButterKnife.bind(this);

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

    @Override
    public void getActivityInfoSuccess(ActivityModel activityModel) {
        /*
        tvTitle.setText(activityModel.getTitle());
        tvDescription.setText(activityModel.getDescription());
        tvPrice.setText(Utils.getPriceFormated(activityModel.getPrice()));
        */
    }

    @OnClick(R.id.ivClose)
    void closeButtonClick(View view) {
        onBackPressed();
    }
}
