package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.Utils.SingleShotLocationProvider;
import com.jauxim.grandapp.models.ActivityModel;
import com.jauxim.grandapp.networking.ServiceActivity;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_DESCRIPTION;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_IMAGES;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_LOCATION;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_PREVIEW;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_TIME;
import static com.jauxim.grandapp.ui.Activity.ActivityEdit.ContainerEditFragment.stepsEditActivity.STEP_TITLE;

public class ActivityEditActivity extends BaseActivity implements ActivityEditView {

    @Inject
    public ServiceActivity service;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.indicator)
    TabLayout indicator;

    @BindView(R.id.bNext)
    Button bNext;

    @BindView(R.id.bPrevious)
    Button bPrevious;

    static private boolean demo_edit_mode = false;
    //private ActivityStepsAdapter activityAdapter;

    private String title;
    private String description;
    private SingleShotLocationProvider.GPSCoordinates coordinates;
    private List<String> imagesBase64;
    private Long timeStart;
    private Long timeEnd;

    private ActivityEditPresenter presenter;

    private ActivityEditPageAdapter activityPageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        getDeps().inject(this);
        ButterKnife.bind(this);

        presenter = new ActivityEditPresenter(service, this);

        //activityAdapter = new ActivityStepsAdapter(this);
        //viewPager.setAdapter(activityAdapter);
        activityPageAdapter = new ActivityEditPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(activityPageAdapter);
        viewPager.setOffscreenPageLimit(5);
        indicator.setupWithViewPager(viewPager, true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("pagerThing", "onPageSelected " + position);
                if (!saveActualState()) {
                    //cant go next screen
                    viewPager.setCurrentItem(position - 1);
                } else {
                    //can go next screen
                    if (isInlastStep(position)) {
                        bNext.setText("Create");
                    } else {
                        bNext.setText("Next");
                    }
                }
                Log.d("pagerThing", "onPageScrolled " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("pagerThing", "onPageScrollStateChanged ");

            }
        });
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
    public void createActivityInfoSuccess(ActivityModel activityModel) {
        Dialog.createDialog(this).title("activity created").description("activity created succefully").onDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ActivityEditActivity.this.onBackPressed();
            }
        }).build();
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

    private void updateModel() {
        //TODO: make async calls and fill urls list

        ActivityModel model = new ActivityModel();
        model.setTitle(title);
        model.setDescription(description);
        if (coordinates!=null) {
            model.setLatitude(coordinates.latitude);
            model.setLongitude(coordinates.longitude);
        }
        model.setImagesUrl(new ArrayList<String>());
        model.setImagesBase64(imagesBase64);
        model.setTimestampStart(timeStart);
        model.setTimestampEnd(timeEnd);
        presenter.createActivityInfo(model);
    }

    @OnClick(R.id.bNext)
    void nextButtonClick(View view) {
        if (!isInlastStep(viewPager.getCurrentItem())) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

        } else {
            updateModel();
        }
    }

    @OnClick(R.id.bPrevious)
    void previousButtonClick(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

    private boolean saveActualState() {
        //if (true) return true;
        switch (viewPager.getCurrentItem() - 1) {
            case STEP_TITLE:
                title = getInputTitle();
                if ((TextUtils.isEmpty(title) || title.length() < 5) && !demo_edit_mode) {
                    Dialog.createDialog(this).title(getString(R.string.invalid_title_title)).description(getString(R.string.invalid_title_description)).build();
                    return false;
                }
                return true;
            case STEP_DESCRIPTION:
                description = getInputDescription();
                if ((TextUtils.isEmpty(description) || description.length() < 5) && !demo_edit_mode) {
                    Dialog.createDialog(this).title(getString(R.string.invalid_title_title)).description(getString(R.string.invalid_title_description)).build();
                    return false;
                }
                return true;
            case STEP_IMAGES:
                imagesBase64 = getInputBase64Images();
                if ((imagesBase64==null || imagesBase64.size()==0) && !demo_edit_mode) {
                    Dialog.createDialog(this).title(getString(R.string.invalid_images_title)).description(getString(R.string.invalid_images_description)).build();
                    return false;
                }
                break;
            case STEP_LOCATION:
                coordinates = getInputCoordinates();
                if (coordinates == null && !demo_edit_mode) {
                    Dialog.createDialog(this).title(getString(R.string.invalid_title_title)).description(getString(R.string.invalid_title_description)).build();
                    return false;
                }
                break;
            case STEP_TIME:
                timeStart = getTimeStart();
                timeEnd = getTimeEnd();
                if ((timeStart==null || timeStart<=System.currentTimeMillis()) && !demo_edit_mode){
                    Dialog.createDialog(this).title(getString(R.string.invalid_time_title)).description(getString(R.string.invalid_time_description)).build();
                    return false;
                }
                break;
            case STEP_PREVIEW:
                break;
            default:
                break;
        }
        return true;
    }

    private boolean isInlastStep(int position) {
        return (position == activityPageAdapter.getCount() - 1);
    }

    private class ActivityEditPageAdapter extends FragmentPagerAdapter {

        public ActivityEditPageAdapter(FragmentManager fm) {
            super(fm);
        }

        ContainerEditFragment titleFragment;
        ContainerEditFragment descriptionFragment;
        ContainerEditFragment imagesFragment;
        ContainerEditFragment locationFragment;
        ContainerEditFragment timeFragment;

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case STEP_TITLE:
                    titleFragment = ContainerEditFragment.newInstance(STEP_TITLE);
                    return titleFragment;
                case STEP_DESCRIPTION:
                    descriptionFragment = ContainerEditFragment.newInstance(STEP_DESCRIPTION);
                    return descriptionFragment;
                case STEP_IMAGES:
                    imagesFragment = ContainerEditFragment.newInstance(STEP_IMAGES);
                    return imagesFragment;
                case STEP_LOCATION:
                    locationFragment = ContainerEditFragment.newInstance(STEP_LOCATION);
                    return locationFragment;
                case STEP_TIME:
                    timeFragment = ContainerEditFragment.newInstance(STEP_TIME);
                    return timeFragment;
                default:
                    return ContainerEditFragment.newInstance(5);
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        public String getTitle() {
            if (titleFragment != null)
                return titleFragment.getTitle();
            return null;
        }

        public String getDescription() {
            if (descriptionFragment != null)
                return descriptionFragment.getDescription();
            return null;
        }

        public SingleShotLocationProvider.GPSCoordinates getLocation() {
            if (locationFragment != null)
                return locationFragment.getLocation();
            return null;
        }

        public List<String> getBitmaps(){
            if (imagesFragment != null)
                return imagesFragment.getImages();
            return null;
        }

        public Long getTimeStart() {
            if (timeFragment!=null)
                return timeFragment.getTimeStart();
            return null;
        }

        public Long getTimeEnd() {
            if (timeFragment!=null)
                return timeFragment.getTimeEnd();
            return null;
        }
    }

    private String getInputTitle() {
        if (activityPageAdapter != null)
            return activityPageAdapter.getTitle();
        return "";
    }

    private String getInputDescription() {
        if (activityPageAdapter != null)
            return activityPageAdapter.getDescription();
        return "";
    }

    private SingleShotLocationProvider.GPSCoordinates getInputCoordinates() {
        if (activityPageAdapter != null)
            return activityPageAdapter.getLocation();
        return null;
    }

    private List<String> getInputBase64Images(){
        if (activityPageAdapter != null)
            return activityPageAdapter.getBitmaps();
        return null;
    }

    private Long getTimeStart(){
        if (activityPageAdapter!=null)
            return activityPageAdapter.getTimeStart();
        return null;
    }

    private Long getTimeEnd(){
        if (activityPageAdapter !=null)
            return activityPageAdapter.getTimeEnd();
        return null;
    }



}
