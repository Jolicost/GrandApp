package com.siziksu.layers.domain.main;

import android.util.Log;

import com.siziksu.layers.App;
import com.siziksu.layers.common.Constants;
import com.siziksu.layers.common.manager.ThrowableManager;
import com.siziksu.layers.data.RepositoryContract;
import com.siziksu.layers.data.model.DateDataModel;
import com.siziksu.layers.data.model.LoremIpsumDataModel;
import com.siziksu.layers.domain.mapper.DateMapper;
import com.siziksu.layers.domain.mapper.LoremIpsumMapper;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class LoremDomain implements LoremDomainContract<LoremDomainPresenterContract> {

    private static final int PARAGRAPHS = 5;

    @Inject
    RepositoryContract repository;

    private Disposable[] disposable = new Disposable[2];
    private LoremDomainPresenterContract presenter;

    public LoremDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register(LoremDomainPresenterContract presenter) {
        this.presenter = presenter;
    }

    @Override
    public void unregister() {
        presenter = null;
        disposable = new Disposable[2];
    }

    @Override
    public void start() {
        getLastVisitedDate();
        getLoremIpsum(true);
    }

    @Override
    public void getLoremIpsum(boolean usingCache) {
        clearDisposable(0);
        presenter.showLoadingDialog();
        disposable[0] = repository.getLoremIpsum(PARAGRAPHS, usingCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::showLoremIpsum,
                        this::handleLoremIpsumError
                );
    }

    @Override
    public void setLastVisitedDate() {
        repository.setLastVisitedDate();
    }

    private void getLastVisitedDate() {
        clearDisposable(1);
        disposable[1] = repository.getLastVisitedDate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::showLastVisitedDate,
                        throwable -> Log.e(Constants.TAG, throwable.getMessage(), throwable)
                );
    }

    private void showLoremIpsum(LoremIpsumDataModel loremIpsumDataModel) {
        if (presenter != null) {
            presenter.showLoremIpsum(new LoremIpsumMapper().map(loremIpsumDataModel));
            presenter.hideLoadingDialog();
        }
    }

    private void showLastVisitedDate(DateDataModel dateDataModel) {
        if (presenter != null) {
            presenter.showLastVisitedDate(new DateMapper().map(dateDataModel));
        }
    }

    private void handleLoremIpsumError(Throwable throwable) {
        int error = ThrowableManager.handleException(throwable);
        String message;
        switch (error) {
            case ThrowableManager.CONNECTION_EXCEPTION:
                message = "Error: there was a connection error";
                break;
            case ThrowableManager.SERVER_EXCEPTION:
                message = "Error: there was a server error";
                break;
            case ThrowableManager.TIME_OUT_EXCEPTION:
                message = "Error: there was a time out";
                break;
            case ThrowableManager.UNAUTHORIZED_EXCEPTION:
                message = "Error: unauthorized call";
                break;
            case ThrowableManager.GENERIC_EXCEPTION:
            default:
                message = "There was an error";
                break;
        }
        if (presenter != null) {
            presenter.hideLoadingDialog();
            presenter.showError(message);
        }
    }

    private void clearDisposable(int index) {
        if (disposable[index] != null) {
            disposable[index].dispose();
            disposable[index] = null;
        }
    }
}
