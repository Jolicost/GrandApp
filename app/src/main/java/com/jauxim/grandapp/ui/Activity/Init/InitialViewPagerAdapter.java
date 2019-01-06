package com.jauxim.grandapp.ui.Activity.Init;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jauxim.grandapp.R;

public class InitialViewPagerAdapter extends PagerAdapter {


    private String[] mTestArray;
    private String[] mTestArrayTitle;

    public InitialViewPagerAdapter(Context context){
        mTestArray = context.getResources().getStringArray(R.array.main_viewpager_strings);
        mTestArrayTitle = context.getResources().getStringArray(R.array.main_viewpager_strings_titles);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup layout = null;
        if(container != null) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            layout = (ViewGroup) inflater.inflate(R.layout.item_init_viewpager, container, false);

            String string = mTestArray[position];
            String stringTitle = mTestArrayTitle[position];
            TextView textView = layout.findViewById(R.id.tvText);
            TextView textViewTitle = layout.findViewById(R.id.tvTextTitle);

            textView.setText(string);
            textViewTitle.setText(stringTitle);

            container.addView(layout);
        }
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
    }*/


    @Override
    public int getCount() {
        return mTestArray.length;
    }
}

