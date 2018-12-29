package com.jauxim.grandapp.ui.Activity.Main;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;
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

    @BindView(R.id.tvPriceValues)
    TextView tvPriceValues;

    @BindView(R.id.etName)
    EditText etName;

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
    }

    @OnClick(R.id.bCancel)
    public void cancelClick() {
        dismiss();
    }

    @OnClick(R.id.bSend)
    public void sendClick() {
        if (c!=null) {
            c.updateFilter(updateModel());
            dismiss();
        }
    }

    @OnClick(R.id.tvReset)
    public void resetClick() {
        if (rsbPrice!=null){
            rsbPrice.setSelectedMinValue(rsbPrice.getAbsoluteMinValue());
            rsbPrice.setSelectedMaxValue(rsbPrice.getAbsoluteMaxValue());
            updatePrice();
        }

        if (etName!=null)
            etName.setText("");
    }

    private void setPriceRangeView() {
        updatePrice();
        rsbPrice.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                updatePrice();
            }
        });
    }

    private void updatePrice() {
        tvPriceValues.setText("[" + rsbPrice.getSelectedMinValue() + "€ - " + rsbPrice.getSelectedMaxValue() + "€]");
    }

    private FilterActivityModel updateModel(){
        FilterActivityModel filter = new FilterActivityModel();
        boolean edited = false;
        if (rsbPrice!=null){
            if (rsbPrice.getSelectedMaxValue()!=rsbPrice.getAbsoluteMaxValue()
                    ||rsbPrice.getSelectedMinValue() != rsbPrice.getAbsoluteMinValue()){
                edited = true;
            }

            filter.setMaxPrice(rsbPrice.getSelectedMaxValue().longValue());
            filter.setMinPrice(rsbPrice.getSelectedMinValue().longValue());
        }

        if (etName!=null && !TextUtils.isEmpty(etName.getText())){
            filter.setName(etName.getText().toString());
            edited = true;
        }

        if (!edited)
            return null;

        return filter;
    }
}
