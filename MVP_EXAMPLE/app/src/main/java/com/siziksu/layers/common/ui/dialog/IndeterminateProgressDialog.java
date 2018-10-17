package com.siziksu.layers.common.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.siziksu.layers.R;

class IndeterminateProgressDialog extends ProgressDialog {

    IndeterminateProgressDialog(Context context) {
        super(context, R.style.AppTheme_TransparentDialogStyle_NoTitle);
        init();
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.dialog_loading);
    }

    private void init() {
        setCanceledOnTouchOutside(false);
        setIndeterminate(true);
    }
}
