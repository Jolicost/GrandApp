package com.siziksu.layers.common.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.siziksu.layers.common.function.Consumer;
import com.siziksu.layers.common.ui.dialog.ConfirmationDialogFragment;
import com.siziksu.layers.common.ui.dialog.IndeterminateProgressFragment;

public class DialogFragmentHelper {

    private static final String CONFIRMATION_TAG = "confirmation_dialog";
    private static final String INDETERMINATE_TAG = "indeterminate_dialog";

    private DialogFragmentHelper() {}

    public static void showConfirmationDialog(AppCompatActivity activity, @StringRes int message, @StringRes int positiveText, Consumer positive, @StringRes int negativeText, Consumer negative) {
        ConfirmationDialogFragment fragment = getConfirmationDialogFragment(positive, negative);
        Bundle bundle = new Bundle();
        bundle.putString(ConfirmationDialogFragment.MESSAGE_KEY, activity.getString(message));
        bundle.putString(ConfirmationDialogFragment.BUTTON_POSITIVE_KEY, activity.getString(positiveText));
        bundle.putString(ConfirmationDialogFragment.BUTTON_NEGATIVE_KEY, activity.getString(negativeText));
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), CONFIRMATION_TAG);
    }

    public static void showLoadingDialog(AppCompatActivity activity) {
        IndeterminateProgressFragment fragment = getIndeterminateProgressFragment(activity);
        if (fragment == null) {
            fragment = new IndeterminateProgressFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragment.setCancelable(false);
            fragment.show(fragmentManager, INDETERMINATE_TAG);
        }
    }

    public static void hideLoadingDialog(AppCompatActivity activity) {
        IndeterminateProgressFragment fragment = getIndeterminateProgressFragment(activity);
        if (fragment != null) {
            fragment.dismiss();
        }
    }

    private static IndeterminateProgressFragment getIndeterminateProgressFragment(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        return (IndeterminateProgressFragment) fragmentManager.findFragmentByTag(INDETERMINATE_TAG);
    }

    @NonNull
    private static ConfirmationDialogFragment getConfirmationDialogFragment(Consumer positive, Consumer negative) {
        ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
        fragment.setCallback(positive, negative);
        fragment.setCancelable(false);
        return fragment;
    }
}
