package com.jauxim.grandapp.ui.Activity.ActivityEdit;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Utils;

public class ActivityStepsAdapter extends PagerAdapter {

    private Activity context;

    private TextView etTitle;
    private TextView etDescription;

    public @interface stepsEditActivity {
        int STEP_TITLE = 0;
        int STEP_DESCRIPTION = 1;
        int STEP_IMAGES = 2;
        int STEP_PEOPLE_LOCATION = 3;
        int STEP_TIME = 4;
        int STEP_MISCELANIA = 5;
        int STEP_PREVIEW = 6;
    }

    public ActivityStepsAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.edit_activity_step_item, null);

        View main = view.findViewById(R.id.main);
        View vTitle = view.findViewById(R.id.vTitle);
        View vDescription = view.findViewById(R.id.vDescription);
        View vImages = view.findViewById(R.id.vImages);
        View vLocation = view.findViewById(R.id.vLocation);
        View vDate = view.findViewById(R.id.vDate);
        View vMiscelania = view.findViewById(R.id.vMiscelania);
        View vPreview = view.findViewById(R.id.vPreview);

        Button bImage = view.findViewById(R.id.bImage);

        switch (position) {
            case stepsEditActivity.STEP_TITLE:
                etTitle = view.findViewById(R.id.etTitle);

                vTitle.setVisibility(View.VISIBLE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case stepsEditActivity.STEP_DESCRIPTION:
                etDescription = view.findViewById(R.id.etDescription);

                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.VISIBLE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case stepsEditActivity.STEP_IMAGES:

                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.VISIBLE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case stepsEditActivity.STEP_PEOPLE_LOCATION:

                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.VISIBLE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case stepsEditActivity.STEP_TIME:
                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.VISIBLE);
                vMiscelania.setVisibility(View.GONE);
                vPreview.setVisibility(View.GONE);

                break;
            case stepsEditActivity.STEP_MISCELANIA:
                vTitle.setVisibility(View.GONE);
                vDescription.setVisibility(View.GONE);
                vImages.setVisibility(View.GONE);
                vLocation.setVisibility(View.GONE);
                vDate.setVisibility(View.GONE);
                vMiscelania.setVisibility(View.VISIBLE);
                vPreview.setVisibility(View.GONE);

                break;

            case stepsEditActivity.STEP_PREVIEW:
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

        bImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.createCropCamera(false).start(context);
            }
        });

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }

    public String getTitle() {
        return etTitle.getText().toString();
    }

    public String getDescription() {
        return etDescription.getText().toString();
    }
}