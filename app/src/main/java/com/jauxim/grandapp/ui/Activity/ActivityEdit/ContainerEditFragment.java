package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.SingleShotLocationProvider;
import com.jauxim.grandapp.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_DESCRIPTION;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_IMAGES;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_LOCATION;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_MISCELANIA;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_PREVIEW;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_TIME;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_TITLE;

public class ContainerEditFragment extends Fragment implements View.OnClickListener {

    int position = 0;

    private TextView etTitle;
    private TextView etDescription;
    private ImageView[] images = new ImageView[4];
    private Bitmap[] bitmaps = new Bitmap[4];
    private SupportMapFragment mapFragment;
    private MapView gMapView;

    private double actualLatitude;
    private double actualLongitude;

    private int imageChanging = -1;

    public @interface stepsEditActivity {
        int STEP_TITLE = 0;
        int STEP_DESCRIPTION = 1;
        int STEP_IMAGES = 2;
        int STEP_LOCATION = 3;
        int STEP_TIME = 4;
        int STEP_MISCELANIA = 5;
        int STEP_PREVIEW = 6;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_activity_step_item, container, false);

        position = getArguments().getInt("pos");
        View main = view.findViewById(R.id.main);
        View vTitle = view.findViewById(R.id.vTitle);
        View vDescription = view.findViewById(R.id.vDescription);
        View vImages = view.findViewById(R.id.vImages);
        View vLocation = view.findViewById(R.id.vLocation);
        View vDate = view.findViewById(R.id.vDate);
        View vMiscelania = view.findViewById(R.id.vMiscelania);
        View vPreview = view.findViewById(R.id.vPreview);

        switch (position) {
            case STEP_TITLE:
                etTitle = view.findViewById(R.id.etTitle);

                vTitle.setVisibility(View.VISIBLE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case STEP_DESCRIPTION:
                etDescription = view.findViewById(R.id.etDescription);

                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.VISIBLE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case STEP_IMAGES:

                images[0] = view.findViewById(R.id.ivImage1);
                images[1] = view.findViewById(R.id.ivImage2);
                images[2] = view.findViewById(R.id.ivImage3);
                images[3] = view.findViewById(R.id.ivImage4);

                for (ImageView image : images)
                    image.setOnClickListener(this);

                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.VISIBLE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case STEP_LOCATION:
                mapFragment = new SupportMapFragment();

                //Utils.changeMapFragment((AppCompatActivity) getActivity(), R.id.geoFencingMapFragment, mapFragment, "GeoFetchingMapFragment");

                gMapView = view.findViewById(R.id.soleViewMap);
                gMapView.onCreate(savedInstanceState);
                gMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap googleMap) {
                        SingleShotLocationProvider.GPSCoordinates coord = DataUtils.getLocation(getActivity());
                        if (coord != null) {
                            Log.d("coordEdit", "coord are not null: " + coord.latitude + ", " + coord.longitude);
                            googleMap.setMinZoomPreference(10);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(coord.latitude, coord.longitude), 15.0f));
                        } else {
                            Log.d("coordEdit", "coord are null: ");
                        }

                        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                            @Override
                            public void onCameraMove() {
                                actualLatitude = googleMap.getCameraPosition().target.latitude;
                                actualLongitude = googleMap.getCameraPosition().target.longitude;
                            }
                        });
                    }
                });

                MapsInitializer.initialize(getActivity());

                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.VISIBLE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case STEP_TIME:
                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.VISIBLE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case STEP_MISCELANIA:
                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.VISIBLE);
                vPreview.setVisibility(View.GONE);

                break;

            case STEP_PREVIEW:
                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, position);

        return view;
    }

    public static ContainerEditFragment newInstance(int position) {

        ContainerEditFragment f = new ContainerEditFragment();
        Bundle b = new Bundle();
        b.putInt("pos", position);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivImage1:
                imageChanging = 0;
                break;
            case R.id.ivImage2:
                imageChanging = 1;
                break;
            case R.id.ivImage3:
                imageChanging = 2;
                break;
            case R.id.ivImage4:
                imageChanging = 3;
                break;
        }
        Utils.createCropCamera(false).start(getActivity(), this);
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

    public String getTitle() {
        if (etTitle != null)
            return etTitle.getText().toString();
        return null;
    }

    public String getDescription() {
        if (etDescription != null)
            return etDescription.getText().toString();
        return null;
    }

    public SingleShotLocationProvider.GPSCoordinates getLocation() {
        //activity won't never be on the sea
        if (actualLatitude != 0 && actualLongitude != 0) {
            return new SingleShotLocationProvider.GPSCoordinates(actualLatitude, actualLongitude);
        }
        return null;
    }

    public List<Bitmap> getImages(){
        List<Bitmap> bitmapList = new ArrayList<>();
        if (bitmaps!=null){
            for (Bitmap bitmap:bitmaps){
                if (bitmap!=null)
                    bitmapList.add(bitmap);
            }
        }
        return bitmapList;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            com.theartofdev.edmodo.cropper.CropImage.ActivityResult result = com.theartofdev.edmodo.cropper.CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    if (imageChanging > -1) {
                        bitmaps[imageChanging] = Utils.getBitmapFromUri(getActivity(), result.getUri());
                        if (images[imageChanging] != null)
                            images[imageChanging].setImageBitmap(bitmaps[imageChanging]);
                        imageChanging = -1;
                    }
                } catch (Exception e) {
                }
            } else if (resultCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            }
        }
    }
}
