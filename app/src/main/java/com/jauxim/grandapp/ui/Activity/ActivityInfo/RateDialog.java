package com.jauxim.grandapp.ui.Activity.ActivityInfo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;

import com.hbb20.CountryCodePicker;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.ui.Activity.ActivityLogin.ActivityLogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RateDialog extends Dialog {

    public ActivityInfo c;

    @BindView(R.id.rbValue)
    RatingBar rbValue;

    public RateDialog(ActivityInfo a) {
        super(a, R.style.DialogWithoutMargins);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rate_layout);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.bCancel)
    public void cancelClick(){
        dismiss();
    }

    @OnClick(R.id.bVote)
    public void voteClick(){
        if (rbValue!=null) {
            c.voteActivity(rbValue.getRating());
            dismiss();
        }
    }
}
