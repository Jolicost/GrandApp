package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityEditActivity extends BaseActivity implements ActivityEditView {

    @Inject
    public Service service;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.indicator)
    TabLayout indicator;

    @BindView(R.id.bNext)
    Button bNext;

    @BindView(R.id.bPrevious)
    Button bPrevious;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        ButterKnife.bind(this);

        ActivityEditPresenter presenter = new ActivityEditPresenter(service, this);
        //presenter.getActivityInfo();

        viewPager.setAdapter(new ActivityStepsAdapter(this));
        indicator.setupWithViewPager(viewPager, true);
    }

    @Override
    public void showWait() {

    }

    @Override
    public void removeWait() {

    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getActivityInfoSuccess(ActivityModel activityModel) {

    }

    @OnClick(R.id.ivClose)
    void closeButtonClick(View view) {
        onBackPressed();
    }

    /*
    @OnClick(R.id.btUpdate)
    void updateButton(View view) {
        updateModel();
    }
    */

    private void updateModel(){
        /*
        String title = etTitle.getText().toString();
        String desdription = etDescription.getText().toString();
        Long price = Long.parseLong(etPrice.getText().toString());

        ActivityModel model = new ActivityModel();
        model.setTitle(title);
        model.setDescription(desdription);
        model.setPrice(price);
        */
    }

    @OnClick(R.id.bNext)
    void nextButtonClick(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }

    @OnClick(R.id.bPrevious)
    void previousButtonClick(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    }
}
