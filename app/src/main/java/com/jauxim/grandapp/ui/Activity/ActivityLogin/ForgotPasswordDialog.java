package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.deps.Deps;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordDialog extends Dialog {

    public ActivityLogin c;

    @BindView(R.id.edPhone)
    EditText edPhone;

    @BindView(R.id.ccpForgot)
    CountryCodePicker ccp;

    @BindView(R.id.tilPhone)
    TextInputLayout tilPhone;

    public ForgotPasswordDialog(ActivityLogin a) {
        super(a, R.style.DialogWithoutMargins);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forgot_password_layout);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.bCancel)
    public void cancelClick(){
        dismiss();
    }

    @OnClick(R.id.bSend)
    public void sendClick(){
        String code = ccp.getSelectedCountryCodeWithPlus();
        String phone = edPhone.getText().toString();
        c.forgotPassword(phone, code);
        dismiss();
    }
}
