package com.jauxim.grandapp.ui.Activity.ActivityProfile;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.Window;
import android.widget.EditText;

import com.jauxim.grandapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordDialog extends Dialog {

    public ActivityProfile c;

    @BindView(R.id.edPasswordOld)
    EditText edPasswordOld;

    @BindView(R.id.edPasswordNew)
    EditText edPasswordNew;

    @BindView(R.id.edPasswordNewRe)
    EditText edPasswordNewRe;

    @BindView(R.id.tilPasswordOld)
    TextInputLayout tilPasswordOld;

    @BindView(R.id.tilPasswordNew)
    TextInputLayout tilPasswordNew;

    @BindView(R.id.tilPasswordNewRe)
    TextInputLayout tilPasswordNewRe;

    public ChangePasswordDialog(ActivityProfile a) {
        super(a, R.style.DialogWithoutMargins);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_password_layout);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.bCancel)
    public void cancelClick(){
        dismiss();
    }

    @OnClick(R.id.bChange)
    public void changeClick(){
        String pOld = edPasswordOld.getText().toString();
        String pNew = edPasswordNew.getText().toString();
        String pNewRe = edPasswordNewRe.getText().toString();
        c.changePassword(pOld, pNew, pNewRe);
    }

    public void resetErrors() {
        tilPasswordOld.setError(null);
        tilPasswordNew.setError(null);
        tilPasswordNewRe.setError(null);
    }

    public void showOldPassError(String pass_error) {
        tilPasswordOld.setError(pass_error);
    }

    public void showNewPassError(String pass_error) {
        tilPasswordNew.setError(pass_error);
    }

    public void showNewRePassError(String pass_error) {
        tilPasswordNewRe.setError(pass_error);
    }

    public void showPass2Error(String pass2_error) {
        tilPasswordNewRe.setError(pass2_error);
    }
}
