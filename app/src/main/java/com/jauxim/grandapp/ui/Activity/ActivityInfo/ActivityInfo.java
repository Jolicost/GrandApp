package com.jauxim.grandapp.ui.Activity.ActivityInfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.Utils.Utils;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.ActivityProfile.ActivityProfile;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Main.Main;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.graphics.Color.rgb;

public class ActivityInfo extends BaseActivity implements ActivityInfoView {

    @Inject
    public Service service;

    @BindView(R.id.tvUpperTag)
    TextView tvUpperTag;

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

    @BindView(R.id.tvDirection)
    TextView tvDirection;

    @BindView(R.id.image1)
    ImageView image1;

    @BindView(R.id.image2)
    ImageView image2;

    @BindView(R.id.image3)
    ImageView image3;

    @BindView(R.id.image4)
    ImageView image4;

    @BindView(R.id.tvInitDate)
    TextView tvInitDate;

    @BindView(R.id.tvEndlDate)
    TextView tvEndlDate;

    @BindView(R.id.ivEdit)
    ImageView ivEdit;

    @BindView(R.id.ivDelete)
    ImageView ivDelete;

    @BindView(R.id.vProfile)
    RelativeLayout vProfile;


    private String activityId;
    private String userActivityId;
    private MapView gMapView;
    ActivityInfoPresenter presenter;

    private UserModel user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        setContentView(R.layout.activity_info);
        getDeps().inject(this);
        ButterKnife.bind(this);

        ivEdit.setVisibility(View.GONE);
        ivDelete.setVisibility(View.GONE);

        gMapView = findViewById(R.id.soleViewMap);
        gMapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(this);

        if (getIntent() != null && getIntent().getExtras() != null) {
            activityId = getIntent().getExtras().getString(Constants.ACTIVITY_ID);
        }

        Log.d("activityInfo", "id: "+activityId);

        presenter = new ActivityInfoPresenter(service, this);
        presenter.getActivityInfo(activityId);
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
        Dialog.createDialog(this).title("server error int act. info").description(appErrorMessage).build();
    }

    @Override
    public void getActivityInfoSuccess(final ActivityModel activityModel) {
        user = DataUtils.getUserInfo(this);
        userActivityId = activityModel.getUserId();
        tvUpperTag.setText(R.string.upperTag);

        tvTitle.setText(activityModel.getTitle());

        tvDescription.setText(activityModel.getDescription());

        Long price = activityModel.getPrice();
        if(price == 0) {
            tvPrice.setText(R.string.free_price);
            tvPrice.setTextColor(rgb(11, 188, 37));
        } else {
            tvPrice.setText(String.valueOf(price) + "€");
            tvPrice.setTextColor(rgb(216, 19, 19));
        }

        tvRatingValue.setText(activityModel.getRating()+"");
        tvDirection.setText(activityModel.getAddress());
        rbValue.setRating(activityModel.getRating());

        if (activityModel.getTimestampStart()!=null){
            tvInitDate.setText(Utils.getFullDataFormat(activityModel.getTimestampStart()));
        }else{
            tvInitDate.setVisibility(View.GONE);
        }

        if (activityModel.getTimestampEnd()!=null){
            tvEndlDate.setText(Utils.getFullDataFormat(activityModel.getTimestampEnd()));
        }else{
            tvEndlDate.setVisibility(View.GONE);
        }
        
        if (!TextUtils.isEmpty(activityModel.getUserId()) && userActivityId.equals(user.getId())){
            ivEdit.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
        }

        List<String> urls = activityModel.getImagesUrl();
        if (urls!=null){
            if (urls.size()>=1) {
                image1.setVisibility(View.VISIBLE);
                Glide.with(this).load(urls.get(0)).into(image1);
            }if (urls.size()>=2) {
                image2.setVisibility(View.VISIBLE);
                Glide.with(this).load(urls.get(1)).into(image2);
            }if (urls.size()>=3) {
                image3.setVisibility(View.VISIBLE);
                Glide.with(this).load(urls.get(2)).into(image3);
            }if (urls.size()>=4) {
                image4.setVisibility(View.VISIBLE);
                Glide.with(this).load(urls.get(3)).into(image4);
            }
        }

        if (gMapView!=null) {
            gMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (activityModel.getLatitude() != null && activityModel.getLongitude() != null)
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(activityModel.getLatitude(), activityModel.getLongitude()), 15.0f));
                    else
                        gMapView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void backToMainView() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void showDeleteSuccess(int delete_success) {
        Dialog.createDialog(this).title(getString(delete_success)).description(getString(delete_success)).build();
    }

    @Override
    public void viewProfile(String userId) {
        Intent intent = new Intent(this, ActivityProfile.class);
        intent.putExtra(Constants.PROFILE_ID, userId);
        startActivity(intent);
    }

    @OnClick(R.id.ivClose)
    void closeButtonClick() {
        onBackPressed();
    }

    @OnClick(R.id.ivEdit)
    void editButtonClick() {
        presenter.editActivity(activityId);
    }

    @OnClick(R.id.ivDelete)
    void deleteButtonClick() {
        showWait();
        presenter.deleteActivity(activityId);
    }

    @OnClick(R.id.vProfile)
    void viewProfileClick() {
        presenter.viewProfile(userActivityId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle("map");
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle("map", mapViewBundle);
        }

        if (gMapView != null)
            gMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (gMapView != null)
            gMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (gMapView != null)
            gMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (gMapView != null)
            gMapView.onStop();
    }

    @Override
    public void onPause() {
        if (gMapView != null)
            gMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (gMapView != null)
            gMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (gMapView != null)
            gMapView.onLowMemory();
    }
}
