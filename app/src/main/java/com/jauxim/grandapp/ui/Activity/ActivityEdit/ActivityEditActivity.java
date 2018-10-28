package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.Utils.Utils;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityInfo.ActivityInfo;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import java.util.ArrayList;

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
        getDeps().inject(this);
        ButterKnife.bind(this);

        ActivityEditPresenter presenter = new ActivityEditPresenter(service, this);

        /*
        //TEST POST
        ActivityModel activityInfo = new ActivityModel();
        activityInfo.setTitle("prova amb android");
        activityInfo.setDescription("hola!! això és una prova amb android usant el POST. Avui es diumenge, fa fred i tinc gana. Bona nit");
        activityInfo.setAddress("carrer del madamás al-halad Haidím, 25 bis 3º-4º");
        activityInfo.setCapacity(9283l);
        activityInfo.setImages(new ArrayList<String>());
        activityInfo.setPrice(4712l);
        activityInfo.setLatitude(41.3);
        activityInfo.setLongitude(2.1);
        activityInfo.setRating(2l);
        activityInfo.setUserId("userId");
        presenter.createActivityInfo(activityInfo);
        */

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
        Dialog.createDialog(this).title("server error").description(appErrorMessage).build();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            com.theartofdev.edmodo.cropper.CropImage.ActivityResult result = com.theartofdev.edmodo.cropper.CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = Utils.getBitmapFromUri(this, result.getUri());
                    //TODO: do something with the image, send to the adapter f.e
                } catch (Exception e) {
                    Log.e("croppingError", "Error preparing camera: ", e);
                }
            } else if (resultCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.e("croppingError", "error: " + result.getError());
            }
        }
    }
}
