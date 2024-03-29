package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.SingleShotLocationProvider;
import com.jauxim.grandapp.Utils.Utils;
import com.jauxim.grandapp.models.ActivityModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_DESCRIPTION;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_IMAGES;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_LOCATION;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_PREVIEW;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_TIME;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_TITLE;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_CAPACITYPRICE;

public class ContainerEditFragment extends Fragment implements View.OnClickListener {

    int position = 0;

    ActivityEditActivity parent;

    private TextView etTitle;
    private TextView etDescription;
    private ImageView[] images = new ImageView[4];
    private String[] imagesBase64 = new String[4];
    private MapView gMapView;

    private double actualLatitude;
    private double actualLongitude;

    private int imageChanging = -1;

    private Long initTimestamp;
    private Long endTimestamp;

    private TextView tvDateStart;
    private TextView tvDateEnd;

    private TextView etCapacity;
    private TextView etPrice;

    private TextView tvTitlePreview;
    private TextView tvDescriptionPreview;
    private TextView tvLocationPreview;
    private TextView tvInitialDatePreview;
    private TextView tvEndDatePreview;
    private TextView tvCapacityPreview;
    private TextView tvPricePreview;
    private TextView tvCategoryPreview;

    private TextView tvAdress;

    private Spinner sCategory;

    public @interface stepsEditActivity {
        int STEP_TITLE = 0;
        int STEP_DESCRIPTION = 1;
        int STEP_IMAGES = 2;
        int STEP_LOCATION = 3;
        int STEP_TIME = 4;
        int STEP_CAPACITYPRICE = 5;
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
        View vCapacityPrice = view.findViewById(R.id.vCapacityPrice);
        View vPreview = view.findViewById(R.id.vPreview);
        View vDateStart = view.findViewById(R.id.vInitDate);
        View vDateEnd = view.findViewById(R.id.vEndDate);
        tvDateStart = view.findViewById(R.id.tvInitDate);
        tvDateEnd = view.findViewById(R.id.tvEndDate);
        tvTitlePreview = view.findViewById(R.id.tvTitlePreview);
        tvDescriptionPreview = view.findViewById(R.id.tvDescriptionPreview);
        tvLocationPreview = view.findViewById(R.id.tvLocationPreview);
        tvInitialDatePreview = view.findViewById(R.id.tvInitialDatePreview);
        tvEndDatePreview = view.findViewById(R.id.tvEndDatePreview);
        tvCapacityPreview = view.findViewById(R.id.tvCapacityPreview);
        tvPricePreview = view.findViewById(R.id.tvPricePreview);
        tvCategoryPreview = view.findViewById(R.id.tvCategoryPreview);
        sCategory = view.findViewById(R.id.sCategory);

        switch (position) {
            case STEP_TITLE:
                etTitle = view.findViewById(R.id.etTitle);

                vTitle.setVisibility(View.VISIBLE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vCapacityPrice.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case STEP_DESCRIPTION:
                etDescription = view.findViewById(R.id.etDescription);

                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.VISIBLE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vCapacityPrice.setVisibility(View.GONE);
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
                vCapacityPrice.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case STEP_LOCATION:
                gMapView = view.findViewById(R.id.soleViewMap);
                tvAdress = view.findViewById(R.id.tvAdress);

                gMapView.onCreate(savedInstanceState);
                gMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap googleMap) {
                        SingleShotLocationProvider.GPSCoordinates coord;
                        if (actualLatitude==0 && actualLongitude==0) {
                            //no activities on the sea
                            coord = DataUtils.getLocation(getActivity());
                        }else{
                            coord = new SingleShotLocationProvider.GPSCoordinates(actualLatitude, actualLongitude);
                        }
                        if (coord != null) {
                            Log.d("coordEdit", "coord are not null: " + coord.latitude + ", " + coord.longitude);
                            googleMap.setMinZoomPreference(10);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(coord.latitude, coord.longitude), 15.0f));
                        } else {
                            Log.d("coordEdit", "coord are null: ");
                        }

                        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                            @Override
                            public void onCameraIdle() {
                                Geocoder geoCoder = new Geocoder(getActivity());
                                List<Address> matches = null;
                                try {
                                    matches = geoCoder.getFromLocation(actualLatitude, actualLongitude, 1);
                                    Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                                    tvAdress.setText(bestMatch.getAddressLine(0));
                                } catch (Exception e) {
                                    tvAdress.setText("");
                                }
                            }
                        });

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
                vCapacityPrice.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case STEP_TIME:

                vDateStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initDatePicker(System.currentTimeMillis(), true);
                    }
                });

                vDateEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initDatePicker(System.currentTimeMillis(), false);
                    }
                });

                tvDateStart.setText(getString(R.string.date_not_choosed));
                tvDateEnd.setText(getString(R.string.date_not_choosed));

                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vCapacityPrice.setVisibility(View.GONE);
                vDate.setVisibility(View.VISIBLE);
                vPreview.setVisibility(View.GONE);

                break;

            case STEP_CAPACITYPRICE:
                etCapacity = view.findViewById(R.id.etCapacity);
                etPrice = view.findViewById(R.id.etPrice);

                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vCapacityPrice.setVisibility(View.VISIBLE);
                vPreview.setVisibility(View.GONE);

                break;

            case STEP_PREVIEW:

                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vCapacityPrice.setVisibility(View.GONE);
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

    public void setTitle(String title){
        if (etTitle != null)
            etTitle.setText(title);
    }

    public String getDescription() {
        if (etDescription != null)
            return etDescription.getText().toString();
        return null;
    }

    public void setDescription(String description){
        if (etDescription != null)
            etDescription.setText(description);
    }

    public SingleShotLocationProvider.GPSCoordinates getLocation() {
        //activity won't never be on the sea
        if (actualLatitude != 0 && actualLongitude != 0) {
            return new SingleShotLocationProvider.GPSCoordinates(actualLatitude, actualLongitude);
        }
        return null;
    }

    public void setCoordinates(Double latitude, Double longitude) {
        //activity won't never be on the sea
        if (gMapView != null){
            actualLatitude = latitude;
            actualLongitude = longitude;
        }
    }

    public List<String> getImages() {
        List<String> imagesBase64List = new ArrayList<>();
        if (imagesBase64 != null) {
            for (String base64 : imagesBase64) {
                if (!TextUtils.isEmpty(base64))
                    imagesBase64List.add(base64);
            }
        }
        return imagesBase64List;
    }

    public void setImages(List<String> urls){
        for (int i = 0; i<urls.size(); i++){
            final int ii = i;
            Glide.with(getActivity()).asBitmap().load(urls.get(i)).into(new BitmapImageViewTarget(images[i]) {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> anim) {
                    super.onResourceReady(bitmap, anim);
                    imagesBase64[ii] = Utils.getBase64(bitmap);
                }
            });
        }
    }

    public Long getTimeStart() {
        return initTimestamp;
    }

    public void setTimeStart(Long timestampStart){
        if (tvDateStart!=null) {
            tvDateStart.setText(Utils.getFullDataFormat(timestampStart));
            initTimestamp = timestampStart;
        }
    }

    public Long getTimeEnd(){
        return endTimestamp;
    }

    public void setTimeEnd(Long timestampEnd){
        if (tvDateEnd!=null) {
            tvDateEnd.setText(Utils.getFullDataFormat(timestampEnd));
            endTimestamp = timestampEnd;
        }
    }

    public String getCapacity() {
        if (etCapacity != null)
            return etCapacity.getText().toString();
        return null;
    }

    public void setCapacity(Long capacity){
        if (etCapacity!=null)
            etCapacity.setText(String.valueOf(capacity));
    }

    public String getPrice() {
        if (etPrice != null)
            return etPrice.getText().toString();
        return null;
    }

    public void setPrice(Long price){
        if (etPrice!=null)
            etPrice.setText(String.valueOf(price));
    }

    public String getCategory(){
        if (sCategory!=null)
            return sCategory.getSelectedItem().toString();
        return "";
    }

    public void setCategory(String category){
        if (sCategory!=null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.filter_categories, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sCategory.setAdapter(adapter);
            if (category != null) {
                int spinnerPosition = adapter.getPosition(category);
                sCategory.setSelection(spinnerPosition);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            com.theartofdev.edmodo.cropper.CropImage.ActivityResult result = com.theartofdev.edmodo.cropper.CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    if (imageChanging > -1) {
                        Bitmap bitmap = Utils.getBitmapFromUri(getActivity(), result.getUri());
                        imagesBase64[imageChanging] = Utils.getBase64(bitmap);
                        if (images[imageChanging] != null)
                            images[imageChanging].setImageBitmap(bitmap);
                        imageChanging = -1;
                    }
                } catch (Exception e) {
                }
            } else if (resultCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            }
        }
    }

    private void initDatePicker(final Long timeStamp, final boolean startTime) {
        Calendar calendar = Calendar.getInstance();
        if (timeStamp != null && timeStamp > 0)
            calendar.setTimeInMillis(timeStamp);

        DatePickerDialog dialogDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth, 0, 0);
                if (startTime) {
                    initTimestamp = newDate.getTimeInMillis();
                } else {
                    endTimestamp = newDate.getTimeInMillis();
                }
            }

        }, calendar.get(Calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        dialogDate.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                TimePickerDialog dialogTime = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int millis = (minute*60+hourOfDay*60*60)*1000;
                        if (startTime) {
                            initTimestamp += millis;
                            if (tvDateStart != null)
                                tvDateStart.setText(Utils.getFullDataFormat(initTimestamp));
                        } else {
                            endTimestamp += millis;
                            if (tvDateEnd != null)
                                tvDateEnd.setText(Utils.getFullDataFormat(endTimestamp));
                        }
                    }
                }, 0, 0, true);
                dialogTime.show();
            }
        });
        dialogDate.show();
    }
    public void setParent(ActivityEditActivity activityEditActivity) {
        parent = activityEditActivity;
    }

    public void updateAndSetModel(ActivityModel model){
        tvTitlePreview.setText(model.getTitle());
        tvDescriptionPreview.setText(model.getDescription());
        tvLocationPreview.setText(model.getLatitude()+","+model.getLongitude());
        tvInitialDatePreview.setText(Utils.getFullDataFormat(model.getTimestampStart()));
        if (model.getTimestampEnd()!=null)
            tvEndDatePreview.setText(Utils.getFullDataFormat(model.getTimestampEnd()));
        else
            tvEndDatePreview.setText("not defined");
        tvCapacityPreview.setText(model.getCapacity()+"");
        tvPricePreview.setText(model.getPrice()+"");
        tvCategoryPreview.setText(model.getActivityType());
        Log.d("pagerThing", "updateAndSetModel ");
    }
}
