package com.siziksu.layers.ui.view.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.siziksu.layers.App;
import com.siziksu.layers.R;
import com.siziksu.layers.common.ui.DialogFragmentHelper;
import com.siziksu.layers.presenter.main.MainPresenterContract;
import com.siziksu.layers.presenter.main.MainViewContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public final class MainActivity extends AppCompatActivity implements MainViewContract {

    @BindView(R.id.loremIpsum)
    TextView loremIpsum;
    @BindView(R.id.lastVisitedDate)
    TextView lastVisitedDate;

    @Inject
    MainPresenterContract<MainViewContract> presenter;
    private boolean alreadyStarted;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.get().getApplicationComponent().inject(this);
        initializeViews();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.register(this);
        if (!alreadyStarted) {
            alreadyStarted = true;
            presenter.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unregister();
    }

    private void initializeViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void showProgress() {
        DialogFragmentHelper.showLoadingDialog(this);
    }

    @Override
    public void hideProgress() {
        DialogFragmentHelper.hideLoadingDialog(this);
    }

    @Override
    public void showLastVisitedDate(String string) {
        lastVisitedDate.setText(string);
    }

    @Override
    public void showLoremIpsum(String string) {
        loremIpsum.setText(string);
    }

    @Override
    public void showError(String message) {
        Snackbar snackbar = Snackbar.make(loremIpsum, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.reload, v -> presenter.onReloadButtonClick());
        snackbar.show();
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return this;
    }

    @OnClick({R.id.reloadButton})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.reloadButton:
                presenter.onReloadButtonClick();
                break;
            default:
                break;
        }
    }
}
