package com.jauxim.grandapp.ui.Activity.Register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Dialog;
import com.jauxim.grandapp.Utils.Utils;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;
import com.jauxim.grandapp.ui.Activity.Main.Main;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.internal.Util;

public class Register extends BaseActivity implements RegisterView {

    @Inject
    public Service service;

    @BindView(R.id.etPhoneNUmber)
    EditText etPhoneNUmber;

    @BindView(R.id.re_email)
    EditText email;

    @BindView(R.id.re_password)
    EditText password;

    @BindView(R.id.re_password2)
    EditText password2;

    @BindView(R.id.re_completeName)
    EditText re_completeName;

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;

    @BindView(R.id.tilPhoneNumber)
    TextInputLayout tilPhoneNUmber;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;

    @BindView(R.id.tilPassword2)
    TextInputLayout tilPassword2;

    @BindView(R.id.tilName)
    TextInputLayout tilName;

    @BindView(R.id.ccpReg)
    CountryCodePicker ccp;

    @BindView(R.id.llInputContainer)
    LinearLayout llInputContainer;

    @BindView(R.id.textTitle)
    TextView textTitle;

    @BindView(R.id.tv2)
    TextView textWelcome;

    @BindView(R.id.rlProfileImage)
    View rlProfileImage;

    @BindView(R.id.civUser)
    CircleImageView civUser;

    RegisterPresenter presenter;
    private String base64Image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.register_app);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            llInputContainer.setTransitionGroup(true);
        }
        getDeps().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        textTitle.setText(getString(R.string.register_button));
        textWelcome.setText(getString(R.string.welcomeRegister));

        rlProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.createCropCamera(true).start(Register.this);
            }
        });

        presenter = new RegisterPresenter(service, this);
    }


    @OnClick(R.id.re_button)
    public void doRegister() {
        showWait();
        String code = ccp.getSelectedCountryCodeWithPlus();
        String phone = etPhoneNUmber.getText().toString();
        String user_email = email.getText().toString();
        String pass = password.getText().toString();
        String pass2 = password2.getText().toString();
        String compName = re_completeName.getText().toString();
        presenter.register(code, phone, user_email, pass, pass2, compName, base64Image);
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
        Dialog.createDialog(this).title("server error int act. info").description(appErrorMessage).build();
    }

    @Override
    public void showUserError(int user_error) {
        removeWait();
        tilPhoneNUmber.setError(getString(user_error));
    }

    @Override
    public void showEmailError(int email_error) {
        removeWait();
        tilEmail.setError(getString(email_error));
    }

    @Override
    public void showPassError(int pass_error) {
        removeWait();
        tilPassword.setError(getString(pass_error));
    }

    @Override
    public void showPass2Error(int pass2_error) {
        removeWait();
        tilPassword2.setError(getString(pass2_error));
    }

    @Override
    public void resetErrors() {
        tilPhoneNUmber.setError(null);
        tilEmail.setError(null);
        tilName.setError(null);
        tilPassword.setError(null);
        tilPassword2.setError(null);
    }

    @Override
    public void showRegisterError(int register_error) {
        removeWait();
        Dialog.createDialog(this).title(getString(register_error)).description(getString(register_error)).build();
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, Main.class);
        intent.putExtra(Constants.NEW_USER, true);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            com.theartofdev.edmodo.cropper.CropImage.ActivityResult result = com.theartofdev.edmodo.cropper.CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = Utils.getBitmapFromUri(this, result.getUri());
                    base64Image = Utils.getBase64(bitmap);
                    civUser.setImageBitmap(bitmap);
                } catch (Exception e) {
                }
            } else if (resultCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            }
        }
    }
}
