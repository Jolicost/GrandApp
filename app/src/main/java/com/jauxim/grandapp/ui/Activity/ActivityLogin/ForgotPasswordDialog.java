package com.jauxim.grandapp.ui.Activity.ActivityLogin;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.deps.Deps;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordDialog extends Dialog {

    public ActivityLogin c;

    Deps deps;

    @BindView(R.id.edPhone)
    EditText edPhone;

    public ForgotPasswordDialog(ActivityLogin a, Deps deps) {
        super(a, R.style.DialogWithoutMargins);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.deps = deps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forgot_password_layout);

        ButterKnife.bind(this);
        deps.inject(this);

    }

    @OnClick(R.id.bCancel)
    public void cancelClick(){
        dismiss();
    }

    @OnClick(R.id.bSend)
    public void sendClick(){
        String phone = edPhone.getText().toString();
        c.forgotPassword(phone);
        dismiss();
    }
}
