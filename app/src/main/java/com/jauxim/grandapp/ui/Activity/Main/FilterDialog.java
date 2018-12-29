package com.jauxim.grandapp.ui.Activity.Main;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.models.FilterActivityModel;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterDialog extends Dialog {

    public Main c;

    @BindView(R.id.rsbPrice)
    RangeSeekBar rsbPrice;

    @BindView(R.id.rsbDistance)
    RangeSeekBar rsbDistance;

    @BindView(R.id.tvPriceValues)
    TextView tvPriceValues;

    @BindView(R.id.tvDistanceValues)
    TextView tvDistanceValues;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.sSort)
    Spinner sSort;

    @BindView(R.id.sCategory)
    Spinner sCategory;

    public FilterDialog(Main a) {
        super(a, R.style.DialogWithoutMargins);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_dialog_layout);

        ButterKnife.bind(this);

        setPriceRangeView();
        setDistanceRangeView();
    }

    @OnClick(R.id.bCancel)
    public void cancelClick() {
        dismiss();
    }

    @OnClick(R.id.bSend)
    public void sendClick() {
        if (c != null) {
            c.updateFilter(updateModel());
            dismiss();
        }
    }

    @OnClick(R.id.tvReset)
    public void resetClick() {
        if (rsbPrice != null) {
            rsbPrice.setSelectedMinValue(rsbPrice.getAbsoluteMinValue());
            rsbPrice.setSelectedMaxValue(rsbPrice.getAbsoluteMaxValue());
            updatePrice();
        }

        if (rsbDistance != null) {
            rsbDistance.setSelectedMinValue(rsbDistance.getAbsoluteMinValue());
            rsbDistance.setSelectedMaxValue(rsbDistance.getAbsoluteMaxValue());
            updateDistance();
        }

        if (etName != null)
            etName.setText("");

        if (sCategory!=null)
            sCategory.setSelection(0);

        if (sSort!=null)
            sSort.setSelection(0);
    }

    private void setPriceRangeView() {
        updatePrice();
        rsbPrice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                updatePrice();
                return false;
            }
        });
    }

    private void setDistanceRangeView() {
        updatePrice();
        rsbDistance.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                updateDistance();
                return false;
            }
        });
    }

    private void updatePrice() {
        updatePrice(rsbPrice.getSelectedMinValue(), rsbPrice.getSelectedMaxValue());
    }

    private void updatePrice(Number min, Number max) {
        if (rsbPrice.getSelectedMaxValue() != rsbPrice.getAbsoluteMaxValue()
                || rsbPrice.getSelectedMinValue() != rsbPrice.getAbsoluteMinValue()) {
            tvPriceValues.setText("[" + min + "€ - " + max + "€]");
        } else {
            tvPriceValues.setText("");
        }
    }

    private void updateDistance() {
        updateDistance(rsbDistance.getSelectedMinValue(), rsbDistance.getSelectedMaxValue());
    }

    private void updateDistance(Number min, Number max) {
        if (rsbDistance.getSelectedMaxValue().intValue() != rsbDistance.getAbsoluteMaxValue().intValue()
                || rsbDistance.getSelectedMinValue().intValue() != rsbDistance.getAbsoluteMinValue().intValue()) {
            tvDistanceValues.setText("[" + min + "m - " + max + "m]");
        } else {
            tvDistanceValues.setText("");
        }
    }

    private FilterActivityModel updateModel() {
        FilterActivityModel filter = new FilterActivityModel();
        boolean edited = false;
        if (rsbPrice != null) {
            if (rsbPrice.getSelectedMaxValue().intValue() != rsbPrice.getAbsoluteMaxValue().intValue()
                    || rsbPrice.getSelectedMinValue().intValue() != rsbPrice.getAbsoluteMinValue().intValue()) {
                edited = true;
            }

            filter.setMaxPrice(rsbPrice.getSelectedMaxValue().longValue());
            filter.setMinPrice(rsbPrice.getSelectedMinValue().longValue());
        }

        if (rsbDistance != null) {
            if (rsbDistance.getSelectedMaxValue() != rsbDistance.getAbsoluteMaxValue()
                    || rsbDistance.getSelectedMinValue() != rsbDistance.getAbsoluteMinValue()) {
                edited = true;
            }


            filter.setMaxDistance(rsbDistance.getSelectedMaxValue().longValue());
            filter.setMinDistance(rsbDistance.getSelectedMinValue().longValue());
        }

        if (etName != null && !TextUtils.isEmpty(etName.getText())) {
            filter.setName(etName.getText().toString());
            edited = true;
        }

        if (sSort!=null){
            if (sSort.getSelectedItemPosition()>0){
                edited = true;
            }

            filter.setSort(sSort.getSelectedItemPosition());
        }

        if (sCategory!=null){
            if (sCategory.getSelectedItemPosition()>0){
                edited = true;
            }

            filter.setCategory(sCategory.getSelectedItemPosition());
        }

        if (!edited)
            return null;

        return filter;
    }
}
