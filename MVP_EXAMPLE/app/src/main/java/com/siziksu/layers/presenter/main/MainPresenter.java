package com.siziksu.layers.presenter.main;

import com.siziksu.layers.App;
import com.siziksu.layers.R;
import com.siziksu.layers.common.ui.DialogFragmentHelper;
import com.siziksu.layers.common.utils.DatesUtils;
import com.siziksu.layers.common.utils.StringUtils;
import com.siziksu.layers.domain.main.LoremDomainContract;
import com.siziksu.layers.domain.main.LoremDomainPresenterContract;
import com.siziksu.layers.domain.model.DateDomainModel;
import com.siziksu.layers.domain.model.LoremIpsumDomainModel;

import javax.inject.Inject;

public final class MainPresenter implements MainPresenterContract<MainViewContract>, LoremDomainPresenterContract {

    @Inject
    LoremDomainContract<LoremDomainPresenterContract> domain;

    private MainViewContract view;

    public MainPresenter() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register(MainViewContract view) {
        this.view = view;
        domain.register(this);
    }

    @Override
    public void unregister() {
        domain.setLastVisitedDate();
        domain.unregister();
        view = null;
    }

    @Override
    public void start() {
        domain.start();
    }

    @Override
    public void onReloadButtonClick() {
        if (view != null) {
            DialogFragmentHelper.showConfirmationDialog(
                    view.getAppCompatActivity(),
                    R.string.main_reload_dialog_message,
                    R.string.main_reload_dialog_yes,
                    () -> domain.getLoremIpsum(false),
                    R.string.main_reload_dialog_no,
                    () -> {}
            );
        }
    }

    @Override
    public void showLoadingDialog() {
        if (view != null) {
            view.showProgress();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (view != null) {
            view.hideProgress();
        }
    }

    @Override
    public void showError(String message) {
        if (view != null) {
            view.showError(message);
        }
    }

    @Override
    public void showLastVisitedDate(DateDomainModel dateDomainModel) {
        if (view != null) {
            String string = view.getAppCompatActivity().getString(R.string.last_visited_date);
            view.showLastVisitedDate(String.format(string, DatesUtils.getDateString(dateDomainModel.date)));
        }
    }

    @Override
    public void showLoremIpsum(LoremIpsumDomainModel loremIpsumDomainModel) {
        if (view != null) {
            view.showLoremIpsum(StringUtils.compoundStringFromStringList(loremIpsumDomainModel.list));
        }
    }
}
