package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivityEditActivity extends BaseActivity implements ActivityEditView {

    @Inject
    public Service service;

    @BindView(R.id.etTitle)
    EditText etTitle;

    @BindView(R.id.etDescription)
    EditText etDescription;

    @BindView(R.id.etPrice)
    EditText etPrice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        ActivityEditPresenter presenter = new ActivityEditPresenter(service, this);
        //presenter.getActivityInfo();
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

    @OnClick(R.id.btUpdate)
    void updateButton(View view) {
        updateModel();
    }

    private void updateModel(){
        String title = etTitle.getText().toString();
        String desdription = etDescription.getText().toString();
        Long price = Long.parseLong(etPrice.getText().toString());

        ActivityModel model = new ActivityModel();
        model.setTitle(title);
        model.setDescription(desdription);
        model.setPrice(price);
    }
}
