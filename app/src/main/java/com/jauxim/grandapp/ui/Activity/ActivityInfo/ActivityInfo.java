package com.jauxim.grandapp.ui.Activity.ActivityInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.jauxim.grandapp.Utils.SingleShotLocationProvider;
import com.jauxim.grandapp.Utils.Utils;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

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

    private String activityId;
    private MapView gMapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        setContentView(R.layout.activity_info);
        getDeps().inject(this);
        ButterKnife.bind(this);

        gMapView = findViewById(R.id.soleViewMap);
        gMapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(this);

        if (getIntent() != null && getIntent().getExtras() != null) {
            activityId = getIntent().getExtras().getString(Constants.ACTIVITY_ID);
        }

        Log.d("activityInfo", "id: "+activityId);

        ActivityInfoPresenter presenter = new ActivityInfoPresenter(service, this);
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

        List<String> urls = activityModel.getImages();
        if (urls!=null){
            if (urls.size()>=1)
                Glide.with(this).load(urls.get(0)).into(image1);
            if (urls.size()>=2)
                Glide.with(this).load(urls.get(1)).into(image2);
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

    @OnClick(R.id.ivClose)
    void closeButtonClick(View view) {
        onBackPressed();
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
